package com.tec02.model.dto.impl;

import com.tec02.model.dto.AbsIdAndCreateTimeDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LocationDto extends AbsIdAndCreateTimeDto {
	private String product;
	private String station;
	private String line;

	public String getName() {
		if (product == null || station == null || line == null) {
			return null;
		}
		return String.format("%s_%s_%s", product, station, line);
	}
}
