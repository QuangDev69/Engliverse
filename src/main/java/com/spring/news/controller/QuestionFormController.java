package com.spring.news.controller;
import com.spring.news.domain.Course;
import com.spring.news.domain.Lesson;
import com.spring.news.domain.Option;
import com.spring.news.domain.Question;
import com.spring.news.service.CourseService;
import com.spring.news.service.LessonService;
import com.spring.news.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;


@Controller

public class QuestionFormController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private CourseService courseService;

    @GetMapping("courses/{courseId}/lessons/{lessonId}/add-question")
    public String showQuestionForm(Model model, @PathVariable("lessonId") int lessonId, @PathVariable("courseId") Integer courseId) {
        Lesson lesson = lessonService.getLessonById(lessonId);
        Course course = courseService.getCourseById(courseId);
        Question question = new Question();
        question.setOptions(new HashSet<>());
        question.getOptions().add(new Option());
        question.getOptions().add(new Option());

        model.addAttribute("question", question);
        model.addAttribute("lesson", lesson);
        model.addAttribute("course", course);
        return "question/create-question";
    }

    @PostMapping("courses/{courseId}/lessons/{lessonId}/add-question")
    public String submitQuestionForm(Question question, @PathVariable("lessonId") Integer lessonId,
                                     @PathVariable("courseId") Integer coursesId,Model model) {
        // Tìm lesson bằng ID
        Lesson lesson = lessonService.getLessonById(lessonId);
        if (lesson != null) {
            question.setLesson(lesson); // Gán lesson cho câu hỏi
            questionService.createQuestionWithOptions(question, question.getOptions());
            return "redirect:courses/"+coursesId+ "/lessons/"+lessonId+"/add-question"; // Hoặc một URL khác phù hợp
        } else {
            // Xử lý trường hợp không tìm thấy lesson
            return "errorPage"; // Ví dụ: trả về một trang lỗi
        }
    }

}
