package com.example.controller;

import com.example.entity.Order;
import com.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @PostMapping("/{studentId}/place")
    public ResponseEntity<Order> placeOrder(@PathVariable Long studentId) {
        Order order = orderService.placeOrder(studentId);
        if (order != null) {
            return ResponseEntity.ok(order);
        }
        return ResponseEntity.badRequest().build();
    }
    
    @GetMapping("/code/{orderCode}")
    public ResponseEntity<Order> getOrderForCode(@PathVariable String orderCode) {
        Order order = orderService.getOrderForCode(orderCode);
        if (order != null) {
            return ResponseEntity.ok(order);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/customer/{studentId}")
    public ResponseEntity<List<Order>> getAllOrdersForCustomer(@PathVariable Long studentId) {
        List<Order> orders = orderService.getAllOrdersForCustomer(studentId);
        return ResponseEntity.ok(orders);
    }
}
