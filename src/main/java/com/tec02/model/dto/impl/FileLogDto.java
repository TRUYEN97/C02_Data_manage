package com.tec02.model.dto.impl;

import com.tec02.model.dto.AbsIdAndCreateTimeDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileLogDto extends AbsIdAndCreateTimeDto{
	private String path;
	private String name;
}
