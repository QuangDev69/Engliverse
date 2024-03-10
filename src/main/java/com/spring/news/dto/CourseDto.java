package com.spring.news.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CourseDto {
    private Integer courseId;
    private String courseName;
    private String courseDes;
    private String topicNames;
    private String levelNames;
    private String imagePath;

    public CourseDto(Integer courseId, String courseName, String courseDes, String imagePath,
                     String topicNames, String levelNames) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDes = courseDes;
        this.imagePath = imagePath;
        this.topicNames = topicNames;
        this.levelNames = levelNames;
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

    public String getLevelNames() {
        return levelNames;
    }

    public void setLevelNames(String levelNames) {
        this.levelNames = levelNames;
    }

}
