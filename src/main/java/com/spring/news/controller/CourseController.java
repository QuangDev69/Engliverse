package com.spring.news.controller;

import com.spring.news.domain.Course;
import com.spring.news.domain.Level;
import com.spring.news.domain.Topic;
import com.spring.news.dto.CourseDto;
import com.spring.news.repository.LevelRepository;
import com.spring.news.repository.TopicRepository;
import com.spring.news.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/courses")
public class CourseController {


    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private LevelRepository levelRepository;

    private String getTopicNames(Set<Topic> topics){
        String topicNames = "";
        int i = 0;
        int length = topics.size();
        for (Topic topic : topics) {

            topicNames += topic.getTopicName();
            if (i < length - 1) {
                topicNames += ",";
            }
            i++;
        }
        return topicNames;
    }

    private String getLevelNames(Set<Level> levels){
        String levelNames = "";
        int i = 0;
        int length = levels.size();
        for (Level level : levels) {
            levelNames += level.getLevelName();
            if (i < length - 1) {
                levelNames += ",";
            }
            i++;
        }
        return levelNames;
    }

    @GetMapping("/all")
    public String listCourses(Model model) {
        List<Course> courses = courseService.findAllCourses();

        List<CourseDto> courseDtos = new ArrayList<>();
        for(Course course: courses){
            String topicNames = getTopicNames(course.getTopics());
            String levelNames = getLevelNames(course.getLevels());
            courseDtos.add(new CourseDto(course.getCourseId(), course.getCourseName(), course.getCourseDes(), topicNames, levelNames));
        }

        model.addAttribute("courses", courseDtos);
        return "courses";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("allTopics", topicRepository.findAll());
        model.addAttribute("allLevels", levelRepository.findAll());
        return "course-create";
    }

    @PostMapping("/save")
    public String saveCourse(@ModelAttribute Course course,
                             @RequestParam List<Integer> topicIds,
                             @RequestParam List<Integer> levelIds) {
        courseService.saveCourseWithTopicsAndLevels(course, topicIds, levelIds);
        return "redirect:/courses/all";
    }

}
