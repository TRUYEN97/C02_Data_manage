package com.tec02.model.dto.impl;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class LogDetailDtoShort {
	private String sn;
	private String mlbsn;
	private String mode;
	private String pcname;
	private String error_code;
	private String status;
	private String onSfis;
	private Instant start_time;
	private Instant finish_time;

	private Long id;

	private String getTimeString(Instant instant) {
		if (instant == null) {
			return null;
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date.from(instant));
	}

	public String getStart_time() {
		return getTimeString(start_time);
	}

	public String getFinish_time() {
		return getTimeString(finish_time);
	}

	public String getCycletime() {
		if (start_time == null || finish_time == null) {
			return null;
		}
		double second = (Date.from(finish_time).getTime() - Date.from(start_time).getTime()) / 1000.0;
		return String.format("%.3f", second);
	}
}
