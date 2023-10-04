package com.tec02.model.dto.updownload;

import java.time.Instant;

import com.tec02.util.Util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRequestDto {
	private String lower_limit;
	private String upper_limit;
	private String error_code;
	private String error_details;
	private String units;
	private String test_value;
	private String test_name;
	private String start_time;
	private String finish_time;
	private String status;

	public Instant getStart_time() {
		return Util.stringCvtInstant(start_time, "yyyy-MM-dd HH:mm:ss.SSS", "yyyy-MM-dd HH:mm:ss");
	}

	public Instant getFinish_time() {
		return Util.stringCvtInstant(finish_time, "yyyy-MM-dd HH:mm:ss.SSS", "yyyy-MM-dd HH:mm:ss");
	}
}
