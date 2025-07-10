package org.example.order.service;


import org.example.order.bean.Order;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    Order createOrder(Long userId, Long productId);
}
