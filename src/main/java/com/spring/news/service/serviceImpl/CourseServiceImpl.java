package com.spring.news.service.serviceImpl;

import com.spring.news.domain.Course;
import com.spring.news.domain.Level;
import com.spring.news.domain.Topic;
import com.spring.news.domain.User;
import com.spring.news.repository.CourseRepository;
import com.spring.news.repository.LevelRepository;
import com.spring.news.repository.TopicRepository;
import com.spring.news.service.CourseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private LevelRepository levelRepository;

    @Override
    @Transactional
    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Page<Course> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Course saveCourseWithTopicsAndLevels(Course course, List<Integer> topicId, List<Integer> levelId) {
        Set<Topic> topics = new HashSet<>(topicRepository.findAllById(topicId));
        Set<Level> levels = new HashSet<>(levelRepository.findAllById(levelId));
        course.setTopics(topics);
        course.setLevels(levels);
        return courseRepository.save(course);
    }

    @Override
    public Course getCourseById(int courseId) {
        return courseRepository.findById(courseId).orElse(null);
    }

    @Override
    public Course updateCourse(Course courseUpdate,  List<Integer> topicIds, List<Integer> levelIds) {
        Course existCourse = courseRepository.findById(courseUpdate.getCourseId()).orElse(null);
        if (existCourse != null) {
            existCourse.setCourseName(courseUpdate.getCourseName());
            existCourse.setCourseDes(courseUpdate.getCourseDes());
            existCourse.setImagePath(courseUpdate.getImagePath());
            // Xử lý topics
            Set<Topic> topics = topicIds.stream()
                    .map(id -> topicRepository.findById(id).orElse(null))
                    .collect(Collectors.toSet());
            existCourse.setTopics(topics);

            // Xử lý levels
            Set<Level> levels = levelIds.stream()
                    .map(id -> levelRepository.findById(id).orElse(null))
                    .collect(Collectors.toSet());
            existCourse.setLevels(levels);

            return courseRepository.save(existCourse);
        }
        return null;
    }

    @Override
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }
    
    @Override
    public List<Level> getAllLevels() {
        return levelRepository.findAll();
    }

    @Override

    public Page<Course> findCourses(String keyword, Integer levelId, Integer topicId, Pageable pageable) {
        return courseRepository.findCoursesByCriteria(keyword, levelId, topicId, pageable);
    }

}
