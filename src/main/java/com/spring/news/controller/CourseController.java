package com.spring.news.controller;

import com.spring.news.domain.Course;
import com.spring.news.domain.Level;
import com.spring.news.domain.Topic;
import com.spring.news.domain.User;
import com.spring.news.dto.CourseDto;
import com.spring.news.repository.LevelRepository;
import com.spring.news.repository.TopicRepository;
import com.spring.news.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
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

    @GetMapping("/all")
    public String listCourses(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "8") int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Course> coursePage = courseService.findAll(pageable); // Sử dụng Pageable
        Page<CourseDto> courseDtoPage = coursePage.map(course -> new CourseDto(
                course.getCourseId(),
                course.getCourseName(),
                course.getCourseDes(),
                course.getImagePath(),
                getTopicNames(course.getTopics()),
                getLevelNames(course.getLevels())
        ));
        model.addAttribute("size", size);
        model.addAttribute("courses", courseDtoPage);
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
                             @RequestParam("image") MultipartFile file,
                             @RequestParam List<Integer> topicIds,
                             @RequestParam List<Integer> levelIds) {
        String imagePath = saveUploadedFile(file);
        course.setImagePath(imagePath);
        courseService.saveCourseWithTopicsAndLevels(course, topicIds, levelIds);
        return "redirect:/courses/all";
    }

    private String saveUploadedFile(MultipartFile file) {
        if (file.isEmpty()) {
            return null; // Trả về null nếu file rỗng
        }

        try {
            // Đường dẫn đến thư mục bạn muốn lưu file, đã tồn tại
            Path uploadDirectory = Paths.get("D:/eng/image");

            // Tạo đường dẫn đầy đủ cho file, bao gồm tên file
            Path filePath = uploadDirectory.resolve(file.getOriginalFilename());

            // Lưu file vào đường dẫn đã chỉ định
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Trả về đường dẫn file dưới dạng String, có thể lưu vào database
            return file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Trả về null nếu có lỗi xảy ra
        }
    }


}
