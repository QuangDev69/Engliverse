package com.spring.news.domain;

import jakarta.persistence.*;

import java.util.Set;

@Entity

public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer levelId;
    private String levelName;

    @OneToMany(mappedBy = "level")
    private Set<Course> courses;

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }


}
