package com.study.mall.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.study.mall.dataObject.OrderDetail;
import com.study.mall.enums.OrderStatusEnum;
import com.study.mall.enums.PayStatusEnum;
import com.study.mall.utils.EnumUtils;
import com.study.mall.utils.serializer.Data2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {


    /** 订单id. */
    private String orderId;

    /** 买家名字. */
    private String buyerName;

    /** 买家手机号. */
    private String buyerPhone;

    /** 买家地址. */
    private String buyerAddress;

    /** 买家微信Openid. */
    private String buyerOpenid;

    /** 订单总金额. */
    private BigDecimal orderAmount;

    /** 订单状态, 默认为0新下单. */
    private Integer orderStatus;

    /** 支付状态, 默认为0未支付. */
    private Integer payStatus;

    /** 创建时间. */
    @JsonSerialize(using = Data2LongSerializer.class)
    private Date createTime;

    /** 更新时间. */
    @JsonSerialize(using = Data2LongSerializer.class)
    private Date updateTime;

   private List<OrderDetail> orderDetailList=new ArrayList<>();
   @JsonIgnore
   public OrderStatusEnum getOrderStatusEnum(){
       return EnumUtils.getByCode(orderStatus,OrderStatusEnum.class);
   }
   @JsonIgnore
   public PayStatusEnum getPayStatusEnum(){
       return EnumUtils.getByCode(payStatus,PayStatusEnum.class);
   }
}
