package com.spring.news.service;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;


public interface PageableService {
    <T> Page<T> findPaginated(int page, int size, Specification<T> spec, Class<T> type);
}
