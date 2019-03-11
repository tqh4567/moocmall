package com.study.mall.enums;

import lombok.Getter;

@Getter
public enum  ResultEnum {
    SUCCESS(0,"操作成功"),
    PARAMS_ERROR(1,"参数错误"),
    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"库存不足"),
    ORDER_NOT_EXIST(12,"订单不存在"),
    ORDERDETAIL_NOT_EXIST(12,"订单详情不存在"),
    ORDER_STATUS_ERROR(13,"订单状态不正确"),
    ORDER_UPDATE_FAIL(14,"订单修改失败"),
    ORDER_DETAIL_EMPTY(15,"顶单详情列表为空"),
    ORDER_PAY_ERROR(16,"支付状态错误"),
    CART_EMPTY(17,"购物车为空"),
    ORDER_OWNER_ERROR(18,"权限异常"),
    WEIXIN_MP_ERROR(19,"公众账号异常")
    ;
    private  Integer code;
    private  String Message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        Message = message;
    }
}
