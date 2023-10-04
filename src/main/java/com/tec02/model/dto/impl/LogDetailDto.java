package com.tec02.model.dto.impl;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LogDetailDto extends HaveLocationDtio {
	private String sn;
	private String mlbsn;
	private String mo;
	private String ethernetmac;
	private String mode;
	private String pnname;
	private String pcname;
	private String test_software_version;
	private String position;
	private String error_code;
	private String error_details;
	private String status;
	private String onSfis;
	private List<FileLogDto> fileLogs = new ArrayList<>();
	private List<ItemDto> tests = new ArrayList<>();
}
