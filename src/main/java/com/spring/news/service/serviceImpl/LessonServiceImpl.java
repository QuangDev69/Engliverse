package com.spring.news.service.serviceImpl;

import com.spring.news.domain.Lesson;

import com.spring.news.repository.LessonRepository;
import com.spring.news.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public Lesson save(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    @Override
    public Lesson getLessonById(Integer id) {
        return lessonRepository.findById(id).orElse(null);
    }

    @Override
    public Lesson updateLesson(Lesson lesson) {
        Lesson existLesson = lessonRepository.findById(lesson.getId()).orElse(null);
        if (existLesson != null) {
            existLesson.setName(lesson.getName());
            existLesson.setDescription(lesson.getDescription());
            existLesson.setCode(lesson.getCode());
            existLesson.setContent(lesson.getContent());
            return lessonRepository.save(existLesson);
        }
        return null;
    }


    @Override
    public List<Lesson> findLessonsByCourseId(Integer courseId) {
        return lessonRepository.findByCourse_CourseId(courseId);
    }

}
