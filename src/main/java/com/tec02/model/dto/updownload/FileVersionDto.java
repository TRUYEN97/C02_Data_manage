package com.tec02.model.dto.updownload;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Component
@NoArgsConstructor
@ToString
public class FileVersionDto {
	private String filename;
	private String filepath;
	private String md5;
}
