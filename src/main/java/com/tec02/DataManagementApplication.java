package com.tec02;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tec02.model.entity.impl.Role;
import com.tec02.model.entity.impl.User;
import com.tec02.repository.impl.RoleRepository;
import com.tec02.repository.impl.UserRepository;

import jakarta.transaction.Transactional;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef="auditorAware")
public class DataManagementApplication implements CommandLineRunner {

	protected final Log logger = LogFactory.getLog(getClass());
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(DataManagementApplication.class, args);
	}
	
	@Override
	@Transactional
	public void run(String... args) throws Exception {
		if (userRepository.count() == 0) {
			User user = new User();
			Role role = new Role();
			role.setName("ADMIN");
			user.setName("Administator");
			user.setUserId("admin");
			user.addRole(role);
			user.setPassword(passwordEncoder.encode("123456"));
			roleRepository.save(role);
			userRepository.save(user);
		}else {
			List<User> userDtos = userRepository.findAll();
			userDtos.toString();
		}
	}
}
