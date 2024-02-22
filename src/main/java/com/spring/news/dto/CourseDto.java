package com.spring.news.dto;

import java.util.Set;

public class CourseDto {
    private int courseId;
    private String courseName;
    private String courseDes;
//    private String creationDate;
    private String topicNames;
    private String levelNames;

    // Constructors, Getters và Setters
    public CourseDto(int courseId, String courseName, String courseDes,
                     String topicNames, String levelNames) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDes = courseDes;
//        this.creationDate = creationDate;
        this.topicNames = topicNames;
        this.levelNames = levelNames;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
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

//    public String getCreationDate() {
//        return creationDate;
//    }
//
//    public void setCreationDate(String creationDate) {
//        this.creationDate = creationDate;
//    }

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
