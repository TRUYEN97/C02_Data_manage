package com.tec02.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.alibaba.fastjson.JSONObject;
import com.tec02.repository.IRepoCustom;
import com.tec02.util.ModelMapperUtil;
import com.tec02.util.Util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class BaseRopoImpl<X, R> implements IRepoCustom<X, R> {

	@PersistenceContext
	protected EntityManager entityManager;

	protected final Class<X> entityClass;

	protected <T> List<T> findAllCustom(R filter, CreatePredicate<R> predicate, PageRequest pageable, Sort sort,
			Class<T> clazz) {
		if (clazz == null) {
			return null;
		}
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = cb.createQuery(clazz);
		Root<X> root = query.from(entityClass);
		if (!clazz.equals(entityClass)) {
			List<String> fields = Util.getRetainField(clazz, entityClass);
			if (fields == null || fields.isEmpty()) {
				return List.of();
			}
			List<Selection<?>> selections = new ArrayList<>();
			for (String item : fields) {
				try {
					selections.add(root.get(item));
				} catch (Exception e) {
					throw new RuntimeException(String.format("root class not contain key: %s", item));
				}
			}
			query.select(cb.construct(clazz, selections.toArray(new Selection[0])));
		}
		if (predicate != null && !predicate.create(filter, cb, root, query)) {
			return null;
		}
		return getResultList(pageable, sort, query, cb, root);
	}

	protected Long count(R filter, CreatePredicate<R> predicate) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = cb.createQuery(Long.class);
		Root<X> root = query.from(entityClass);
		query.select(cb.count(root));
		if (predicate != null) {
			predicate.create(filter, cb, root, query);
		}
		return entityManager.createQuery(query).getSingleResult();
	}

	protected List<Long> findIds(R filter, CreatePredicate<R> predicate, PageRequest pageable, Sort sort) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = cb.createQuery(Long.class);
		Root<X> root = query.from(entityClass);
		query.select(root.get("id"));
		if (predicate != null) {
			predicate.create(filter, cb, root, query);
		}
		return getResultList(pageable, sort, query, cb, root);
	}

	protected <T> List<T> findAllByItemCustom(R filter, Class<T> clazz, List<String> items,
			CreatePredicate<R> predicate, PageRequest pageable, Sort sort) {
		if (items != null && !items.isEmpty()) {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Tuple> query = cb.createTupleQuery();
			Root<X> root = query.from(entityClass);
			List<Selection<?>> selections = new ArrayList<>();
			for (String item : items) {
				try {
					selections.add(root.get(item));
				} catch (Exception e) {
					throw new RuntimeException(String.format("logDetail not contain key: %s", item));
				}
			}
			query.multiselect(selections);
			predicate.create(filter, cb, root, query);
			List<Tuple> tuples = getResultList(pageable, sort, query, cb, root);
			List<T> tS = new ArrayList<>();
			JSONObject object = new JSONObject();
			for (Tuple tuple : tuples) {
				for (int i = 0; i < items.size(); i++) {
					object.put(items.get(i), tuple.get(i));
				}
				tS.add(ModelMapperUtil.convertValue(object, clazz));
			}
			return tS;
		} else {
			return findAllCustom(filter, predicate, pageable, sort, clazz);
		}
	}

	protected <T> List<T> getResultList(PageRequest pageable, Sort sort, CriteriaQuery<T> query, CriteriaBuilder cb,
			Root<X> root) {
		if (sort != null) {
			for (Order order : sort) {
				String property = order.getProperty();
				if (order.isAscending()) {
					query.orderBy(cb.asc(root.get(property)));
				} else {
					query.orderBy(cb.desc(root.get(property)));
				}
			}
		}
		if (pageable != null) {
			int pageNumber = pageable.getPageNumber();
			int pageSize = pageable.getPageSize();
			return entityManager.createQuery(query).setFirstResult(pageNumber * pageSize).setMaxResults(pageSize)
					.getResultList();
		}
		return entityManager.createQuery(query).getResultList();
	}

	protected CriteriaQuery<Tuple> createTupleQuery(List<String> items) {
		if (items == null || items.isEmpty()) {
			throw new RuntimeException("items = null!");
		}
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> query = cb.createTupleQuery();
		Root<X> root = query.from(entityClass);
		List<Selection<?>> selections = new ArrayList<>();
		for (String item : items) {
			selections.add(root.get(item));
		}
		return query.multiselect(selections);
	}

	protected interface CreatePredicate<R> {
		boolean create(R filter, CriteriaBuilder cb, Root<?> root, CriteriaQuery<?> query);
	}

}
