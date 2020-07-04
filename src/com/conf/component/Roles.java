package com.conf.component;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Embeddable
public class Roles {
	private String role;
	@Column(name="active_role")
	private boolean activeRole;
	
	@Generated(GenerationTime.INSERT)
	@Column(name="creation_date",insertable=false, updatable=false)
	private LocalDate creationDate;
	
	@Column(name="created_by")
	private String createdBy;
	
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isActiveRole() {
		return activeRole;
	}
	public void setActiveRole(boolean active) {
		this.activeRole = active;
	}
	public LocalDate getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
}
