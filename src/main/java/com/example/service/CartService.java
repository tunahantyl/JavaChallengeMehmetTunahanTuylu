package com.example.service;

import com.example.entity.*;
import com.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Transactional(readOnly = true)
    public Cart getCart(Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student != null) {
            Cart cart = cartRepository.findByStudent(student);
            if (cart != null) {
                recalculateTotal(cart);
            }
            return cart;
        }
        return null;
    }
    
    @Transactional
    public Cart updateCart(Long studentId) {
        Cart cart = getCart(studentId);
        if (cart != null) {
            recalculateTotal(cart);
            return cartRepository.save(cart);
        }
        return null;
    }
    
    @Transactional
    public void emptyCart(Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student != null) {
            Cart cart = cartRepository.findByStudent(student);
            if (cart != null) {
                cartItemRepository.deleteByCart(cart);
                cart.setTotalPrice(BigDecimal.ZERO);
                cartRepository.save(cart);
            }
        }
    }
    
    @Transactional
    public boolean addCourseToCart(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);
        
        if (student != null && course != null) {
            if (!course.canAddToCart()) {
                return false;
            }
            
            Cart cart = cartRepository.findByStudent(student);
            if (cart == null) {
                cart = new Cart(student);
                cartRepository.save(cart);
            }
            
            CartItem cartItem = new CartItem(cart, course, course.getPrice());
            cartItemRepository.save(cartItem);
            recalculateTotal(cart);
            cartRepository.save(cart);
            return true;
        }
        return false;
    }
    
    @Transactional
    public void removeCourseFromCart(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student != null) {
            Cart cart = cartRepository.findByStudent(student);
            if (cart != null) {
                List<CartItem> items = cartItemRepository.findByCart(cart);
                for (CartItem item : items) {
                    if (item.getCourse().getId().equals(courseId)) {
                        cartItemRepository.delete(item);
                    }
                }
                recalculateTotal(cart);
                cartRepository.save(cart);
            }
        }
    }
    
    private void recalculateTotal(Cart cart) {
        List<CartItem> items = cartItemRepository.findByCart(cart);
        BigDecimal total = items.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(total);
    }
}
