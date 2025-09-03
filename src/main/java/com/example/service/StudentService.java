package com.example.service;

import com.example.entity.Student;
import com.example.entity.Cart;
import com.example.repository.StudentRepository;
import com.example.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private CartRepository cartRepository;
    
    public Student addCustomer(Student student) {
        Student savedStudent = studentRepository.save(student);
        Cart cart = new Cart(savedStudent);
        cartRepository.save(cart);
        return savedStudent;
    }
    
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }
}
