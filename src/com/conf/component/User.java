package com.conf.component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.springframework.stereotype.Component;

@Entity
@Table(name="users")
public class User{
	@Id
	private String username;
	
	@Column(nullable=false)
	private String password;
	
	@Column(nullable=false, name="created_by")
	private String createdBy;
	
	@Column(nullable=false)
	private boolean active;
	
	@Generated(GenerationTime.INSERT)
	@Column(nullable=false, insertable=false, updatable=false, name="creation_date")
	private LocalDate creationDate;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="roles", joinColumns = {@JoinColumn(name = "user_id")})
	//@JoinColumn(name="user_id")
	@Embedded
	private List<Roles> roleList = new ArrayList<>();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public List<Roles> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Roles> roleList) {
		this.roleList = roleList;
	}
}
