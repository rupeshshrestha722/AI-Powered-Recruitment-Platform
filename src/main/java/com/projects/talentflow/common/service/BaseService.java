package com.projects.talentflow.common.service;

import com.projects.talentflow.common.entity.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BaseService<T extends BaseEntity> {

    T saveEntity(T entity);

    T getEntityById(UUID id);

    List<T> getAllEntities();

    Page<T> getAllEntities(Pageable pageable);

    boolean existsById(UUID id);

    long countEntities();

    void deleteEntityById(UUID id);
}
