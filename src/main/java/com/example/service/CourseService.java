package com.example.service;

import com.example.entity.Course;
import com.example.entity.Teacher;
import com.example.repository.CourseRepository;
import com.example.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private TeacherRepository teacherRepository;
    
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    
    public List<Course> getAllCoursesForTeacher(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
        if (teacher != null) {
            return courseRepository.findByTeacher(teacher);
        }
        return List.of();
    }
    
    public Course createCourseForTeacher(Long teacherId, Course course) {
        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
        if (teacher != null) {
            course.setTeacher(teacher);
            course.setActive(true);
            course.setCurrentStudents(0);
            return courseRepository.save(course);
        }
        return null;
    }
    
    public Course updateCourseForTeacher(Long courseId, Course course) {
        if (courseRepository.existsById(courseId)) {
            course.setId(courseId);
            return courseRepository.save(course);
        }
        return null;
    }
    
    public void deleteCourseForTeacher(Long courseId) {
        courseRepository.deleteById(courseId);
    }
    
    public List<Course> getCoursesForStudent(Long studentId) {
        return courseRepository.findAll().stream()
                .filter(Course::isActive)
                .toList();
    }
    
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }
    
    public boolean enrollStudentInCourse(Long courseId, Long studentId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course != null && course.canAddToCart()) {
            course.setCurrentStudents(course.getCurrentStudents() + 1);
            courseRepository.save(course);
            return true;
        }
        return false;
    }
}
