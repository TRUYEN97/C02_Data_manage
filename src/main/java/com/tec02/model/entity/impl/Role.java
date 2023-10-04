package com.tec02.model.entity.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;

import com.tec02.model.entity.AbsIdAndCreateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
@EntityListeners(AuditingEntityListener.class)
public class Role extends AbsIdAndCreateTime implements GrantedAuthority{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4174694370556897669L;
	/**
	 * 
	 */
	@CreatedBy
	@ManyToOne
	@JoinColumn(name = "createby", updatable = false)
	protected User createBy;
	
	private String name;
	
	@ManyToMany(mappedBy = "userRoles")
	private Set<User> users = new HashSet<>();
	
	public void addUser(User user) {
		users.add(user);
	}

	public void removeUser(User user) {
		users.remove(user);
	}
	
	public void addUsers(List<User> listUser) {
		users.addAll(listUser);
	}

	@Override
	public String getAuthority() {
		return name;
	}
	
}
