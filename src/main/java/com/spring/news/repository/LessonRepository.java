package com.spring.news.repository;

import com.spring.news.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  LessonRepository extends JpaRepository<Lesson, Integer> {
    List<Lesson> findByCourse_CourseId(Integer courseId);

    
}
