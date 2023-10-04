package com.tec02.model.dto;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
@NoArgsConstructor
public abstract class AbsIdAndCreateTimeDto {
	protected Long id;
	
	protected Instant createTime;
	
	public String getCreateTime(){
		return getTimeString(createTime);
	}
	
	protected String getTimeString(Instant instant) {
		if(instant == null) {
			return null;
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(Date.from(instant));
	}
}
