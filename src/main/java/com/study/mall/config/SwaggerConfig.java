package com.study.mall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"com.study.mall.controller"})
public class SwaggerConfig {
    @Bean
    public Docket customerDocket(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo());
    }
    private ApiInfo apiInfo(){
        Contact contact=new Contact("ZED","http://www.1y058.cn/","11111@qq.com");
        return new ApiInfoBuilder()
                .title("慕课网商城接口")
                .description("项目API接口")
                .contact(contact)
                .version("1.0.0")
                .build();
    }
}
