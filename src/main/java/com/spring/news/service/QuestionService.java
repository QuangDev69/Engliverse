package com.spring.news.service;

import com.spring.news.domain.Option;
import com.spring.news.domain.Question;

import java.util.Set;

public interface QuestionService {
    public Question createQuestionWithOptions(Question question, Set<Option> options);

}
