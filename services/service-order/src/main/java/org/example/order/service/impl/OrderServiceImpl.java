package org.example.order.service.impl;

import org.example.order.bean.Order;
import org.example.order.service.OrderService;
import org.example.product.bean.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired // 一定导入 spring-cloud-starter-loadbalancer依赖
    LoadBalancerClient loadBalancerClient;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public Order createOrder(Long userId, Long productId) {
        Product product = getProductFromRemoteWithLoadBalance(productId);
        Order order = new Order();
        order.setId(1L);
        // 总金额
        order.setTotalAmount(product.getPrice().multiply(new BigDecimal(product.getNum())));
        order.setUserId(userId);
        order.setNickName("张三");
        order.setAddress("禾澧书店");
        // 远程查询商品列表
        order.setProductList(Arrays.asList(product));

        return order;
    }

    // 目前的发送请求只针对一个机器发送请求，只有当这个机器宕机了，才会换机器发送请求，未实现负载均衡
    private Product getProductFromRemote(Long productId) {
        // 1、获取到商品服务所在的所有机器IP+port
        List<ServiceInstance> instances = discoveryClient.getInstances("service-product");
        ServiceInstance instance = instances.get(0);
        // 拼接远程URL
        String url = "http://"+instance.getHost()+":"+instance.getPort()+"/product/"+productId;
        System.out.println("远程请求："+url);
        // 2、给远程发送请求
        Product product = restTemplate.getForObject(url, Product.class);
        return product;
    }
    // 完成负载均衡发送请求
    private Product getProductFromRemoteWithLoadBalance(Long productId) {
        // 1、获取到商品服务所在的所有机器IP+port
//        List<ServiceInstance> instances = discoveryClient.getInstances("service-product");
        ServiceInstance choose = loadBalancerClient.choose("service-product");

        ServiceInstance instance = choose;
        // 拼接远程URL
        String url = "http://"+instance.getHost()+":"+instance.getPort()+"/product/"+productId;
        System.out.println("远程请求："+url);
        // 2、给远程发送请求
        Product product = restTemplate.getForObject(url, Product.class);
        return product;
    }
}
