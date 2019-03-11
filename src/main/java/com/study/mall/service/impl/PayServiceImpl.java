package com.study.mall.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.study.mall.dto.OrderDTO;
import com.study.mall.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PayServiceImpl implements PayService {
    private static final String PAY_NAME="微信订餐账单";
    @Autowired
    private BestPayServiceImpl bestpayService;
    @Override
    public void create(OrderDTO orderDTO) {

        PayRequest payRequest=new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(PAY_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【payRequest】,payRequest={}",payRequest);
        PayResponse payResponse = bestpayService.pay(payRequest);
        log.info("【payResponse】,payResponse={}",payResponse);
    }
}
