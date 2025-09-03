package com.example.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "carts")
public class Cart extends BaseEntity {
    
    @OneToOne
    @JoinColumn(name = "student_id", nullable = false)
    @JsonIgnoreProperties({"enrolledCourses", "orders", "cart"})
    private Student student;
    
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"cart"})
    private List<CartItem> items;
    
    @Column(nullable = false)
    private BigDecimal totalPrice = BigDecimal.ZERO;
    
    public Cart() {}
    
    public Cart(Student student) {
        this.student = student;
        this.totalPrice = BigDecimal.ZERO;
    }
    
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public List<CartItem> getItems() {
        return items;
    }
    
    public void setItems(List<CartItem> items) {
        this.items = items;
    }
    
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
