package com.study.mall.service;

import com.study.mall.dto.OrderDTO;

public interface BuyerService {
    //查询一个订单
    OrderDTO findOrderOne(String openId,String orderId);
    //取消一个订单
    OrderDTO cancelOneOrder(String openId,String orderId);
}
