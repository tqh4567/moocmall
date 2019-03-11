package com.study.mall.convert;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.study.mall.dataObject.OrderDetail;
import com.study.mall.dto.OrderDTO;
import com.study.mall.enums.ResultEnum;
import com.study.mall.exception.Sellexception;
import com.study.mall.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class OrderFrom2OrderDTOConvert {
    public static OrderDTO convert(OrderForm orderForm){
        Gson gson=new Gson();
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        List<OrderDetail> orderDetailList=new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
            }.getType());
        }catch (Exception e){
            log.error("json转换出错，String",orderForm.getItems());
            throw  new Sellexception(ResultEnum.PARAMS_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
