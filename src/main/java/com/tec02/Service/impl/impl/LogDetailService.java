package com.tec02.Service.impl.impl;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.tec02.Service.FileSystemStorageService;
import com.tec02.Service.impl.BaseService;
import com.tec02.model.dto.RequestDto;
import com.tec02.model.dto.impl.LogDetailDto;
import com.tec02.model.dto.impl.LogDetailDtoShort;
import com.tec02.model.dto.updownload.UploadLogFileRequest;
import com.tec02.model.entity.impl.FileLog;
import com.tec02.model.entity.impl.Item;
import com.tec02.model.entity.impl.Line;
import com.tec02.model.entity.impl.LogDetail;
import com.tec02.model.entity.impl.Product;
import com.tec02.model.entity.impl.Station;
import com.tec02.repository.impl.FilelogRepository;
import com.tec02.repository.impl.ItemRepository;
import com.tec02.repository.impl.LogDetailRepository;
import com.tec02.repository.impl.impl.LogDetailCustomRepo;
import com.tec02.util.ModelMapperUtil;
import com.tec02.util.Util;

import jakarta.transaction.Transactional;

@Service
public class LogDetailService extends BaseService<LogDetailDto, LogDetail> {

	@Autowired
	private FilelogRepository filelogRepository;

	@Autowired
	private FileSystemStorageService storageService;

	@Autowired
	private LocationService locationService;

	@Autowired
	private LogDetailRepository logDetailRepository;

	@Autowired
	private LogDetailCustomRepo detailCustom;

	@Autowired
	private ItemRepository itemRepository;

	protected LogDetailService(LogDetailRepository repository) {
		super(repository, LogDetailDto.class, LogDetail.class);
	}

	@Transactional
	public void upload(List<MultipartFile> multipartFiles) throws Exception {
		if (multipartFiles.isEmpty()) {
			throw new Exception("have no log test!");
		}
		if (multipartFiles.size() < 2) {
			throw new Exception("The number of files needs greater than 2 (Json file, Text file)");
		}
		if (multipartFiles.size() > 5) {
			throw new Exception("Do not upload more than 5 log files");
		}

		byte[] object = multipartFiles.get(0).getBytes();
		UploadLogFileRequest uploadLogFileRequest = ModelMapperUtil.map(JSONObject.parse(object),
				UploadLogFileRequest.class);
		String sn = uploadLogFileRequest.getSn();
		String mlbsn = uploadLogFileRequest.getMlbsn();
		String pcname = uploadLogFileRequest.getPcname();
		String mode = uploadLogFileRequest.getMode();
		if ((sn == null || sn.isBlank()) && (mlbsn == null || mlbsn.isBlank())) {
			throw new Exception("sn and mblsn is empty!");
		}
		if (mode == null || mode.isBlank()) {
			throw new Exception("mode must be not null or empty!");
		}
		if (pcname == null || pcname.isBlank()) {
			throw new Exception("pcname is empty!");
		}
		String sProduct = uploadLogFileRequest.getProduct();
		String sStation = uploadLogFileRequest.getStation();
		String sLine = uploadLogFileRequest.getLine();
		if (uploadLogFileRequest == null || sProduct == null || sProduct.isBlank() || sStation == null
				|| sStation.isBlank() || sLine == null || sLine.isBlank()) {
			throw new Exception("invalid location!");
		}
		String status = uploadLogFileRequest.getStatus();
		if (status == null || (!status.equalsIgnoreCase("passed") && !status.equalsIgnoreCase("failed")
				&& !status.equalsIgnoreCase("failed") && !status.equalsIgnoreCase("cancelled"))) {
			throw new Exception(String.format("invalid test status: %s (passed, failed, cancelled)!", status));
		}
		String onSfis = uploadLogFileRequest.getOnSfis();
		if (onSfis == null || (!onSfis.equalsIgnoreCase("on") && !onSfis.equalsIgnoreCase("off"))) {
			throw new Exception(String.format("invalid onSfis: %s (on, off)", onSfis));
		}
		boolean isPass = status != null && status.equalsIgnoreCase("passed");
		String errorcode = uploadLogFileRequest.getError_code();
		if ((isPass && errorcode != null && !errorcode.isBlank())
				|| (!isPass && (errorcode == null || errorcode.isBlank()))) {
			throw new Exception(String.format("test status: %s / errorcode: %s", status, errorcode));
		}
		Instant startTime = uploadLogFileRequest.getStart_time();
		Instant finishTime = uploadLogFileRequest.getFinish_time();
		if (startTime == null || finishTime == null || startTime.isAfter(finishTime)) {
			throw new Exception("invalid test time");
		}
		Product product = this.locationService.getProduct(sProduct);
		Station station = this.locationService.getStation(sStation);
		Line line = this.locationService.getLine(sLine);
		LogDetail logDetail = ModelMapperUtil.map(uploadLogFileRequest, enityClass);
		List<Item> items = logDetail.getTests();
		if (items.isEmpty()) {
			throw new Exception("items is empty!");
		}
		logDetail.setProduct(product);
		logDetail.setStation(station);
		logDetail.setLine(line);
		String date = Util.instantCvtString(finishTime, "yyyy-MM-dd");
		String nameSaved = String.format("%s_%s_%s_%s_%s_%s_%s_%s_%s", date, product, station, line, mlbsn, sn, status,
				errorcode, System.currentTimeMillis()).replaceAll("\\_\\_", "_");
		String dirString = String.format("%s/%s/%s/%s/%s", date, product, station, line, mode);
		MultipartFile multipartFile = multipartFiles.get(1);
		String type = getType(multipartFile);
		if (type == null || !type.trim().toLowerCase().equals("txt")) {
			throw new Exception("the second file must be a text log");
		}
		///////////////////////////////////////////
		FileLog fileLog = new FileLog();
		fileLog.setName(String.format("%s_serial.%s", nameSaved, type));
		String dir = String.format("%s/serial/%s", dirString, fileLog.getName());
		fileLog.setPath(dir);
		logDetail.addFilelog(this.filelogRepository.save(fileLog));
		this.storageService.storeFile(multipartFile, dir);
		for (int i = 2; i < multipartFiles.size(); i++) {
			multipartFile = multipartFiles.get(i);
			String fType = getType(multipartFile);
			fileLog = new FileLog();
			String fileName = multipartFile.getOriginalFilename().trim().toLowerCase();
			String newName = String.format("%s_%s.%s", nameSaved, i - 1, fType);
			if (fileName.contains("litepoint")) {
				newName = String.format("%s_litepoint_%s.%s", nameSaved, i - 1, fType);
			} else if (fileName.contains("ble")) {
				newName = String.format("%s_BLE_%s.%s", nameSaved, i - 1, fType);
			} else if (fileName.contains("wifi")) {
				newName = String.format("%s_WIFI_%s.%s", nameSaved, i - 1, fType);
			}
			fileLog.setName(newName);
			dir = String.format("%s/other/%s", dirString, fileLog.getName());
			fileLog.setPath(dir);
			logDetail.addFilelog(this.filelogRepository.save(fileLog));
			this.storageService.storeFile(multipartFile, dir);
		}
		logDetail = this.logDetailRepository.save(logDetail);
		for (Item item : items) {
			if (item == null) {
				throw new Exception("item == null!");
			}
			this.itemRepository.save(item);
		}

	}

