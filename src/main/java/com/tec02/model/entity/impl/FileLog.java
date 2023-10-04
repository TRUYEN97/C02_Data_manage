package com.tec02.model.entity.impl;

import com.tec02.model.entity.AbsIdAndCreateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "filelog")
public class FileLog extends AbsIdAndCreateTime {
	private String path;
	private String name;
	@ManyToOne
	@JoinColumn(name = "logdetail")
	private LogDetail logdetail;
}
