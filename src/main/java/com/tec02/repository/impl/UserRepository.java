package com.tec02.repository.impl;

import java.util.List;
import java.util.Optional;

import com.tec02.model.entity.impl.User;
import com.tec02.repository.IBaseRepo;

public interface UserRepository extends IBaseRepo<User> {

	List<User> findAllByUserstatus(boolean status);

	Optional<User> findByUserIdAndUserstatus(
			String userName, boolean b);

	boolean existsByUserIdOrName(String fullname, String username);

	Optional<User> findOneByName(String username);
	

}
