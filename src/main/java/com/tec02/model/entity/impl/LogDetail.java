package com.tec02.model.entity.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.tec02.model.entity.AbsIdAndCreateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "logdetail")
public class LogDetail extends AbsIdAndCreateTime {
	@Column(updatable = false, length = 16)
	private String sn;
	@Column(updatable = false, length = 16)
	private String mlbsn;
	@Column(updatable = false, length = 20)
	private String mode;
	@Column(updatable = false, length = 20)
	private String pcname;
	@Column(updatable = false, length = 20)
	private String error_code;
	@Column(updatable = false, length = 6)
	private String status;
	@Column(updatable = false, length = 3)
	private String onSfis;
	@OneToMany(mappedBy = "logdetail", cascade = CascadeType.ALL)
	private List<FileLog> fileLogs = new ArrayList<>();
	
	
	@Column(updatable = false, length = 16)
	private String mo;
	@Column(updatable = false, length = 17)
	private String ethernetmac;

	public void addFilelog(FileLog fileLog) {
		if (fileLog == null) {
			return;
		}
		fileLog.setLogdetail(this);
		this.fileLogs.add(fileLog);
	}

	@Column(updatable = false, length = 10)
	private String pnname;
	@Column(updatable = false, length = 23)
	private Instant start_time;
	@Column(updatable = false, length = 23)
	private Instant finish_time;
	@Column(updatable = false, length = 10)
	private String test_software_version;
	@Column(updatable = false, length = 5)
	private String position;
	@Column(updatable = false, length = 100)
	private String error_details;
	
	@ManyToOne
	@JoinColumn(name = "product")
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "station")
	private Station station;
	
	@ManyToOne
	@JoinColumn(name = "line")
	private Line line;

	@OneToMany(mappedBy = "logdetail", cascade = CascadeType.ALL)
	private List<Item> tests = new ArrayList<>();
}
