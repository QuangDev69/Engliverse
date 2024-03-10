package com.spring.news.repository;

import com.spring.news.domain.Level;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelRepository extends JpaRepository<Level, Integer> {
    // Custom query methods if needed
}
