package com.spring.news.service.serviceImpl;

import com.spring.news.service.PageableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public class PageableServiceImpl implements PageableService {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public <T> Page<T> findPaginated(int page, int size, Specification<T> spec, Class<T> type) {
        Pageable pageable = PageRequest.of(page, size);
        JpaRepository<T, ?> repository = getRepository(type);
        if (repository instanceof JpaSpecificationExecutor) {
            JpaSpecificationExecutor<T> jpaSpecExecutor = (JpaSpecificationExecutor<T>) repository;
            return jpaSpecExecutor.findAll(spec, pageable);
        } else {
            throw new IllegalArgumentException("The repository does not support JpaSpecificationExecutor");
        }
    }


    private <T> JpaRepository<T, ?> getRepository(Class<T> type) {
        // Logic to retrieve the appropriate repository from the application context
        // based on the entity type
        String[] beanNames = applicationContext.getBeanNamesForType(ResolvableType.forClassWithGenerics(JpaRepository.class, type));
        if (beanNames.length == 0) {
            throw new IllegalStateException("No repository found for type: " + type.getName());
        }
        return (JpaRepository<T, ?>) applicationContext.getBean(beanNames[0]);
    }
}
