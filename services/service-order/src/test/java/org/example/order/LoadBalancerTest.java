package org.example.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

@SpringBootTest
public class LoadBalancerTest {
    @Autowired
    LoadBalancerClient loadBalancerClient;
    DiscoveryClient discoveryClient;

    @Test
    void test(){
        // discoveryClient会获取所有服务实例
//        discoveryClient.getInstances("service-product");

        // 按照轮询规则的负载均衡
        ServiceInstance choose = loadBalancerClient.choose("service-product");
        System.out.println("choose = " + choose.getHost() + ": "+choose.getPort());
        choose = loadBalancerClient.choose("service-product");
        System.out.println("choose = " + choose.getHost() + ": "+choose.getPort());
        choose = loadBalancerClient.choose("service-product");
        System.out.println("choose = " + choose.getHost() + ": "+choose.getPort());
        choose = loadBalancerClient.choose("service-product");
        System.out.println("choose = " + choose.getHost() + ": "+choose.getPort());
    }
}
