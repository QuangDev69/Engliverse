package com.spring.news.controller;

import com.spring.news.domain.Course;
import com.spring.news.domain.Level;
import com.spring.news.domain.Topic;
import com.spring.news.dto.CourseDto;
import com.spring.news.repository.LevelRepository;
import com.spring.news.repository.TopicRepository;
import com.spring.news.security.CustomUserDetails;
import com.spring.news.service.CourseService;
import com.spring.news.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;
    private final FileStorageService fileStorageService;

    @Autowired
    public CourseController(CourseService courseService,FileStorageService fileStorageService) {

        this.courseService = courseService;
        this.fileStorageService = fileStorageService;

    }

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private LevelRepository levelRepository;

    private String getTopicNames(Set<Topic> topics) {
        return topics.stream()
                .map(Topic::getTopicName)
                .collect(Collectors.joining(","));
    }

    private String getLevelNames(Set<Level> levels) {
        return levels.stream()
                .map(Level::getLevelName)
                .collect(Collectors.joining(","));
    }

    private CourseDto convertToDto(Course course) {
        String topicNames = getTopicNames(course.getTopics());
        String levelNames = getLevelNames(course.getLevels());
        CourseDto courseDto = new CourseDto(course.getCourseId(), course.getCourseName(), course.getCourseDes(),
                course.getImagePath(), topicNames, levelNames);

        return courseDto;
    }


    @GetMapping("/all")
    public String listCourses(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "8") int size,
                              @RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(value = "levelId", required = false) Integer levelId,
                              @RequestParam(value = "topicId", required = false) Integer topicId
                              ) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
        Pageable pageable = PageRequest.of(page, size);
        Page<Course> coursePage = courseService.findCourses(keyword, levelId, topicId, pageable);
/*

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            model.addAttribute("userId", userDetails.getUserId());
            model.addAttribute("isAdmin", isAdmin);
        }
*/


        Page<CourseDto> courseDtoPage = coursePage.map(course -> new CourseDto(
                course.getCourseId(),
                course.getCourseName(),
                course.getCourseDes(),
                course.getImagePath(),
                getTopicNames(course.getTopics()),
                getLevelNames(course.getLevels())
        ));
        model.addAttribute("levelId", levelId);
        model.addAttribute("topicId", topicId);
        model.addAttribute("allTopics", topicRepository.findAll());
        model.addAttribute("allLevels", levelRepository.findAll());
        model.addAttribute("size", size);
        model.addAttribute("courses", courseDtoPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalPages", coursePage.getTotalPages());
        model.addAttribute("currentPage", page);
        return  "/course/courses";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("allTopics", topicRepository.findAll());
        model.addAttribute("allLevels", levelRepository.findAll());
        return "/course/course-create";
    }

    @PostMapping("/save")
    public String saveCourse(@ModelAttribute Course course,
                             @RequestParam("image") MultipartFile file,
                             @RequestParam List<Integer> topicIds,
                             @RequestParam List<Integer> levelIds) {
        String imageUrl = fileStorageService.storeFile(file);
        course.setImagePath(imageUrl);
        courseService.saveCourseWithTopicsAndLevels(course, topicIds, levelIds);
        return "redirect:/courses/all";
    }


    @GetMapping("/{courseId}")
    public String getCourseById(Model model, @PathVariable("courseId") int courseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
        Course course = courseService.getCourseById(courseId);
        CourseDto courseDto = convertToDto(course);
        if (isAdmin) {
            model.addAttribute("allTopics", topicRepository.findAll());
            model.addAttribute("allLevels", levelRepository.findAll());
        }
        model.addAttribute("course", courseDto);
        return isAdmin ? "/course/course-detail-admin" : "/course/course-detail";
    }

    @PostMapping("/update/{courseId}")
    public String updateCourse(@PathVariable("courseId") int courseId,
                               @RequestParam("image") MultipartFile file,
                               @RequestParam List<Integer> topicId,
                               @RequestParam List<Integer> levelId,
                               @ModelAttribute Course course) {
        course.setCourseId(courseId);
        if (!file.isEmpty()) {
            String imagePath = fileStorageService.storeFile(file);
            course.setImagePath(imagePath);
        } else {
            if (course.getCourseId() != null) {
                Course existingCourse = courseService.getCourseById(course.getCourseId());
                if (existingCourse != null) {
                    course.setImagePath(existingCourse.getImagePath());
                }
            }
        }
        courseService.updateCourse(course,topicId, levelId);
        return "redirect:/courses/" + courseId;
    }

}
