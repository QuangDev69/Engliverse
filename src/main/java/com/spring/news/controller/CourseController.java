package com.spring.news.controller;

import com.spring.news.domain.Course;
import com.spring.news.domain.Lesson;
import com.spring.news.domain.Level;
import com.spring.news.domain.Topic;
import com.spring.news.dto.CourseDto;
import com.spring.news.repository.LevelRepository;
import com.spring.news.repository.TopicRepository;
import com.spring.news.security.CustomUserDetails;
import com.spring.news.service.CourseService;
import com.spring.news.service.FileStorageService;
import com.spring.news.service.LessonService;
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
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/courses")
public class CourseController {

    // Inject các service và repository cần thiết cho các thao tác với Course.
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

    @Autowired
    private LessonService lessonService;

    // Hàm trợ giúp để chuyển đổi danh sách các Topic thành một chuỗi tên, cách nhau bởi dấu phẩy.
    private String getTopicNames(Set<Topic> topics) {
        return topics.stream()
                .map(Topic::getTopicName)
                .collect(Collectors.joining(","));
    }

    // Hàm trợ giúp để chuyển đổi đối tượng Course sang CourseDto.
    private CourseDto convertToDto(Course course) {
        String topicNames = getTopicNames(course.getTopics());
        String levelNames =  course.getLevel() != null ? course.getLevel().getLevelName() : null;
        CourseDto courseDto = new CourseDto(course.getCourseId(), course.getCourseName(), course.getCourseDes(),
                course.getImagePath(), topicNames, levelNames);
        courseDto.setLessons(course.getLessons());
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
        List<Course> courses = courseService.findAllCourses();
        Map<Integer, List<Lesson>> courseLessonsMap = new HashMap<>();

        for (Course course : courses) {
            List<Lesson> lessons = lessonService.findLessonsByCourseId(course.getCourseId());
            courseLessonsMap.put(course.getCourseId(), lessons);
        }
        Page<CourseDto> courseDtoPage = coursePage.map(course -> {
            String topicNames = getTopicNames(course.getTopics());
            String levelName = course.getLevel() != null ? course.getLevel().getLevelName() : null;
            return new CourseDto(
                    course.getCourseId(),
                    course.getCourseName(),
                    course.getCourseDes(),
                    course.getImagePath(),
                    topicNames,
                    levelName
            );
        });

        model.addAttribute("courseLessonsMap", courseLessonsMap);
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
                             @RequestParam Integer levelId,
                             Model model) {

        // Kiểm tra các trường bắt buộc và số lượng topic đã chọn
        if (course.getCourseName().isEmpty() || course.getCourseDes().isEmpty() || file.isEmpty() || topicIds.isEmpty() || levelId == null || topicIds.size() > 2) {
            model.addAttribute("course", course);
            model.addAttribute("allTopics", topicRepository.findAll());
            model.addAttribute("allLevels", levelRepository.findAll());
            return "/course/course-create";
        }

        String imageUrl = fileStorageService.storeFile(file);
        course.setImagePath(imageUrl);
        courseService.saveCourseWithTopicsAndLevels(course, topicIds, levelId);
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
                               @RequestParam List<Integer> topicIds,
                               @RequestParam Integer levelId,
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
        courseService.updateCourse(course,topicIds, levelId);
        return "redirect:/courses/" + courseId;
    }

    @PostMapping("/{courseId}/delete")
    public String deleteCourse(@PathVariable("courseId") int courseId) {
        courseService.deleteCourseById(courseId);
        return "redirect:/courses/all";
    }

    @GetMapping("/course-lesson")
    public String showCourses(Model model) {
        List<Course> courses = courseService.findAllCourses();
        model.addAttribute("courses", courses);
        return "/course/overview";
    }
//    @GetMapping("/list-lesson")
//    public String listCourses(Model model) {
//        List<Course> courses = courseService.findAllCourses();
//        Map<Integer, List<Lesson>> courseLessonsMap = new HashMap<>();
//
//        for (Course course : courses) {
//            List<Lesson> lessons = lessonService.findLessonsByCourseId(course.getCourseId());
//            courseLessonsMap.put(course.getCourseId(), lessons);
//        }
//
//        model.addAttribute("courses", courses);
//        model.addAttribute("courseLessonsMap", courseLessonsMap);
//        return "lesson/all-lesson";
//    }
}
