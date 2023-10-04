package com.tec02.model.dto.impl;

import com.tec02.model.dto.AbsIdAndCreateTimeDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LineDto extends AbsIdAndCreateTimeDto {
	private String name;
}
