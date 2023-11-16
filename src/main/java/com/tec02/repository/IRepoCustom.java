package com.tec02.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepoCustom<X, R> {
	
	List<X> findAll(R filter, PageRequest pageable, Sort sort);

	<T> List<T> findAllByParameter(R filter, Class<T> clazz, List<String> items, PageRequest pageable, Sort sort);
	
	Long count(R filter);
	
	List<Long> findIds(R rd, PageRequest pageable, Sort sort);
	
}
