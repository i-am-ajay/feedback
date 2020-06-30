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
	@Column(name="creation_date")
	@Generated(GenerationTime.INSERT)
	private LocalDate creationDate;
	@Column(name="created_by")
	private String createdBy;
	
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
}
