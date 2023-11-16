package com.tec02.model.dto.impl;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class HaveLocationDto extends HaveStartTimeAndFinishTimeDto {
	private String product;
	private String station;
	private String line;

}
