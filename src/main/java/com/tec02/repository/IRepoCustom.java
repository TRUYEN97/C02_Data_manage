package com.tec02.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepoCustom<X, R> {
	
	List<X> findAll(R filter, PageRequest pageable);

	<T> List<T> findAllByParameter(R filter, Class<T> clazz, List<String> items, PageRequest pageRequest);
	
	Long count(R filter);
	
}
