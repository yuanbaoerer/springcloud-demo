package org.example.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.example.order.bean.Order;
import org.example.order.properties.OrderProperties;
import org.example.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderProperties orderProperties;

    @GetMapping("/config")
    public String config(){
        return "order.timeout:" + orderProperties.getTimeout() +
                " order.auto-confirm:" + orderProperties.getAutoConfirm() +
                " order.db-url="+orderProperties.getDbUrl();
    }

    @GetMapping("/create")
    public Order createOrder(@RequestParam("userId") Long userId,
                             @RequestParam("productId") Long productId) {
        Order order = orderService.createOrder(userId, productId);
        return order;
    }

    @GetMapping("/seckill")
    @SentinelResource(value = "seckill", fallback = "seckillFallback")
    public Order seckill(@RequestParam("userId") Long userId,
                             @RequestParam("productId") Long productId) {
        Order order = orderService.createOrder(userId, productId);
        order.setId(Long.MAX_VALUE);
        order.setNickName("秒杀用户");
        return order;
    }

    public Order seckillFallback(Long userId,
                                 Long productId, BlockException exception) {
        System.out.println("seckillFallback....兜底回调");
        Order order = new Order();
        order.setId(productId);
        order.setUserId(userId);
        order.setAddress("异常信息："+exception.getClass());

        return order;
    }

    @GetMapping("/writeDb")
    public String writeDb(){
        return "writeDb successsss...";
    }
    @GetMapping("/readDb")
    public String readDb(){
        log.info("readDb...");
        return "readDb successsss...";
    }
}
