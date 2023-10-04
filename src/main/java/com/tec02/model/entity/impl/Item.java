package com.tec02.model.entity.impl;

import java.time.Instant;

import com.tec02.model.entity.AbsIdAndCreateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item")
public class Item extends AbsIdAndCreateTime{
	@ManyToOne
	@JoinColumn(name = "logdetail", nullable = false)
	private LogDetail logdetail;
	@Column(updatable = false, length = 23)
	private Instant start_time;
	@Column(updatable = false, length = 23)
	private Instant finish_time;
	@Column(updatable = false, length = 100)
	private String lower_limit;
	@Column(updatable = false, length = 100)
	private String upper_limit;
	@Column(updatable = false, length = 20)
	private String error_code;
	@Column(updatable = false, length = 100)
	private String error_details;
	@Column(updatable = false, length = 10)
	private String units;
	@Column(updatable = false, length = 255)
	private String test_value;
	@Column(updatable = false)
	private String test_name;
	@Column(updatable = false, length = 6)
	private String status;
}
