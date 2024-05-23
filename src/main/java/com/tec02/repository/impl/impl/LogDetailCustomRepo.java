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
		if (ids != null && !ids.isEmpty()) {
			Predicate[] predicates = ids.stream().map(id -> cb.equal(root.get("id"), id)).toArray(Predicate[]::new);
			query.where(cb.or(predicates));
			return true;
		}
		return false;
	};

	private final CreatePredicate<RequestDto> createPredicate = (filter, cb, root, query) -> {
		List<Predicate> predicates = new ArrayList<>();
		Set<String> sns = filter.getSns();
		if (sns != null && !sns.isEmpty()) {
			List<Predicate> p = new ArrayList<>();
			Predicate[] predicatesSn = sns.stream().map(sn -> cb.equal(root.get("sn"), sn)).toArray(Predicate[]::new);
			Predicate[] predicatesMlbsn = sns.stream().map(sn -> cb.equal(root.get("mlbsn"), sn))
					.toArray(Predicate[]::new);
			p.add(cb.or(predicatesSn));
			p.add(cb.or(predicatesMlbsn));
			predicates.add(cb.or(p.toArray(new Predicate[0])));
		} else {
			String sn = filter.getSn();
			String mlbsn = filter.getMlbsn();
			List<Predicate> p = new ArrayList<>();
			if (sn != null && !sn.isBlank()) {
				p.add(cb.equal(root.get("sn"), sn));
			}
			if (mlbsn != null && !mlbsn.isBlank()) {
				p.add(cb.equal(root.get("mlbsn"), mlbsn));
			}
			if (!p.isEmpty()) {
				predicates.add(cb.or(p.toArray(new Predicate[0])));
			}
		}
		String mo = filter.getMo();
		if (mo != null && !mo.isBlank()) {
			predicates.add(cb.equal(root.get("mo"), mo));
		}
		String mac = filter.getEthernetmac();
		if (mac != null && !mac.isBlank()) {
			predicates.add(cb.equal(root.get("ethernetmac"), mac));
		}
		String mode = filter.getMode();
		if (mode != null && !mode.isBlank()) {
			predicates.add(cb.like(root.get("mode"), String.format("%%%s%%", mode)));
		}
		String pnname = filter.getPnname();
		if (pnname != null && !pnname.isBlank()) {
			predicates.add(cb.equal(root.get("pnname"), pnname));
		}
		String pcname = filter.getPcname();
		if (pcname != null && !pcname.isBlank()) {
			predicates.add(cb.like(root.get("pcname"), String.format("%%%s%%", pcname)));
		}
		String version = filter.getTest_software_version();
		if (version != null && !version.isBlank()) {
			predicates.add(cb.like(root.get("test_software_version"), String.format("%s%%", version)));
		}
		String position = filter.getPosition();
		if (position != null && !position.isBlank()) {
			predicates.add(cb.like(root.get("position"), String.format("%%%s%%", position)));
		}
		String errorcode = filter.getError_code();
		if (errorcode != null && !errorcode.isBlank()) {
			predicates.add(cb.like(root.get("error_code"), String.format("%%%s%%", errorcode)));
		}
		String errorDes = filter.getError_details();
		if (errorDes != null && !errorDes.isBlank()) {
			predicates.add(cb.like(root.get("error_details"), String.format("%%%s%%", errorDes)));
		}
		String status = filter.getStatus();
		if (status != null && !status.isBlank()) {
			predicates.add(cb.equal(root.get("status"), status));
		}
		String sfis = filter.getOnSfis();
		if (sfis != null && !sfis.isBlank()) {
			predicates.add(cb.equal(root.get("onSfis"), sfis));
		}
		String product = filter.getProduct();
		if (product != null && !product.isBlank()) {
			predicates.add(cb.equal(root.get("product").get("name"), product));
		}
		String station = filter.getStation();
		if (station != null && !station.isBlank()) {
			predicates.add(cb.equal(root.get("station").get("name"), station));
		}
		String line = filter.getLine();
		if (line != null && !line.isBlank()) {
			predicates.add(cb.equal(root.get("line").get("name"), line));
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
