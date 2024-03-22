package com.spring.news.service.serviceImpl;

import com.spring.news.domain.Option;
import com.spring.news.domain.Question;
import com.spring.news.repository.OptionRepository;
import com.spring.news.repository.QuestionRepository;
import com.spring.news.service.QuestionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, OptionRepository optionRepository) {
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
    }

    @Transactional
    public Question createQuestionWithOptions(Question question, Set<Option> options) {
        // Lưu câu hỏi
        Question savedQuestion = questionRepository.save(question);

        // Lưu từng lựa chọn và thiết lập quan hệ với câu hỏi đã lưu
        options.forEach(option -> {
            option.setQuestion(savedQuestion);
            optionRepository.save(option);
        });

        // Thiết lập các lựa chọn cho câu hỏi và trả về
        savedQuestion.setOptions(options);
        return savedQuestion;
    }
}
