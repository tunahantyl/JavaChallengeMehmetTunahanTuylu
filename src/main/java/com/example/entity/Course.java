package com.example.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "courses")
@JsonIgnoreProperties({"cartItems"})
public class Course extends BaseEntity {
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private BigDecimal price;
    
    @Column(nullable = false)
    private int maxStudents;
    
    @Column(nullable = false)
    private int currentStudents;
    
    @Column(nullable = false)
    private boolean active = true;
    
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    @JsonIgnoreProperties({"courses"})
    private Teacher teacher;
    
    @ManyToMany(mappedBy = "enrolledCourses")
    @JsonIgnore
    private List<Student> students;
    
    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private List<CartItem> cartItems;
    
    public Course() {}
    
    public Course(String title, String description, BigDecimal price, int maxStudents, Teacher teacher) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.maxStudents = maxStudents;
        this.currentStudents = 0;
        this.teacher = teacher;
        this.active = true;
    }
    
    public boolean canAddToCart() {
        return active && currentStudents < maxStudents;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public int getMaxStudents() {
        return maxStudents;
    }
    
    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }
    
    public int getCurrentStudents() {
        return currentStudents;
    }
    
    public void setCurrentStudents(int currentStudents) {
        this.currentStudents = currentStudents;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public Teacher getTeacher() {
        return teacher;
    }
    
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    
    public List<Student> getStudents() {
        return students;
    }
    
    public void setStudents(List<Student> students) {
        this.students = students;
    }
    
    public List<CartItem> getCartItems() {
        return cartItems;
    }
    
    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
