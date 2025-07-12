package org.example.order.config;

import feign.Logger;
import feign.Retryer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OrderConfig {

    @Bean
    Retryer retryer(){
        return new Retryer.Default();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * 给远程发送请求使用，线程安全的，全局只有一个即可
     * @return
     */
    @LoadBalanced // 注解式负载均衡
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
