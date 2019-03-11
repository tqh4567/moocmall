package com.study.mall.service.impl;
import com.study.mall.dataObject.ProductInfo;
import com.study.mall.dto.CartDTO;
import com.study.mall.enums.ProductStatusEnum;
import com.study.mall.enums.ResultEnum;
import com.study.mall.exception.Sellexception;
import com.study.mall.repository.ProductInfoRepository;
import com.study.mall.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        return repository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    public void increaseStock(List<CartDTO> cartDTOs) {
        for (CartDTO cart:cartDTOs) {
            ProductInfo productInfo=repository.findOne(cart.getProductId());
            if(productInfo==null){
                throw new Sellexception(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result=productInfo.getProductStock()+cart.getProductQuantity();

            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    @Override
    public void decreaseStock(List<CartDTO> cartDTOs) {
        for (CartDTO cart:cartDTOs) {
            ProductInfo productInfo=repository.findOne(cart.getProductId());
            if(productInfo==null){
                throw new Sellexception(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result=productInfo.getProductStock()-cart.getProductQuantity();
            if(result<0){
                throw new Sellexception(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    /**
     * 商品上架
     * @param productId
     * @return
     */
    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = repository.findOne(productId);
        if(productInfo==null){
            throw new Sellexception(ResultEnum.PRODUCT_NOT_EXIST);

        }
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        repository.save(productInfo);
        return productInfo;
    }

    /**
     * 商品下架
     * @param productId
     * @return
     */
    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = repository.findOne(productId);
        if(productInfo==null){
            throw new Sellexception(ResultEnum.PRODUCT_NOT_EXIST);

        }
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        repository.save(productInfo);
        return productInfo;
    }
}
