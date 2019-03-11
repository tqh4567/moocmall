package com.study.mall.controller;

import com.study.mall.service.OrderMastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/buyer/order/create")
public class OrderListController {
    @Autowired
    private OrderMastService orderMastService;


}
