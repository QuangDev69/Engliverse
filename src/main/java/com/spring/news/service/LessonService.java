package com.spring.news.service;

import com.spring.news.domain.Lesson;

import java.util.List;

public interface LessonService {
    Lesson save(Lesson lesson);
    Lesson getLessonById(Integer id);
    Lesson updateLesson(Lesson lesson);
    List<Lesson> findLessonsByCourseId(Integer courseId);

}
