package org.example.product.service.impl;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.example.product.bean.Product;
import org.example.product.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public Product getProductById(Long productId) {
        Product product = new Product();
        product.setId(productId);
        product.setPrice(new BigDecimal("99"));
        product.setProductName("苹果-"+productId);
        product.setNum(2);

        // 测试指定休眠100秒，测试Feign的超时等待，默认是等待60秒
//        try {
//            TimeUnit.SECONDS.sleep(100);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }


        return product;
    }
}
