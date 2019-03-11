package com.study.mall.controller;

import com.study.mall.dto.OrderDTO;
import com.study.mall.enums.ResultEnum;
import com.study.mall.exception.Sellexception;
import com.study.mall.service.OrderMastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private OrderMastService orderMastService;
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public void create(@RequestParam("orderId") String orderID,
                       @RequestParam("returnUrl")String returnUrl){
        //订单查询
        OrderDTO orderDTO=orderMastService.findOne(orderID);
        if(orderDTO==null){
            throw new Sellexception(ResultEnum.ORDER_NOT_EXIST);
        }
        //发起支付


    }
}
