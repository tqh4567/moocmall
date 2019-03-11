package com.study.mall.service.impl;

import com.study.mall.dataObject.OrderDetail;
import com.study.mall.dto.OrderDTO;
import com.study.mall.enums.OrderStatusEnum;
import com.study.mall.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderMastServiceImplTest {
    private final String BUYER_OPENID="oYoIb6DSLXZUVvduqD8PQEZVH-7o";
    @Autowired
    private OrderMastServiceImpl orderMastService;
    @Test
    public void create() {
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setBuyerName("廖师兄");
        orderDTO.setBuyerAddress("浙江大学");
        orderDTO.setBuyerPhone("123456789011");

        orderDTO.setBuyerOpenid(BUYER_OPENID);
        List<OrderDetail> orderDetils=new ArrayList<>();
        OrderDetail o1=new OrderDetail();
        o1.setProductId("1232343");
        o1.setProductQuantity(1);
        orderDetils.add(o1);
        orderDTO.setOrderDetailList(orderDetils);
        OrderDTO result=orderMastService.create(orderDTO);
        log.info("创建订单  result",result);
    }

    @Test
    public void findOne() {
        OrderDTO result=orderMastService.findOne("1102131164317011968");
        log.info("查询订单  result",result);
        Assert.assertEquals("1102131164317011968",result.getOrderId());
    }

    @Test
    public void findList () throws Exception {
        PageRequest request = new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage = orderMastService.findList(BUYER_OPENID, request);
        Assert.assertNotEquals(0, orderDTOPage.getTotalElements());

    }

    @Test
    public void cancelOrder() {
        OrderDTO one = orderMastService.findOne("1100400273303552000");
        OrderDTO cancel = orderMastService.cancel(one);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),cancel.getOrderStatus());
    }

    @Test
    public void finishOrder() {
        OrderDTO one = orderMastService.findOne("1100400273303552000");
        OrderDTO cancel = orderMastService.finish(one);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),cancel.getOrderStatus());
    }

    @Test
    public void payOrder() {
        OrderDTO orderDTO = orderMastService.findOne("1100400273303552000");
        OrderDTO result = orderMastService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.FINISHED.getCode(), result.getPayStatus());
    }
    @Test
    public void findAll(){
        PageRequest pageRequest=new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage= orderMastService.findAll(pageRequest);
        Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
    }
}