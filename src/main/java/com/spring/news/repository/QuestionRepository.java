package com.spring.news.repository;

import com.spring.news.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByLessonId(Integer lessonId);



}
