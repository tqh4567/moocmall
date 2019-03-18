package com.study.mall.controller;

import com.study.mall.dataObject.ProductCategory;
import com.study.mall.dataObject.ProductInfo;
import com.study.mall.enums.ResultEnum;
import com.study.mall.exception.Sellexception;
import com.study.mall.service.ProductCategoryService;
import com.study.mall.service.ProductInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "卖家商品模块",description = "卖家商品部分的功能模块")
public class SellProductController {
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductCategoryService categoryService;

    @GetMapping("/list")
    @ApiOperation(value = "查询商品列表",notes = "获取商品的列表")
    public ModelAndView findAll(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "size",defaultValue = "3")Integer size, Map<String,Object> map){
        PageRequest pageRequest=new PageRequest(page-1,size);

        Page<ProductInfo> productInfos = productInfoService.findAll(pageRequest);
        map.put("productInfos",productInfos);
        map.put("currentPage",page);
        map.put("size",size);

        return new ModelAndView("product/list",map);

    }
    @GetMapping("/update")
    @ApiOperation(value = "更新商品信息",notes = "更新商品的信息")
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
    @ApiOperation(value = "商品上架",notes = "上架商品")
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
    @ApiOperation(value = "商品下架",notes = "下架上商品")
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
