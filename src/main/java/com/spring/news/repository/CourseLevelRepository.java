package com.spring.news.repository;

import com.spring.news.domain.Course;
import com.spring.news.domain.CourseLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseLevelRepository extends JpaRepository<CourseLevel, Integer> {
    void deleteByCourse(Course course);
}
