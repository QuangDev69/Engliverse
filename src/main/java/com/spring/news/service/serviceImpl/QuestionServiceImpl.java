package com.spring.news.service.serviceImpl;

import com.spring.news.domain.Option;
import com.spring.news.domain.Question;
import com.spring.news.repository.OptionRepository;
import com.spring.news.repository.QuestionRepository;
import com.spring.news.service.QuestionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    @Transactional
    public Question createQuestionWithOptions(Question question, List<Option> options) {
        boolean hasCorrectOption = options.stream().anyMatch(Option::getIsCorrect);
        if (!hasCorrectOption) {
            throw new RuntimeException("At least one option must be marked as correct.");
        }

        question.setOptions(new ArrayList<>()); // Đảm bảo rằng danh sách options không null
        for (Option option : options) {
            option.setQuestion(question); // Liên kết option với question
            question.getOptions().add(option);
        }

        return questionRepository.save(question); // Lưu question và các options của nó
    }

    @Override
    public List<Question> getQuestionsByLessonId(Integer lessonId) {
        return questionRepository.findByLessonId(lessonId);
    }

    @Override
    public boolean isCorrectAnswer(Integer questionId, Integer optionId) {
        Option option = optionRepository.findById(optionId).orElse(null);
        return option != null && option.getIsCorrect() && option.getQuestion().getId().equals(questionId);
    }

    @Override
    public Question getQuestionById( Integer questionId) {
        return questionRepository.findById(questionId).orElse(null);
    }

    @Override
    @Transactional
    public Question updateQuestionById(Question question, Integer questionId) {
        // Kiểm tra xem câu hỏi có tồn tại trong cơ sở dữ liệu hay không
        Question questionExist = questionRepository.findById(questionId).orElse(null);
        if (questionExist == null) {
            throw new RuntimeException("Question with id " + questionId + " not found.");
        }

        // Cập nhật nội dung của câu hỏi
        questionExist.setContent(question.getContent());

        // Kiểm tra xem người dùng đã cập nhật tùy chọn mới nào không
        List<Option> updatedOptions = new ArrayList<>();
        for (Option option : question.getOptions()) {
            // Nếu tùy chọn không có ID (tùy chọn mới được thêm), hoặc nếu ID của tùy chọn không trùng khớp với danh sách tùy chọn hiện có
            if (option.getId() == null || !questionExist.getOptions().contains(option)) {
                option.setQuestion(questionExist); // Liên kết option với question
                updatedOptions.add(option);
            }
        }

        // Nếu có tùy chọn mới, cập nhật danh sách tùy chọn của câu hỏi
        if (!updatedOptions.isEmpty()) {
            questionExist.getOptions().addAll(updatedOptions);
        }

        // Lưu và trả về câu hỏi đã được cập nhật
        return questionRepository.save(questionExist);
    }



}
