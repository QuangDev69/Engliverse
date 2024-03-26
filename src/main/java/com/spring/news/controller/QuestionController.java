package com.spring.news.controller;

import com.spring.news.domain.Course;
import com.spring.news.domain.Lesson;
import com.spring.news.domain.Option;
import com.spring.news.domain.Question;
import com.spring.news.service.CourseService;
import com.spring.news.service.LessonService;
import com.spring.news.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/courses/{courseId}/lessons/{lessonId}")

public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/add-question")
    public String showQuestionForm(Model model,
                                   @PathVariable("lessonId") int lessonId,
                                   @PathVariable("courseId") Integer courseId) {
        Lesson lesson = lessonService.getLessonById(lessonId);
        Course course = courseService.getCourseById(courseId);
        Question question = new Question();
        question.setOptions(new ArrayList<>());
        model.addAttribute("question", question);
        model.addAttribute("lesson", lesson);
        model.addAttribute("course", course);
        return "question/create-question";
    }

    @PostMapping("/add-question")
    public String addQuestion(@ModelAttribute Question question,
                              @PathVariable Integer courseId, @PathVariable Integer lessonId, RedirectAttributes redirectAttributes) {
        // Đặt lesson cho question dựa trên lessonId nhận được
        Lesson lesson = lessonService.getLessonById(lessonId);
        question.setLesson(lesson);

        // Lưu question cùng với các options của nó
        questionService.createQuestionWithOptions(question, question.getOptions());

        redirectAttributes.addFlashAttribute("message", "Question added successfully.");
        return String.format("redirect:/courses/%d/lessons/%d", courseId, lessonId);
    }

//    @PostMapping("/checkAnswers")
//    public String checkAnswers(@PathVariable Integer lessonId,
//                               @PathVariable Integer courseId, @RequestParam Map<String, String> allParams, Model model,
//                               RedirectAttributes redirectAttributes) {
//        Lesson lesson = lessonService.getLessonById(lessonId);
//        List<Question> questions = questionService.getQuestionsByLessonId(lessonId);
//        System.out.println("----- Answerr --------: "+questionService.getQuestionsByLessonId(lessonId));
//        Map<Integer, Boolean> results = new HashMap<>();
//        Map<String, String> userAnswers = new HashMap<>();
//
//        for (Question question : questions) {
//            String answerParam = "answer_" + question.getId();
//
//            Integer selectedOptionId = Integer.parseInt(allParams.getOrDefault("answer_" + question.getId(), "0"));
//            boolean isCorrect = question.getOptions().stream()
//                    .filter(Option::getIsCorrect)
//                    .anyMatch(option -> option.getId().equals(selectedOptionId));
//
//            results.put(question.getId(), isCorrect);
//            userAnswers.put(answerParam, selectedOptionId.toString());
//
//        }
//        redirectAttributes.addFlashAttribute("results", results);
//        redirectAttributes.addFlashAttribute("userAnswers", userAnswers);
//
//        model.addAttribute("lesson", lesson);
//        model.addAttribute("questions", questions);
//
//        return String.format("redirect:/courses/%d/lessons/%d", courseId, lessonId);
//    }





    @GetMapping("/resetLesson")
    public String resetLesson(@PathVariable("courseId") Integer courseId,
                              @PathVariable("lessonId") Integer lessonId, Model model) {

        return String.format("redirect:/courses/%d/lessons/%d", courseId, lessonId);
    }


}
