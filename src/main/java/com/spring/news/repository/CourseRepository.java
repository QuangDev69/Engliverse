package com.spring.news.repository;

import com.spring.news.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    // Custom query methods if needed
}