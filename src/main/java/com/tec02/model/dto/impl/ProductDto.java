package com.tec02.model.dto.impl;

import com.tec02.model.entity.AbsIdAndCreateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto extends AbsIdAndCreateTime {
	private String name;
}
