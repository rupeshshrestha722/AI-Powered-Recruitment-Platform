package com.projects.talentflow.common.service.impl;

import com.projects.talentflow.common.entity.BaseEntity;
import com.projects.talentflow.common.exception.ResourceNotFoundException;
import com.projects.talentflow.common.repository.BaseRepository;
import com.projects.talentflow.common.service.BaseService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

@Transactional
public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

    protected final BaseRepository<T> baseRepository;

    protected BaseServiceImpl(BaseRepository<T> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    public T saveEntity(T entity) {
        return baseRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public T getEntityById(UUID id) {

        return baseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Record not found with ID: " + id
                        )
                );
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getAllEntities() {
        return baseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> getAllEntities(Pageable pageable) {
        return baseRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return baseRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countEntities() {
        return baseRepository.count();
    }

    @Override
    public void deleteEntityById(UUID id) {

        if (!baseRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Record not found with ID: " + id
            );
        }

        baseRepository.deleteById(id);
    }


}
