package com.tec02.model.dto.impl;

import java.time.Instant;
import java.util.Date;

import com.tec02.model.dto.AbsIdAndCreateTimeDto;

import lombok.Setter;

@Setter
public abstract class HaveStartTimeAndFinishTimeDto extends AbsIdAndCreateTimeDto {

	private Instant start_time;
	private Instant finish_time;

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
