package com.tec02.model.dto.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tec02.model.entity.impl.FileLog;

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
	private List<FileLog> fileLogs = new ArrayList<>();
}
