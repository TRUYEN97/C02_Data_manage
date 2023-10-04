package com.tec02.model.entity;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class HaveName extends AbsIdAndCreateTime {
	
	private String name;
	
	@Override
	public String toString() {
		return name;
	}
}
