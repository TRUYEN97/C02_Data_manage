package com.tec02.model.dto.impl;

import com.tec02.model.entity.AbsIdAndCreateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FileLogDto extends AbsIdAndCreateTime{
	private String path;
	private String name;
}
