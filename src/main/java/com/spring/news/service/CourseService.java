package com.spring.news.service;

import com.spring.news.domain.Course;
import com.spring.news.domain.Level;
import com.spring.news.domain.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {

    List<Course> findAllCourses();

    Page<Course> findAll(Pageable pageable);

    Course saveCourseWithTopicsAndLevels(Course course, List<Integer> topicIds, List<Integer> levelIds);

    Course getCourseById(int courseId);

    Course updateCourse(Course course,  List<Integer> topicIds, List<Integer> levelIds);

     List<Topic> getAllTopics();

     List<Level> getAllLevels() ;

    Page<Course> findCourses(String keyword, Integer levelId, Integer topicId, Pageable pageable);
}
