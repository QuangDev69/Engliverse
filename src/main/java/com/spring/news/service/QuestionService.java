package com.spring.news.service;

import com.spring.news.domain.Option;
import com.spring.news.domain.Question;

import java.util.List;
import java.util.Set;

public interface QuestionService {
    Question createQuestionWithOptions(Question question, List<Option> options);
    List<Question> getQuestionsByLessonId(Integer lessonId);
    boolean isCorrectAnswer(Integer questionId, Integer optionId);
    Question updateQuestionById (Question question, Integer questionId);

    Question getQuestionById ( Integer questionId);

    void deleteQuestionById(Integer questionId);

}
