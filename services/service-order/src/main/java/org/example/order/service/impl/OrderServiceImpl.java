package org.example.order.service.impl;

import org.example.order.bean.Order;
import org.example.order.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public Order createOrder(Long userId, Long productId) {
        Order order = new Order();
        order.setId(1L);
        //TODO 总金额
        order.setTotalAmount(new BigDecimal("0"));
        order.setUserId(userId);
        order.setNickName("张三");
        order.setAddress("禾澧书店");
        //TODO 远程查询商品列表
        order.setProductList(null);

        return order;
    }
}
