package com.tec02.Service.impl;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tec02.Service.IBaseService;
import com.tec02.model.dto.AbsIdAndCreateTimeDto;
import com.tec02.model.entity.AbsIdAndCreateTime;
import com.tec02.repository.IBaseRepo;
import com.tec02.util.ModelMapperUtil;

import jakarta.transaction.Transactional;

public abstract class BaseService<D extends AbsIdAndCreateTimeDto, 
E extends AbsIdAndCreateTime> implements IBaseService<D, E> {

	private IBaseRepo<E> repository;
	protected Class<D> dtoClass;
	protected Class<E> enityClass;

	protected BaseService(IBaseRepo<E> repository, Class<D> dtoClass, Class<E> enityClass) {
		this.repository = repository;
		this.dtoClass = dtoClass;
		this.enityClass = enityClass;
	}

	@Override
	public D update(Long id, D dto){
		E entity = ModelMapperUtil.map(dto, enityClass);
		return updateDto(id, entity);
	}

	public D updateDto(Long id, E entity){
		return convertToDto(update(id, entity));
	}
	
	@Transactional
	public E update(Long id, E entity){
		Long eID = id == null ? entity.getId() : id;
		entity.setId(eID);
		E entityOld;
		E entitySaved;
		if (eID != null && (entityOld = repository.findById(eID).orElse(null)) != null) {
			ModelMapperUtil.update(entity, entityOld);
			entitySaved = repository.save(entityOld);
		} else {
			entitySaved = repository.save(entity);
		}
		if (entitySaved == null) {
			throw new RuntimeException("Save user failed!");
		}
		return entitySaved;
	}

	@Override
	public void delete(Long id) {
		if (id == null) {
			return;
		}
		this.repository.deleteById(id);
	}

	@Override
	public List<E> findAllByIds(List<Long> ids) {
		if (ids == null) {
			return null;
		}
		List<E> entity = this.repository.findAllById(ids);
		if (entity == null || entity.isEmpty()) {
			return null;
		}
		return entity;
	}

	@Override
	public List<E> findAll() {
		List<E> entity = this.repository.findAll();
		if (entity == null || entity.isEmpty()) {
			return null;
		}
		return entity;
	}

	@Override
	public boolean exists(D dto) {
		if (dto == null) {
			return false;
		}
		ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase();
		return this.repository.exists(Example.of(ModelMapperUtil.map(dto, enityClass), caseInsensitiveExampleMatcher));
	}
	
	@Override
	public boolean exists(E entity) {
		if (entity == null) {
			return false;
		}
		ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase();
		return this.repository.exists(Example.of(entity, caseInsensitiveExampleMatcher));
	}

	@Override
	public List<D> findAllDtoByIds(List<Long> ids) {
		return convertToDtos(this.findAllByIds(ids));
	}

	@Override
	public List<D> findAllDto() {
		return convertToDtos(this.findAll());
	}

	@Override
	public E findOne(Long id){
		if (id == null) {
			throw new RuntimeException(String.format("%s id == null", enityClass.getSimpleName()));
		}
		E entity = this.repository.findById(id).orElse(null);
		if(entity == null) {
			throw new RuntimeException(String.format("Not found %s with id = %s", enityClass.getSimpleName(), id));
		}
		return entity;
	}
	
	@Override
	public D findOneDto(Long id){
		return convertToDto(this.findOne(id));
	}

	public List<D> findAllDto(Pageable pageable) {
		if (pageable == null) {
			return this.findAllDto();
		}
		return convertToDtos(this.findAll(pageable));
	}
	
	public List<E> findAll(Pageable pageable) {
		if (pageable == null) {
			return this.findAll();
		}
		Page<E> pagedResult = this.repository.findAll(pageable);
		if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        }
		return null;
	}

	protected List<E> convertToEntitys(List<D> dtos) {
		if(dtos == null) {
			return null;
		}
		return ModelMapperUtil.mapAll(dtos, enityClass);
	}
	
	protected List<D> convertToDtos(List<E> entitys) {
		if(entitys == null) {
			return null;
		}
		return ModelMapperUtil.mapAll(entitys, dtoClass);
	}
	
	protected D convertToDto(E entity) {
		if(entity == null) {
			return null;
		}
		return ModelMapperUtil.map(entity, dtoClass);
	}
	
	protected E convertToEntity(D dto) {
		if(dto == null) {
			return null;
		}
		return ModelMapperUtil.map(dto, enityClass);
	}
}