	public void delete(Long... ids) {
		if (ids == null) {
			return;
		}
		for (Long id : ids) {
			delete(id);
		}
	}

	public void delete(Long id) {
		List<FileLog> fileLogs = this.filelogRepository.findAllByLogdetailId(id);
		if (fileLogs != null) {
			for (FileLog fileLog : fileLogs) {
				String path = fileLog.getPath();
				if (path != null && !path.isBlank()) {
					try {
						this.storageService.deleteFile(path);
					} catch (Exception e) {
						throw new RuntimeException(String.format("Delete log id(%s) - path(%s) failed: %s", id, path,
								e.getLocalizedMessage()));
					}
				}
			}
			this.logDetailRepository.deleteById(id);
		}
	}

	private String getType(MultipartFile multipartFile) {
		String fileName = multipartFile.getOriginalFilename();
		if (!fileName.contains(".")) {
			return null;
		}
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	public Resource downloadFile(String filepath) {
		Resource resource = storageService.loadFileAsResource(filepath);
		return resource;
	}

	public List<LogDetailDto> findAllLogDetailDto(RequestDto rd, List<String> items, PageRequest pageable, Sort sort)
			throws Exception {
		return convertToDtos(detailCustom.findAllByParameter(rd, LogDetail.class, items, pageable, sort));
	}
	
	public List<Long> findIds(RequestDto rd, PageRequest pageable, Sort sort)
			throws Exception {
		return detailCustom.findIds(rd, pageable, sort);
	}

	public List<LogDetail> findAllLogDetail(RequestDto rd, PageRequest pageable, Sort sort) throws Exception {
		return detailCustom.findAllCustom(rd, LogDetail.class, pageable, sort);
	}

	public Long count(RequestDto filter) {
		return this.detailCustom.count(filter);
	}

	public List<LogDetailDtoShort> findAllLogDetailShortDto(RequestDto requestDto, PageRequest pageable, Sort sort) {
		return detailCustom.findAllCustom(requestDto, LogDetailDtoShort.class, pageable, sort);
	}

	public List<LogDetailDtoShort> findAllLogDetailShortDtoByIds(RequestDto requestDto, Sort sort) {
		return detailCustom.findAllCustomByids(requestDto, sort, LogDetailDtoShort.class);
	}
	
	public List<LogDetailDto> findAllLogDetailDtoByIds(RequestDto requestDto, Sort sort) {
		return convertToDtos(detailCustom.findAllCustomByids(requestDto, sort, LogDetail.class));
	}
}
