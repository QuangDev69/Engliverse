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
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
        question.setOptions(new ArrayList<>());
        for (Option option : options) {
            option.setQuestion(question);
            question.getOptions().add(option);
        }
        return questionRepository.save(question);
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

//    @Override
//    @Transactional
//// Đánh dấu phương thức này để được quản lý bởi Spring's transaction management,
//// đảm bảo tính nhất quán của dữ liệu.
//    public Question updateQuestionById(Question updatedQuestion, Integer questionId) {
//        // Tìm `Question` hiện tại trong cơ sở dữ liệu dựa trên `questionId` được cung cấp.
//        Question existingQuestion = questionRepository.findById(questionId)
//                .orElseThrow(() -> new RuntimeException("Question not found with id: " + questionId));
//        // Nếu không tìm thấy `Question`, một ngoại lệ sẽ được ném ra.
//
//        // Cập nhật nội dung của `Question` từ dữ liệu được cung cấp trong `updatedQuestion`.
//        existingQuestion.setContent(updatedQuestion.getContent());
//
//        // Tạo một Map từ ID của mỗi `Option` hiện có đến chính `Option` đó.
//        // Điều này giúp dễ dàng tra cứu và cập nhật các `Option` dựa trên ID của chúng.
//        Map<Integer, Option> existingOptionsMap = existingQuestion.getOptions().stream()
//                .collect(Collectors.toMap(Option::getId, option -> option));
//
//        // Chuẩn bị một danh sách mới để lưu trữ các `Option` sau khi đã cập nhật.
//        List<Option> updatedOptions = new ArrayList<>();
//
//        // Duyệt qua từng `Option` trong danh sách các `Option` mới được cung cấp.
//        for (Option updatedOption : updatedQuestion.getOptions()) {
//            if (updatedOption.getId() != null && existingOptionsMap.containsKey(updatedOption.getId())) {
//                // Nếu `Option` có ID và ID đó tồn tại trong Map của các `Option` hiện tại,
//                // thì cập nhật nội dung của `Option` đó từ dữ liệu mới.
//                Option existingOption = existingOptionsMap.get(updatedOption.getId());
//                existingOption.setContent(updatedOption.getContent());
//                updatedOptions.add(existingOption);
//                // Thêm `Option` đã cập nhật vào danh sách mới.
//            } else if (updatedOption.getId() == null) {
//                // Nếu `Option` mới không có ID, coi đó là một `Option` mới
//                // và thiết lập mối quan hệ với `Question` hiện tại.
//                updatedOption.setQuestion(existingQuestion);
//                updatedOptions.add(updatedOption);
//                // Thêm `Option` mới vào danh sách mới.
//            }
//        }
//        // Cập nhật danh sách `Option` của `Question` với danh sách mới đã được cập nhật.
//        existingQuestion.setOptions(updatedOptions);
//
//        // Lưu và trả về `Question` đã được cập nhật vào cơ sở dữ liệu.
//        return questionRepository.save(existingQuestion);
//    }


    @Override
    @Transactional
    public Question updateQuestionById(Question question, Integer questionId){
        Question exitstingQuestion = questionRepository.findById(questionId).orElse(null);
        // Cap nhat noi dung question
        exitstingQuestion.setContent(question.getContent());


        //Tao 1 Map luu tru cac Option hien tai cua question
        Map<Integer, Option> existingOptionMap =
                exitstingQuestion.getOptions().stream().collect(Collectors.toMap(Option::getId, option->option));

        //Tao 1 List de luu danh sach cac option moi.
        List<Option> updateOption = new ArrayList<>();

        for(Option option : question.getOptions()){
            if(option.getId()!=null && existingOptionMap.containsKey(option.getId())){
                System.out.println("option.getIsCorrect(): "+option.getId()+ " " +option.getIsCorrect());
                Option existingOption = existingOptionMap.get(option.getId());
                existingOption.setContent(option.getContent());
                existingOption.setIsCorrect(option.getIsCorrect());
                updateOption.add(existingOption);
            }else if(option.getId() == null){
                option.setQuestion(exitstingQuestion);
                updateOption.add(option);
            }
        }
        exitstingQuestion.setOptions(updateOption);
        return questionRepository.save(exitstingQuestion);
    }

    @Override
    public void deleteQuestionById(Integer questionId) {
        questionRepository.deleteById(questionId);
    }
}
