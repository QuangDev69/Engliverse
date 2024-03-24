package com.spring.news.service;

import com.spring.news.domain.Option;
import com.spring.news.domain.Question;

import java.util.List;
import java.util.Set;

public interface QuestionService {
    public Question createQuestionWithOptions(Question question, List<Option> options);
    public List<Question> getQuestionsByLessonId(Integer lessonId);
    boolean isCorrectAnswer(Integer questionId, Integer optionId);

}
