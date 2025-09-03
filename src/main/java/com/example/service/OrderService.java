package com.example.service;

import com.example.entity.*;
import com.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Transactional
    public Order placeOrder(Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) return null;
        Cart cart = cartService.getCart(studentId);
        if (cart == null) return null;
        
        List<CartItem> items = cartItemRepository.findByCart(cart);
        if (items == null || items.isEmpty()) return null;
        
        String orderCode = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Order order = new Order(orderCode, student, cart.getTotalPrice());
        order = orderRepository.save(order);
        
        for (CartItem cartItem : items) {
            OrderItem orderItem = new OrderItem(order, cartItem.getCourse(), cartItem.getPrice());
            orderItemRepository.save(orderItem);
        }
        
        cartService.emptyCart(studentId);
        return order;
    }
    
    @Transactional(readOnly = true)
    public Order getOrderForCode(String orderCode) {
        return orderRepository.findByOrderCode(orderCode);
    }
    
    @Transactional(readOnly = true)
    public List<Order> getAllOrdersForCustomer(Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student != null) {
            return orderRepository.findByStudent(student);
        }
        return List.of();
    }
}
