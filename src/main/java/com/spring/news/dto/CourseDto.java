package com.spring.news.dto;

import com.spring.news.domain.Lesson;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * CourseDto được sử dụng để chuyển đổi dữ liệu từ Course entity sang một định dạng
 * phù hợp hơn cho việc truyền dữ liệu qua các lớp và cuối cùng đến client.
 * Điều này giúp đảm bảo rằng dữ liệu được truyền đi một cách hiệu quả và bảo mật,
 * đồng thời giảm thiểu rủi ro lộ thông tin nhạy cảm.
 */

public class CourseDto {
    private Integer courseId;
    private String courseName;
    private String courseDes;
    private String topicNames;
    private String levelName;
    private String imagePath;

    // Tập hợp các Lesson liên quan đến khóa học
    private Set<Lesson> lessons;

    /**
     * Constructor cho CourseDto.
     *
     * @param courseId ID của khóa học
     * @param courseName Tên của khóa học
     * @param courseDes Mô tả chi tiết về khóa học
     * @param imagePath Đường dẫn tới hình ảnh của khóa học
     * @param topicNames Tên của các chủ đề liên quan đến khóa học
     * @param levelName Tên của cấp độ đối với khóa học
     */
    public CourseDto(Integer courseId, String courseName, String courseDes, String imagePath,
                     String topicNames, String levelName) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDes = courseDes;
        this.imagePath = imagePath;
        this.topicNames = topicNames;
        this.levelName = levelName;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDes() {
        return courseDes;
    }

    public void setCourseDes(String courseDes) {
        this.courseDes = courseDes;
    }

    public String getTopicNames() {
        return topicNames;
    }

    public void setTopicNames(String topicNames) {
        this.topicNames = topicNames;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

}
