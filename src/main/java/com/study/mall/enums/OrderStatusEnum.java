package com.study.mall.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum implements CodeEnum{
    /**
     * 订单的状态
     */
    NEW(0,"新订单"),
    FINISHED(1,"订单已处理"),
    CANCEL(2,"订单取消");
    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
