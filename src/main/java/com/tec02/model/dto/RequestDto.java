package com.tec02.model.dto;

import java.time.Instant;

import com.tec02.util.Util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestDto {
	protected String sn;
	protected String mlbsn;
	protected String mo;
	protected String ethernetmac;
	protected String mode;
	protected String pnname;
	protected String pcname;
	protected String test_software_version;
	protected String position;
	protected String error_code;
	protected String error_details;
	protected String status;
	protected String onSfis;
	private String product;
	private String station;
	private String line;
	protected String start_time;
	protected String finish_time;
	
	public Instant getStart_time() {
		return Util.stringCvtInstant(start_time, "yyyy-MM-dd_HH-mm-ss");
	}

	public Instant getFinish_time() {
		return Util.stringCvtInstant(finish_time, "yyyy-MM-dd_HH-mm-ss");
	}
	
}
