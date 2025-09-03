package com.example.controller;

import com.example.entity.Course;
import com.example.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    
    @Autowired
    private CourseService courseService;
    
    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }
    
    @GetMapping("/teacher/{teacherId}")
    public List<Course> getAllCoursesForTeacher(@PathVariable Long teacherId) {
        return courseService.getAllCoursesForTeacher(teacherId);
    }
    
    @PostMapping("/teacher/{teacherId}")
    public ResponseEntity<Course> createCourseForTeacher(@PathVariable Long teacherId, @RequestBody Course course) {
        Course createdCourse = courseService.createCourseForTeacher(teacherId, course);
        if (createdCourse != null) {
            return ResponseEntity.ok(createdCourse);
        }
        return ResponseEntity.badRequest().build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourseForTeacher(@PathVariable Long id, @RequestBody Course course) {
        Course updatedCourse = courseService.updateCourseForTeacher(id, course);
        if (updatedCourse != null) {
            return ResponseEntity.ok(updatedCourse);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourseForTeacher(@PathVariable Long id) {
        courseService.deleteCourseForTeacher(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/student/{studentId}")
    public List<Course> getCoursesForStudent(@PathVariable Long studentId) {
        return courseService.getCoursesForStudent(studentId);
    }
}
