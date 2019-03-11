package com.study.mall.service.impl;

import com.study.mall.dto.OrderDTO;
import com.study.mall.service.OrderMastService;
import com.study.mall.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {
    @Autowired
    private PayService payService;
    @Autowired
    private OrderMastService service;
    @Test
    public void create() {
        OrderDTO orderDTO=service.findOne("1102131164317011968");
        payService.create(orderDTO);
    }
}