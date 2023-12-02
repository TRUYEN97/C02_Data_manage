package com.tec02.repository.impl.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

	private final CreatePredicate<RequestDto> crPreFindByIds = (filter, cb, root, query) -> {
		Set<Long> ids = filter.getIds();
		if (ids != null) {
			Predicate[] predicates = ids.stream().map(id -> cb.equal(root.get("id"), id)).toArray(Predicate[]::new);
			query.where(cb.or(predicates));
			return true;
		}
		return false;
	};

	private final CreatePredicate<RequestDto> createPredicate = (filter, cb, root, query) -> {
		List<Predicate> predicates = new ArrayList<>();
		Set<String> sns = filter.getSns();
		if (sns != null) {
			List<Predicate> p = new ArrayList<>();
			Predicate[] predicatesSn = sns.stream().map(sn -> cb.equal(root.get("sn"), sn)).toArray(Predicate[]::new);
			Predicate[] predicatesMlbsn = sns.stream().map(sn -> cb.equal(root.get("mlbsn"), sn)).toArray(Predicate[]::new);
			p.add(cb.or(predicatesSn));
			p.add(cb.or(predicatesMlbsn));
			predicates.add(cb.or(p.toArray(new Predicate[0])));
		} else if (filter.getSn() != null || filter.getMlbsn() != null) {
			List<Predicate> p = new ArrayList<>();
			if (filter.getSn() != null) {
				p.add(cb.equal(root.get("sn"), filter.getSn()));
			}
			if (filter.getMlbsn() != null) {
				p.add(cb.equal(root.get("mlbsn"), filter.getMlbsn()));
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
			predicates.add(cb.like(root.get("mode"), String.format("%%%s%%", filter.getMode())));
		}
		if (filter.getPnname() != null) {
			predicates.add(cb.equal(root.get("pnname"), filter.getPnname()));
		}
		if (filter.getPcname() != null) {
			predicates.add(cb.like(root.get("pcname"), String.format("%%%s%%", filter.getPcname())));
		}
		if (filter.getTest_software_version() != null) {
			predicates.add(cb.like(root.get("test_software_version"),
					String.format("%s%%", filter.getTest_software_version())));
		}
		if (filter.getPosition() != null) {
			predicates.add(cb.like(root.get("position"), String.format("%%%s%%", filter.getPosition())));
		}
		if (filter.getError_code() != null) {
			predicates.add(cb.like(root.get("error_code"), String.format("%%%s%%", filter.getError_code())));
		}
		if (filter.getError_details() != null) {
			predicates.add(cb.like(root.get("error_details"), String.format("%%%s%%", filter.getError_details())));
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
		if (!predicates.isEmpty()) {
			query.where(cb.and(predicates.toArray(new Predicate[0])));
		}
		return true;
	};

	@Override
	public List<LogDetail> findAll(RequestDto rd, PageRequest pageable, Sort sort) {
		return findAllCustom(rd, createPredicate, pageable, sort, LogDetail.class);
	}

	@Override
	public <T> List<T> findAllByParameter(RequestDto filter, Class<T> clazz, List<String> items, PageRequest pageable,
			Sort sort) {
		return findAllByItemCustom(filter, clazz, items, createPredicate, pageable, sort);
	}

	public <T> List<T> findAllCustom(RequestDto filter, Class<T> clazz, PageRequest pageRequest, Sort sort) {
		return findAllCustom(filter, createPredicate, pageRequest, sort, clazz);
	}

	@Override
	public Long count(RequestDto filter) {
		return count(filter, createPredicate);
	}

	@Override
	public List<Long> findIds(RequestDto rd, PageRequest pageable, Sort sort) {
		return findIds(rd, createPredicate, pageable, sort);
	}

	public <T> List<T> findAllCustomByids(RequestDto rd, Sort sort, Class<T> clazz) {
		return findAllCustom(rd, crPreFindByIds, null, sort, clazz);
	}

}
