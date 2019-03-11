package com.study.mall.controller;

import com.study.mall.dataObject.ProductCategory;
import com.study.mall.dataObject.ProductInfo;
import com.study.mall.enums.ResultEnum;
import com.study.mall.exception.Sellexception;
import com.study.mall.service.ProductCategoryService;
import com.study.mall.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sell/product")
public class SellProductController {
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductCategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView findAll(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "size",defaultValue = "3")Integer size, Map<String,Object> map){
        PageRequest pageRequest=new PageRequest(page-1,size);

        Page<ProductInfo> productInfos = productInfoService.findAll(pageRequest);
        map.put("productInfos",productInfos);
        map.put("currentPage",page);
        map.put("size",size);

        return new ModelAndView("product/list",map);

    }
    @GetMapping("/update")
    public ModelAndView detail(@RequestParam("productId") String productId, Map<String,Object> map){
        ProductInfo productInfo;
        try {
            productInfo= productInfoService.findOne(productId);
        }catch (Sellexception e){
            map.put("msg", e.getMessage());
            map.put("returnUrl","/sell/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("productInfo",productInfo);
        return new ModelAndView("product/detail",map);
    }
    @GetMapping("/onSale")
    public ModelAndView onSale(@RequestParam("productId") String productId, Map<String,Object> map){
        ProductInfo productInfo;
        try {
            productInfo= productInfoService.onSale(productId);
        }catch (Sellexception e){
            map.put("msg", e.getMessage());
            map.put("returnUrl","/sell/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("productInfo",productInfo);
        map.put("msg", ResultEnum.SUCCESS.getMessage());
        map.put("returnUrl","/sell/product/list");
        return new ModelAndView("common/success",map);
    }

    @GetMapping("/offSale")
    public ModelAndView offSale(@RequestParam("productId") String productId, Map<String,Object> map){
        ProductInfo productInfo;
        try {
            productInfo= productInfoService.offSale(productId);
        }catch (Sellexception e){
            map.put("msg", e.getMessage());
            map.put("returnUrl","/sell/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("productInfo",productInfo);
        map.put("msg", ResultEnum.SUCCESS.getMessage());
        map.put("returnUrl","/sell/product/list");
        return new ModelAndView("common/success",map);
    }
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId") String productId, Map<String,Object> map) {
        ProductInfo productInfo = productInfoService.findOne(productId);
        map.put("productInfo",productInfo);

        List<ProductCategory> categories = categoryService.findAll();
        map.put("categories",categories);

        return new ModelAndView("product/index",map);
    }
}
