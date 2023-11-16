package com.tec02.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tec02.Service.impl.impl.FilelogService;
import com.tec02.model.dto.ResponseDto;

@RestController
@RequestMapping("api/v1/filelog")
public class FileLogAPI {

	private final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private FilelogService filelogService;
	
	@GetMapping()
	public ResponseEntity<ResponseDto> getAllByLogdetailId(@RequestParam("ids") List<Long> ids) {
		try {
			return ResponseDto.toResponse(true, this.filelogService.findAllDtoByLogdetailId(ids), "ok");
		} catch (Exception e) {
			logger.error(String.format("Get count: %s", e.getLocalizedMessage()));
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}
	
}
