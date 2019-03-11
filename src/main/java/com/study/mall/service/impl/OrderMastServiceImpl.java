package com.study.mall.service.impl;

import com.study.mall.convert.OrderMast2OrderDTOConvert;
import com.study.mall.dataObject.OrderDetail;
import com.study.mall.dataObject.OrderMaster;
import com.study.mall.dataObject.ProductInfo;
import com.study.mall.dto.CartDTO;
import com.study.mall.dto.OrderDTO;
import com.study.mall.enums.OrderStatusEnum;
import com.study.mall.enums.PayStatusEnum;
import com.study.mall.enums.ResultEnum;
import com.study.mall.exception.Sellexception;
import com.study.mall.repository.OrderDetailRepository;
import com.study.mall.repository.OrderMasterRepository;
import com.study.mall.service.OrderMastService;
import com.study.mall.service.ProductInfoService;
import com.study.mall.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
@Transactional
@Service
@Slf4j
public class OrderMastServiceImpl implements OrderMastService {
    @Autowired
    private OrderMasterRepository masterRepository;
    @Autowired
    private OrderDetailRepository detailsRepository;
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private OrderMastService orderMastService;
    @Autowired
    private IdWorker idWorker;
    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        String Orderid=String.valueOf(idWorker.nextId());
        BigDecimal orderAmount=new BigDecimal(BigInteger.ZERO);
        //查询商品的信息
        for(OrderDetail orderDetils:orderDTO.getOrderDetailList()){

            ProductInfo productInfo =  productInfoService.findOne(orderDetils.getProductId());
            if (productInfo == null) {
                throw new Sellexception(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //2. 计算订单总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetils.getProductQuantity()))
                    .add(orderAmount);

            //订单详情入库
            orderDetils.setDetailId(String.valueOf(idWorker.nextId()));
            orderDetils.setOrderId(Orderid);
            BeanUtils.copyProperties(productInfo, orderDetils);
            detailsRepository.save(orderDetils);

        }
        OrderMaster orderMaster=new OrderMaster();
//        orderMaster.setOrderId(Orderid);
        orderDTO.setOrderId(Orderid);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        masterRepository.save(orderMaster);
        //库存减少
        List<CartDTO> cartDTOList=orderDTO.getOrderDetailList().stream().map(e->
                new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster=masterRepository.findOne(orderId);
        if(orderMaster==null){
            throw new Sellexception(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList=detailsRepository.findByOrderId(orderMaster.getOrderId());
        if(orderDetailList==null){
            throw new Sellexception(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenId, Pageable pageable) {
       Page<OrderMaster> orderDTOPage =masterRepository.findByBuyerOpenid(buyerOpenId, pageable);
       List<OrderDTO> orderDTOList= OrderMast2OrderDTOConvert.convert(orderDTOPage.getContent());

        return new PageImpl<>(orderDTOList,pageable,orderDTOPage.getTotalElements());
    }

    /**
     * 查询所有的订单列表
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findAll(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = masterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList= OrderMast2OrderDTOConvert.convert(orderMasterPage.getContent());

        return new PageImpl<>(orderDTOList,pageable,orderMasterPage.getTotalElements());
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        //判断订单状态

       if(!(orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode()))){
            log.error("【取消订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new Sellexception(ResultEnum.ORDER_STATUS_ERROR);
        }
        OrderMaster orderMaster=new OrderMaster();
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster result=masterRepository.save(orderMaster);
        if(result==null){
            log.error("【取消订单】更新失败, orderMaster={}", orderMaster);
            throw new Sellexception(ResultEnum.ORDER_UPDATE_FAIL);
        }
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【取消订单】订单中无商品详情, orderDTO={}", orderDTO);
            throw new Sellexception(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);


        //如果已支付, 需要退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.FINISHED.getCode())) {
            //TODO
        }
        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        OrderMaster orderMaster=new OrderMaster();
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【取消订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new Sellexception(ResultEnum.ORDER_STATUS_ERROR);
        }
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster result=masterRepository.save(orderMaster);
        if(result==null){
            log.error("【取消订单】更新失败, orderMaster={}", orderMaster);
            throw new Sellexception(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【订单支付完成】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new Sellexception(ResultEnum.ORDER_STATUS_ERROR);
        }

        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【订单支付异常】订单支付状态不正确, OrderDTO={}", orderDTO);
            throw new Sellexception(ResultEnum.ORDER_PAY_ERROR);
        }
        orderDTO.setPayStatus(PayStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster result=masterRepository.save(orderMaster);
        if(result==null){
            log.error("【取消订单】更新失败, orderMaster={}", orderMaster);
            throw new Sellexception(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }


}
