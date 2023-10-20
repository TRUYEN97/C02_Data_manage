package com.tec02.repository.impl.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.tec02.model.dto.RequestDto;
import com.tec02.model.entity.impl.LogDetail;
import com.tec02.repository.impl.BaseRopoImpl;

import jakarta.persistence.criteria.Predicate;

@Repository
public class LogDetailCustomRepo extends BaseRopoImpl<LogDetail, RequestDto> {

	public LogDetailCustomRepo() {
		super(LogDetail.class);
	}

	private final CreatePredicate<RequestDto> createPredicate = (filter, cb, root, query) -> {
		List<Predicate> predicates = new ArrayList<>();
		if (filter.getSn() != null || filter.getMlbsn() != null) {
			List<Predicate> p = new ArrayList<>();
			if (filter.getSn() != null) {
				p.add(cb.like(root.get("sn"),  String.format("%%%s%%",filter.getSn())));
			}
			if (filter.getMlbsn() != null) {
				p.add(cb.like(root.get("mlbsn"),  String.format("%%%s%%",filter.getMlbsn())));
			}
			predicates.add(cb.or(p.toArray(new Predicate[0])));
		}
		if (filter.getMo() != null) {
			predicates.add(cb.equal(root.get("mo"), filter.getMo()));
		}
		if (filter.getEthernetmac() != null) {
			predicates.add(cb.equal(root.get("ethernetmac"), filter.getEthernetmac()));
		}
		if (filter.getMode() != null) {
			predicates.add(cb.equal(root.get("mode"), filter.getMode()));
		}
		if (filter.getPnname() != null) {
			predicates.add(cb.like(root.get("pnname"),  String.format("%%%s%%",filter.getPnname())));
		}
		if (filter.getPcname() != null) {
			predicates.add(cb.like(root.get("pcname"), String.format("%%%s%%",filter.getPcname())));
		}
		if (filter.getTest_software_version() != null) {
			predicates.add(cb.like(root.get("test_software_version"),  String.format("%s%%",filter.getTest_software_version())));
		}
		if (filter.getPosition() != null) {
			predicates.add(cb.like(root.get("position"),  String.format("%%%s%%",filter.getPosition())));
		}
		if (filter.getError_code() != null) {
			predicates.add(cb.like(root.get("error_code"),  String.format("%%%s%%",filter.getError_code())));
		}
		if (filter.getError_details() != null) {
			predicates.add(cb.like(root.get("error_details"),  String.format("%%%s%%",filter.getError_details())));
		}
		if (filter.getStatus() != null) {
			predicates.add(cb.equal(root.get("status"), filter.getStatus()));
		}
		if (filter.getOnSfis() != null) {
			predicates.add(cb.equal(root.get("onSfis"), filter.getOnSfis()));
		}
		if (filter.getProduct() != null) {
			predicates.add(cb.equal(root.get("product").get("name"), filter.getProduct()));
		}
		if (filter.getStation() != null) {
			predicates.add(cb.equal(root.get("station").get("name"), filter.getStation()));
		}
		if (filter.getLine() != null) {
			predicates.add(cb.equal(root.get("line").get("name"), filter.getLine()));
		}
		if (filter.getStart_time() != null && filter.getFinish_time() != null) {
			predicates.add(cb.between(root.get("finish_time"), filter.getStart_time(), filter.getFinish_time()));
		}
		return query.where(cb.and(predicates.toArray(new Predicate[0])));
	};

	@Override
	public List<LogDetail> findAll(RequestDto filter, PageRequest pageable) {
		return findAllCustom(filter, createPredicate, pageable);
	}

	@Override
	public <T> List<T> findAllByParameter(RequestDto filter, Class<T> clazz, List<String> items,
			PageRequest pageRequest) {
		return findAllByItemCustom(filter, clazz, items, createPredicate, pageRequest);
	}

	@Override
	public Long count(RequestDto filter) {
		return count(filter, createPredicate);
	}

}
