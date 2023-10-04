package com.tec02.controller;

import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tec02.Service.impl.impl.LogDetailService;
import com.tec02.model.dto.RequestDto;
import com.tec02.model.dto.ResponseDto;
import com.tec02.model.dto.impl.LogDetailDto;

@RestController
public class LogDetailAPI {
	@Autowired
	private LogDetailService logDetailService;

	@PostMapping("api/v1/upload")
	public ResponseEntity<ResponseDto> create(@RequestPart("file") List<MultipartFile> files) {
		try {
			logDetailService.upload(files);
			return ResponseDto.toResponse(true, null, "Save succeed!");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@GetMapping("/{date}/{product}/{station}/{line}/{mode}/{type}/{filepath:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable("date") String date,
			@PathVariable("product") String product, @PathVariable("station") String station,
			@PathVariable("line") String line, @PathVariable("mode") String mode, @PathVariable("type") String type,
			@PathVariable("filepath") String filepath) {
		try {
			Path path = Path.of(date, product, station, line, mode, type, filepath);
			Resource resource = this.logDetailService.downloadFile(path.toString());
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_DISPOSITION,
					String.format("attachment; filename=\"%s\"", resource.getFilename()));
			headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
			return ResponseDto.toDownloadResponse(headers, resource);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ofNullable(null);
		}
	}
	
	@GetMapping("api/v1/log/count")
	public ResponseEntity<ResponseDto> count(@ModelAttribute RequestDto requestDto) {
		try {
			return ResponseDto.toResponse(true, this.logDetailService.count(requestDto), "ok");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@GetMapping("api/v1/log")
	public ResponseEntity<ResponseDto> findAll(@ModelAttribute RequestDto requestDto,
			@RequestParam(name = "item", required = false) List<String> items,
			@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
		try {
			List<LogDetailDto> logs = page == null || size == null
					? this.logDetailService.findAllLogDetailDto(requestDto, items, null)
					: this.logDetailService.findAllLogDetailDto(requestDto, items, PageRequest.of(page, size));
			if (logs == null || logs.isEmpty()) {
				return ResponseDto.toResponse(false, List.of(), "Not found!");
			}
			return ResponseDto.toResponse(true, logs, "ok");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("api/v1/log/{id}")
	public ResponseEntity<ResponseDto> delete(@PathVariable("id") Long id) {
		try {
			this.logDetailService.delete(id);
			return ResponseDto.toResponse(true, id, "ok");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

}
