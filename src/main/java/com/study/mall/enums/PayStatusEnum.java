package com.study.mall.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnum implements CodeEnum{
    WAIT(0,"待支付"),
    FINISHED(1,"已支付");
    private Integer code;

    private String message;
    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
