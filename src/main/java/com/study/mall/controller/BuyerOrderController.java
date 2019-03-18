package com.study.mall.controller;

import com.study.mall.VO.ResultVO;
import com.study.mall.convert.OrderFrom2OrderDTOConvert;
import com.study.mall.dto.OrderDTO;
import com.study.mall.enums.ResultEnum;
import com.study.mall.exception.Sellexception;
import com.study.mall.form.OrderForm;
import com.study.mall.service.BuyerService;
import com.study.mall.service.OrderMastService;
import com.study.mall.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping(value = "/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private OrderMastService orderMastService;
    @Autowired
    private BuyerService buyerService;
    //创建订单
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【表单校验】 表单校验失败，orderFrom={}",orderForm);
            throw new Sellexception(ResultEnum.PARAMS_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO= OrderFrom2OrderDTOConvert.convert(orderForm);
        if(orderDTO==null){
            log.error("购物车为空，OrderDTO");
            throw new Sellexception(ResultEnum.CART_EMPTY);
        }
        OrderDTO orderResult = orderMastService.create(orderDTO);
        Map<String,String>  map=new HashMap<>();
        map.put("orderId",orderResult.getOrderId());
        return ResultVOUtil.success(map);


    }

    //订单列表
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ResultVO<List<OrderDTO>> create(@RequestParam("openid") String openid,
                                           @RequestParam(value = "page",defaultValue = "") Integer page,
                                           @RequestParam(value = "size",defaultValue = "10")Integer size){
        if(StringUtils.isEmpty(openid)){
            throw new Sellexception(ResultEnum.ORDER_NOT_EXIST);
        }
        PageRequest pageRequest=new PageRequest(page,size);
        Page<OrderDTO> orderDTOS = orderMastService.findList(openid, pageRequest);
        return ResultVOUtil.success(orderDTOS.getContent());
    }
    //  订单详情
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public ResultVO<List<OrderDTO>> create(@RequestParam("openid") String openid,
                                           @RequestParam(value = "orderId") String orderId){
//        //TODO 改进
//        OrderDTO orderDTO = orderMastService.findOne(orderId);
        OrderDTO orderOne = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(orderOne);

    }

    //订单取消
    @RequestMapping(value = "/cancel",method = RequestMethod.POST)
    public ResultVO<List<OrderDTO>> cancel(@RequestParam("openid") String openid,
                                           @RequestParam(value = "orderId") String orderId){
//        //TODO 改进
//        OrderDTO orderDTO = orderMastService.findOne(orderId);
//        orderMastService.cancel(orderDTO);
        buyerService.cancelOneOrder(openid,orderId);
        return ResultVOUtil.success();

    }

    //支付订单
}
