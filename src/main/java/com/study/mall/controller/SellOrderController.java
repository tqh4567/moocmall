package com.study.mall.controller;

import com.study.mall.dto.OrderDTO;
import com.study.mall.enums.ResultEnum;
import com.study.mall.exception.Sellexception;
import com.study.mall.service.OrderMastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/sell/order")
public class SellOrderController {
    @Autowired
    private OrderMastService orderMastService;
    @GetMapping("/list")
    public ModelAndView findAll(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "size",defaultValue = "3")Integer size, Map<String,Object> map){
        PageRequest pageRequest=new PageRequest(page-1,size);

        Page<OrderDTO> orderDTOS = orderMastService.findAll(pageRequest);
        map.put("orderDTOS",orderDTOS);
        map.put("currentPage",page);
        map.put("size",size);

        return new ModelAndView("order/list",map);

    }
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam(value = "orderId") String orderId,Map<String,Object> map){
        try {
            OrderDTO orderDTO = orderMastService.findOne(orderId);
            orderMastService.cancel(orderDTO);
        }catch (Sellexception e){
            map.put("msg", e.getMessage());
            map.put("returnUrl","/sell/order/list");
            return new ModelAndView("common/error",map);
        }
//        orderMastService.cancel(orderDTO);
        map.put("msg", ResultEnum.SUCCESS.getMessage());
        map.put("returnUrl","/sell/order/list");
        return new ModelAndView("common/success",map);
    }
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam(value = "orderId") String orderId,Map<String,Object> map){
        OrderDTO orderDTO = new OrderDTO();
        try {
            orderDTO = orderMastService.findOne(orderId);
        }catch (Sellexception e){
            map.put("msg", e.getMessage());
            map.put("returnUrl","/sell/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("orderDTO",orderDTO);
        return new ModelAndView("order/detail",map);
    }

    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam(value = "orderId") String orderId,Map<String,Object> map){
        OrderDTO orderDTO = new OrderDTO();
        try {
            orderDTO = orderMastService.findOne(orderId);
            orderMastService.finish(orderDTO);
        }catch (Sellexception e){
            map.put("msg", e.getMessage());
            map.put("returnUrl","/sell/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("orderDTO",orderDTO);
        return new ModelAndView("common/success",map);
    }

}
