package com.spring.news.controller;


import com.spring.news.domain.Course;
import com.spring.news.domain.Lesson;
import com.spring.news.security.SecurityService;
import com.spring.news.service.CourseService;
import com.spring.news.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/courses")
public class LessonController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/{courseId}/lessons/add")
    public String showLessonAdd(@PathVariable int courseId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
        if (isAdmin) {

        Course course = courseService.getCourseById(courseId);
        model.addAttribute("course", course);
        model.addAttribute("lesson", new Lesson());
        return "/lesson/create-lesson";
        }
        else {
            return "refuse";

        }
    }

    @PostMapping("/{courseId}/lessons/add")
    public String createLesson(@PathVariable int courseId,
                               @ModelAttribute("lesson") Lesson lesson,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (securityService.isAdmin()) {


            Course course = courseService.getCourseById(courseId);
            lesson.setCourse(course);
            System.out.println("Content: === " + lesson.getContent());

            if (course.getCourseId() != null) {
                boolean hasError = false;
                if (lesson.getName().isEmpty()) {
                    model.addAttribute("nameError", " *Please fill in the Lesson Name");
                    hasError = true;
                }

                if (lesson.getCode().isEmpty()) {
                    model.addAttribute("codeError", " *Please fill in the Code");
                    hasError = true;
                }

                if (lesson.getDescription().isEmpty()) {
                    model.addAttribute("desError", " *Please fill in the Description");
                    hasError = true;
                }

                boolean isDefaultContent = lesson.getContent().equals("<p><br></p>");

                if (lesson.getContent().isEmpty() || isDefaultContent) {
                    model.addAttribute("contentError", "*Please fill in the Content");
                    hasError = true;
                }

                // Nếu có lỗi, giữ lại giá trị của các trường đã nhập
                if (hasError) {
                    model.addAttribute("course", course);
                    return "/lesson/create-lesson";
                }
            }

            lessonService.save(lesson);
            redirectAttributes.addFlashAttribute("success", "Lesson added successfully!");
            return "redirect:/courses/" + courseId;
        } else {
            return "refuse";
        }
    }


    @GetMapping("/{courseId}/lessons/{lessonId}")
    public String getLesson(@PathVariable int courseId,
                            @PathVariable Integer lessonId,
                            Model model) {
        Lesson lesson = lessonService.getLessonById(lessonId);
        Course course = courseService.getCourseById(courseId);
        lesson.setCourse(course);
        model.addAttribute("lesson", lesson);
        model.addAttribute("course", course);
        return "/lesson/edit-lesson";
    }

    @PostMapping("/{courseId}/lessons/{lessonId}")
    public String updateLesson(@PathVariable Integer lessonId,
                               @PathVariable int courseId,
                               @ModelAttribute("lesson") Lesson lesson,
                               RedirectAttributes redirectAttributes) {

        lesson.setId(lessonId);
        lessonService.updateLesson(lesson);
        redirectAttributes.addFlashAttribute("success", "Lesson update successfully!");
        return "redirect:/courses/" + courseId + "/lessons/" + lessonId;
    }


}
