package com.study.mall.exception;

import com.study.mall.enums.ResultEnum;

public class Sellexception extends  RuntimeException {
    private  Integer code;

    public Sellexception(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code=resultEnum.getCode();
    }

    public Sellexception(Integer code,String defaultMessage) {
        super(defaultMessage);
        this.code = code;
    }

//    public Sellexception(Integer code, String defaultMessage) {
//    }
}
