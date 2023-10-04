package com.tec02.model.entity.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tec02.model.entity.AbsIdAndCreateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class User extends AbsIdAndCreateTime implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6057213588216190866L;
	/**
	 * 
	 */
	@CreatedBy
	@ManyToOne
	@JoinColumn(name = "createby", updatable = false)
	protected User createBy;
	
	@Column(name = "userid", updatable = false, unique = true, length = 10)
	private String userId;
	@Column(name = "userpass", length = 60)
	private String password;
	@Column(name = "userstatus", nullable = false)
	private Boolean userstatus = true;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> userRoles = new HashSet<>();
	
	private String name;

	public void addRole(Role role) {
		userRoles.add(role);
	}

	public void removeRole(Role role) {
		userRoles.remove(role);
	}

	public void addRoles(List<Role> roles) {
		userRoles.addAll(roles);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return userRoles;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.userstatus;
	}

	@Override
	public String getUsername() {
		return name;
	}
}
