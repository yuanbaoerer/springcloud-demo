package org.example.order.feign;

import org.example.order.feign.fallback.ProductFeignClientFallback;
import org.example.product.bean.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

// fallback 指定远程调用失败后，用什么兜底
@FeignClient(value = "service-product", fallback = ProductFeignClientFallback.class)
public interface ProductFeignClient {

    //mvc注解的两套使用逻辑
    //1、标注在Controller上，是接收这样的请求
    //2、标注在FeignClient上，是发送这样的请求
    @GetMapping("/product/{id}")
    Product getProductById(@PathVariable("id") Long id);

    /**
     * 获取商品信息
     * @param productId
     * @return
     */
    @GetMapping("/product/{id}")
    Product getProduct(@PathVariable("id") Long productId);

}
