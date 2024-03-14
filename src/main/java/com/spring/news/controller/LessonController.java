package com.spring.news.controller;


import com.spring.news.domain.Course;
import com.spring.news.domain.Lesson;
import com.spring.news.service.CourseService;
import com.spring.news.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{courseId}/lessons/add")
    public String showLessonAdd(@PathVariable int courseId, Model model) {
        Course course = courseService.getCourseById(courseId);
        model.addAttribute("course", course);
        model.addAttribute("lesson", new Lesson());
        return "/lesson/create-lesson";
    }

    @PostMapping("/{coursesId}/lessons/add")
    public String createLesson(@PathVariable int coursesId, @ModelAttribute("lesson") Lesson lesson) {
        Course course = courseService.getCourseById(coursesId);
        lesson.setCourse(course);
        lessonService.save(lesson);
        return "redirect: /courses/" + coursesId;
    }

    @GetMapping("/{courseId}/lessons/{lessonId}")
    public String getLesson(@PathVariable int courseId,
                                @PathVariable Integer lessonId,
                                Model model) {
        Lesson lesson= lessonService.getLessonById(lessonId);
        Course course = courseService.getCourseById(courseId);
        lesson.setCourse(course);
        model.addAttribute("lesson",lesson);
        model.addAttribute("course",course);
        return "/lesson/edit-lesson";
    }

    @PostMapping("/{courseId}/lessons/{lessonId}")
    public String updateLesson(@PathVariable Integer lessonId,
                                   @PathVariable int courseId,
                                   @ModelAttribute("lesson") Lesson lesson) {
        lesson.setId(lessonId);
        lessonService.updateLesson(lesson);
        return "redirect:/courses/"+courseId +"/lessons/" +lessonId;
    }


}
