package com.tec02.model.dto;

import java.time.Instant;
import java.util.List;

import com.tec02.util.Util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestDto {
	protected List<Long> ids;
	protected List<String> contains;
	protected List<String> items;
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
		if (start_time != null) {
			if (start_time.matches("^[0-9]+")) {
				return Util.longCvtInstant(Long.valueOf(start_time));
			}else{
				return Util.stringCvtInstant(start_time
						, "yyyy-MM-dd HH:mm:ss"
						, "yyyy-MM-dd HH:mm:ss.SSS"
						, "yyyy/MM/dd HH:mm:ss"
						, "yyyy/MM/dd HH:mm:ss.SSS"
						, "yyyy-MM-dd_HH-mm-ss");
			}
		}
		return null;
	}

	public Instant getFinish_time() {
		if (finish_time != null) {
			if (finish_time.matches("^[0-9]+")) {
				return Util.longCvtInstant(Long.valueOf(finish_time));
			}else{
				return Util.stringCvtInstant(finish_time
						, "yyyy-MM-dd HH:mm:ss"
						, "yyyy-MM-dd HH:mm:ss.SSS"
						, "yyyy/MM/dd HH:mm:ss"
						, "yyyy/MM/dd HH:mm:ss.SSS"
						, "yyyy-MM-dd_HH-mm-ss");
			}
		}
		return null;
	}

}
