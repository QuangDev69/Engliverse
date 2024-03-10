package com.spring.news.domain;
import jakarta.persistence.*;

@Entity
@Table(name = "course_level")
public class CourseLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "courseId", referencedColumnName = "courseId")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "levelId", referencedColumnName = "levelId")
    private Level level;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
