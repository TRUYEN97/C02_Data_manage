package com.tec02.repository.impl;

import java.util.List;

import com.tec02.model.entity.impl.FileLog;
import com.tec02.repository.IBaseRepo;

public interface FilelogRepository extends IBaseRepo<FileLog> {
	List<FileLog> findAllByLogdetailId(Long id);

}
