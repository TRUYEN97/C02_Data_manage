package com.tec02.Service.impl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tec02.Service.FileSystemStorageService;
import com.tec02.Service.impl.BaseService;
import com.tec02.model.dto.impl.FileLogDto;
import com.tec02.model.entity.impl.FileLog;
import com.tec02.repository.IBaseRepo;
import com.tec02.repository.impl.FilelogRepository;

@Service
public class FilelogService extends BaseService<FileLogDto, FileLog>{

	protected FilelogService(IBaseRepo<FileLog> repository) {
		super(repository, FileLogDto.class, FileLog.class);
	}
	
	@Autowired
	private FilelogRepository filelogRepository;
	
	@Autowired
	private FileSystemStorageService storageService;
	
	public List<FileLog> findAllByLogdetailId(List<Long> ids){
		if(ids == null || ids.isEmpty()) {
			throw new RuntimeException("id can't be null or empty!");
		}
		return filelogRepository.findAllByLogdetailIdIn(ids);
	}
	
	public List<FileLogDto> findAllDtoByLogdetailId(List<Long> ids){
		return convertToDtos(findAllByLogdetailId(ids));
	}
	
	@Override
	public void delete(Long id) {
		List<FileLog> fileLogs = this.filelogRepository.findAllByLogdetailId(id);
		if (fileLogs != null) {
			for (FileLog fileLog : fileLogs) {
				String path = fileLog.getPath();
				if (path != null && !path.isBlank()) {
					try {
						this.storageService.deleteFile(path);
					} catch (Exception e) {
						throw new RuntimeException(String.format("Delete file log id(%s) - path(%s) failed: %s", id, path,
								e.getLocalizedMessage()));
					}
				}
			}
		}
		super.delete(id);
	}
}
