package com.spring.news.repository;

import com.spring.news.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query("SELECT DISTINCT c FROM Course c " +
            "LEFT JOIN c.level l " +
            "LEFT JOIN c.topics t " +
            "WHERE (:keyword IS NULL OR c.courseName LIKE %:keyword%) " +
            "AND (:levelId IS NULL OR l.levelId = :levelId) " +
            "AND (:topicId IS NULL OR t.topicId = :topicId) " +
            "ORDER BY c.courseId DESC")
    Page<Course> findCoursesByCriteria(@Param("keyword") String keyword,
                                       @Param("levelId") Integer levelId,
                                       @Param("topicId") Integer topicId,
                                       Pageable pageable);

}