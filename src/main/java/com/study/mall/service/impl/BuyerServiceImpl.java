package com.study.mall.service.impl;

import com.study.mall.dto.OrderDTO;
import com.study.mall.enums.ResultEnum;
import com.study.mall.exception.Sellexception;
import com.study.mall.service.BuyerService;
import com.study.mall.service.OrderMastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderMastService service;
    @Override
    public OrderDTO findOrderOne(String openId, String orderId) {
        return isExist(openId,orderId);
    }

    @Override
    public OrderDTO cancelOneOrder(String openId, String orderId) {
        OrderDTO orderDTO=isExist(openId,orderId);
        if(orderDTO==null){
            throw new Sellexception(ResultEnum.ORDER_NOT_EXIST);
        }
        return service.cancel(orderDTO);
    }
    public OrderDTO isExist(String openid,String orderId){
        OrderDTO orderDTO=service.findOne(orderId);
        if(orderDTO==null){
            return null;
        }
        if(orderId==null){
            throw new Sellexception(ResultEnum.ORDER_NOT_EXIST);
        }
        if(!orderDTO.getBuyerOpenid().equals(openid)){
            throw new Sellexception(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }

}
