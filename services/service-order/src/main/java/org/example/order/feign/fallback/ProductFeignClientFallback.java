package org.example.order.feign.fallback;
import java.math.BigDecimal;

import org.example.order.feign.ProductFeignClient;
import org.example.product.bean.Product;
import org.springframework.stereotype.Component;

/**
 * 兜底回调：当远程调用失败了，就调用Fallback
 */
@Component  // 也需要加入IOC容器才能生效
public class ProductFeignClientFallback implements ProductFeignClient {
    @Override
    public Product getProductById(Long id) {
        System.out.println("ProductFeignClientFallback ....");
        Product product = new Product();
        product.setId(0L);
        product.setPrice(new BigDecimal("0"));
        product.setProductName("");
        product.setNum(0);

        return product;
    }

    @Override
    public Product getProduct(Long productId) {
        System.out.println("ProductFeignClientFallback ....");
        return null;
    }
}
