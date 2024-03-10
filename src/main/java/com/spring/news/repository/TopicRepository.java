package com.spring.news.repository;

import com.spring.news.domain.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Integer> {
    // Custom query methods if needed
}
