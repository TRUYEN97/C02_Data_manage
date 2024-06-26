package com.tec02.controller;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
import com.tec02.model.dto.impl.LogDetailDtoShort;

@RestController
public class LogDetailAPI {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private LogDetailService logDetailService;

	@PostMapping("api/v1/upload")
	public ResponseEntity<ResponseDto> create(@RequestPart("file") List<MultipartFile> files) {
		try {
			logDetailService.upload(files);
			return ResponseDto.toResponse(true, null, "Save succeed!");
		} catch (Exception e) {
			logger.error(String.format("upload logs: %s - %s",e.getClass().getSimpleName(),
					e.getLocalizedMessage()));
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@GetMapping("/{date}/{product}/{station}/{line}/{mode}/{type}/{filepath:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable("date") String date,
			@PathVariable("product") String product, @PathVariable("station") String station,
			@PathVariable("line") String line, @PathVariable("mode") String mode, @PathVariable("type") String type,
			@PathVariable("filepath") String filepath) {
		Path path = Path.of(date, product, station, line, mode, type, filepath);
		try {
			Resource resource = this.logDetailService.downloadFile(path.toString());
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_DISPOSITION,
					String.format("attachment; filename=\"%s\"", resource.getFilename()));
			headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
			logger.info(String.format("Download file: %s", path));
			return ResponseDto.toDownloadResponse(headers, resource);
		} catch (Exception e) {
			logger.error(String.format("Download file:%s - %s failed! %s",e.getClass().getSimpleName(),
					path, e.getLocalizedMessage()));
			return ResponseEntity.ofNullable(null);
		}
	}

	@GetMapping("api/v1/log/count")
	public ResponseEntity<ResponseDto> count(@ModelAttribute RequestDto requestDto) {
		try {
			return ResponseDto.toResponse(true, this.logDetailService.count(requestDto), "ok");
		} catch (Exception e) {
			logger.error(String.format("Get count:%s - %s",e.getClass().getSimpleName(), e.getLocalizedMessage()));
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@GetMapping("api/v1/log")
	public ResponseEntity<ResponseDto> findAll(@ModelAttribute RequestDto requestDto,
			@RequestParam(name = "item", required = false) List<String> items,
			@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size,
			@RequestParam(name = "sortBy", defaultValue = "id") String softBy,
			@RequestParam(name = "sortType", defaultValue = "desc") String softType) {
		try {
			Sort sort = Sort.by(softType.equalsIgnoreCase("asc") ? Direction.ASC : Direction.DESC, softBy);
			List<LogDetailDto> logs = null;
			if(page == null || size == null) {
				logs = this.logDetailService.findAllLogDetailDto(requestDto, items, null, sort);
			}else {
				logs = this.logDetailService.findAllLogDetailDto(requestDto, items, PageRequest.of(page, size), sort);
			}
			if (logs == null || logs.isEmpty()) {
				return ResponseDto.toResponse(false, List.of(), "Not found!");
			}
			logger.info(String.format("Get log: %s logs", logs.size()));
			return ResponseDto.toResponse(true, logs, "ok");
		} catch (Exception e) {
			logger.error(String.format("Get log: %s - %s", e.getClass().getSimpleName(), e.getLocalizedMessage()));
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}
	
	@GetMapping("api/v1/log/short")
	public ResponseEntity<ResponseDto> findShortAll(@RequestParam(name = "ids") Set<Long> ids,
			@RequestParam(name = "sortBy", defaultValue = "id") String softBy,
			@RequestParam(name = "sortType", defaultValue = "desc") String softType) {
		try {
			Sort sort = Sort.by(softType.equalsIgnoreCase("asc") ? Direction.ASC : Direction.DESC, softBy);
			RequestDto requestDto = new RequestDto();
			requestDto.setIds(ids);
			List<LogDetailDtoShort> logs = this.logDetailService.findAllLogDetailShortDtoByIds(requestDto , sort);
			if (logs == null || logs.isEmpty()) {
				return ResponseDto.toResponse(false, List.of(), "Not found!");
			}
			logger.info(String.format("Get short log: %s logs", logs.size()));
			return ResponseDto.toResponse(true, logs, "ok");
		} catch (Exception e) {
			logger.error(String.format("Get short log: %s", e.getLocalizedMessage()));
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}
	
	@GetMapping("api/v1/log/full")
	public ResponseEntity<ResponseDto> findAll(@ModelAttribute RequestDto requestDto,
			@RequestParam(name = "sortBy", defaultValue = "id") String softBy,
			@RequestParam(name = "sortType", defaultValue = "desc") String softType) {
		try {
			Sort sort = Sort.by(softType.equalsIgnoreCase("asc") ? Direction.ASC : Direction.DESC, softBy);
			List<LogDetailDto> logs = this.logDetailService.findAllLogDetailDtoByIds(requestDto, sort);
			if (logs == null || logs.isEmpty()) {
				return ResponseDto.toResponse(false, List.of(), "Not found!");
			}
			logger.info(String.format("Get full log: %s logs", logs.size()));
			return ResponseDto.toResponse(true, logs, "ok");
		} catch (Exception e) {
			logger.error(String.format("Get full log: %s", e.getLocalizedMessage()));
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}
	
	@GetMapping("api/v1/log/id")
	public ResponseEntity<ResponseDto> findIdAll(@ModelAttribute RequestDto requestDto,
			@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size,
			@RequestParam(name = "sortBy", defaultValue = "id") String softBy,
			@RequestParam(name = "sortType", defaultValue = "desc") String softType) {
		try {
			Sort sort = Sort.by(softType.equalsIgnoreCase("asc") ? Direction.ASC : Direction.DESC, softBy);
			List<Long> logs = null;
			if(page == null || size == null) {
				logs = this.logDetailService.findIds(requestDto,  null, sort);
			}else {
				logs = this.logDetailService.findIds(requestDto,  PageRequest.of(page, size), sort);
			}
			if (logs == null || logs.isEmpty()) {
				return ResponseDto.toResponse(false, List.of(), "Not found!");
			}
			logger.info(String.format("Get id: %s", logs.size()));
			return ResponseDto.toResponse(true, logs, "ok");
		} catch (Exception e) {
			logger.error(String.format("Get id: %s", e.getLocalizedMessage()));
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("api/v1/log")
	public ResponseEntity<ResponseDto> delete(@RequestParam("ids") Long... ids) {
		try {
			this.logDetailService.delete(ids);
			logger.info(String.format("delete logID: %s", List.of(ids)));
			return ResponseDto.toResponse(true, ids, "ok");
		} catch (Exception e) {
			logger.error(String.format("delete logID failed: %s", e.getLocalizedMessage()));
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

}
