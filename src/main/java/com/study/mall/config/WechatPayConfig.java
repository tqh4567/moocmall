package com.study.mall.config;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WechatPayConfig {

    /*@Autowired
    private WechatAccountConfig wechatAccountConfig;
    @Bean
    public BestPayServiceImpl bestPayServiceImpl(){
        BestPayServiceImpl bestPayService=new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config());
        return bestPayService;
    }
    @Bean
    public WxPayH5Config wxPayH5Config(){
        WxPayH5Config config=new WxPayH5Config();
        config.setAppId(wechatAccountConfig.getMpAppId());
        config.setAppSecret(wechatAccountConfig.getMpAppSecret());
        config.setKeyPath(wechatAccountConfig.getKeyPath());
        config.setMchId(wechatAccountConfig.getMchId());
        config.setMchKey(wechatAccountConfig.getMchKey());
        config.setNotifyUrl(wechatAccountConfig.getNotifyUrl());
        return config;
    }*/
    @Autowired
    private WechatAccountConfig accountConfig;

    @Bean
    public BestPayServiceImpl bestPayService() {
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config());
        return bestPayService;
    }

    @Bean
    public WxPayH5Config wxPayH5Config() {
        WxPayH5Config wxPayH5Config = new WxPayH5Config();
        wxPayH5Config.setAppId(accountConfig.getMpAppId());
        wxPayH5Config.setAppSecret(accountConfig.getMpAppSecret());
        wxPayH5Config.setMchId(accountConfig.getMchId());
        wxPayH5Config.setMchKey(accountConfig.getMchKey());
        wxPayH5Config.setKeyPath(accountConfig.getKeyPath());
//        wxPayH5Config.setNotifyUrl(accountConfig.getNotifyUrl());
        wxPayH5Config.setNotifyUrl(accountConfig.getNotifyUrl());
        return wxPayH5Config;
    }
}
