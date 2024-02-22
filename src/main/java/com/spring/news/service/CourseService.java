package com.spring.news.service;

import com.spring.news.domain.Course;

import java.util.List;

public interface CourseService {

    List<Course> findAllCourses();

    Course saveCourseWithTopicsAndLevels(Course course, List<Integer> topicIds, List<Integer> levelIds);

}
