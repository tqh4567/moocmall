package com.study.mall.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinControlleer {
    @RequestMapping(value = "/auth",method = RequestMethod.GET)
    public void auth(@RequestParam("code")String code){
        log.info("auth方法执行了");
        log.info("code={}",code);
    }
}
