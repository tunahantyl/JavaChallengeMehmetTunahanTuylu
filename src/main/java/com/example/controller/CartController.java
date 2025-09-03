package com.example.controller;

import com.example.entity.Cart;
import com.example.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    @GetMapping("/{studentId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long studentId) {
        Cart cart = cartService.getCart(studentId);
        if (cart != null) {
            return ResponseEntity.ok(cart);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PutMapping("/{studentId}")
    public ResponseEntity<Cart> updateCart(@PathVariable Long studentId) {
        Cart cart = cartService.updateCart(studentId);
        if (cart != null) {
            return ResponseEntity.ok(cart);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> emptyCart(@PathVariable Long studentId) {
        cartService.emptyCart(studentId);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/{studentId}/add/{courseId}")
    public ResponseEntity<String> addCourseToCart(@PathVariable Long studentId, @PathVariable Long courseId) {
        boolean success = cartService.addCourseToCart(studentId, courseId);
        if (success) {
            return ResponseEntity.ok("Kurs sepete eklendi");
        }
        return ResponseEntity.badRequest().body("Kurs sepete eklenemedi");
    }
    
    @DeleteMapping("/{studentId}/remove/{courseId}")
    public ResponseEntity<Void> removeCourseFromCart(@PathVariable Long studentId, @PathVariable Long courseId) {
        cartService.removeCourseFromCart(studentId, courseId);
        return ResponseEntity.ok().build();
    }
}
