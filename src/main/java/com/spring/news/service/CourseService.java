package com.spring.news.service;

import com.spring.news.domain.Course;
import com.spring.news.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {

    List<Course> findAllCourses();

    Page<Course> findAll(Pageable pageable);

    Course saveCourseWithTopicsAndLevels(Course course, List<Integer> topicIds, List<Integer> levelIds);

}
