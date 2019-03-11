package com.study.mall.service;

import com.study.mall.dataObject.ProductInfo;
import com.study.mall.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInfoService {
    ProductInfo findOne(String productId);

    /**
     * 查询所有在架商品列表
     * @return
     */
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOs);

    //减库存
    void decreaseStock(List<CartDTO> cartDTOs);

//    上架
    ProductInfo onSale(String productId);
//    下架
    ProductInfo offSale(String productId);
}
