package com.tec02.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tec02.model.dto.RequestDto;
import com.tec02.model.entity.impl.LogDetail;
import com.tec02.repository.IBaseRepo;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Selection;

public interface LogDetailRepository extends IBaseRepo<LogDetail>, JpaSpecificationExecutor<LogDetail> {

	List<LogDetail> findAllBySnOrMlbsn(String sn, String mlbsn);

	default List<LogDetail> findAllByParameter(RequestDto filter) {
		Specification<LogDetail> spec = (root, query, criteriaBuilder) -> {
			List<Selection<?>> selections = new ArrayList<>();
			if (filter.getSn() != null) {
				selections.add(root.get("sn"));
			}
			if (filter.getMo() != null) {
				selections.add(root.get("mo"));
			}
			if (filter.getEthernetmac() != null) {
				selections.add(root.get("ethernetmac"));
			}
			query.multiselect(selections.toArray(new Selection<?>[0]));

			List<Predicate> predicates = new ArrayList<>();
			if (filter.getSn() != null) {
				predicates.add(criteriaBuilder.equal(root.get("sn"), filter.getSn()));
			}
			if (filter.getMlbsn() != null) {
				predicates.add(criteriaBuilder.equal(root.get("mlbsn"), filter.getMlbsn()));
			}
			if (filter.getMo() != null) {
				predicates.add(criteriaBuilder.equal(root.get("mo"), filter.getMo()));
			}
			if (filter.getEthernetmac() != null) {
				predicates.add(criteriaBuilder.equal(root.get("ethernetmac"), filter.getEthernetmac()));
			}
			if (filter.getMode() != null) {
				predicates.add(criteriaBuilder.equal(root.get("mode"), filter.getMode()));
			}
			if (filter.getPnname() != null) {
				predicates.add(criteriaBuilder.equal(root.get("pnname"), filter.getPnname()));
			}
			if (filter.getTest_software_version() != null) {
				predicates.add(
						criteriaBuilder.equal(root.get("test_software_version"), filter.getTest_software_version()));
			}
			if (filter.getPosition() != null) {
				predicates.add(criteriaBuilder.equal(root.get("position"), filter.getPosition()));
			}
			if (filter.getError_code() != null) {
				predicates.add(criteriaBuilder.equal(root.get("error_code"), filter.getError_code()));
			}
			if (filter.getError_details() != null) {
				predicates.add(criteriaBuilder.equal(root.get("error_details"), filter.getError_details()));
			}
			if (filter.getStatus() != null) {
				predicates.add(criteriaBuilder.equal(root.get("status"), filter.getStatus()));
			}
			if (filter.getOnSfis() != null) {
				predicates.add(criteriaBuilder.equal(root.get("onSfis"), filter.getOnSfis()));
			}
			if (filter.getProduct() != null) {
				predicates.add(criteriaBuilder.equal(root.get("product").get("name"), filter.getProduct()));
			}
			if (filter.getStation() != null) {
				predicates.add(criteriaBuilder.equal(root.get("station").get("name"), filter.getStation()));
			}
			if (filter.getLine() != null) {
				predicates.add(criteriaBuilder.equal(root.get("line").get("name"), filter.getLine()));
			}
			if (filter.getStart_time() != null && filter.getFinish_time() != null) {
				predicates.add(criteriaBuilder.between(root.get("finish_time"), filter.getStart_time(),
						filter.getFinish_time()));
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
		return findAll(spec);
	}
}
