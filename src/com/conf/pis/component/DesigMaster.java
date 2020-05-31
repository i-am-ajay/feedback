package com.conf.pis.component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Table(name="DesigMast")
@Immutable
public class DesigMaster {
	@Id
	@Column(name="DesigCode")
	private int id;
	
	@Column(name="Desig")
	private String desig;
	
	@OneToOne(mappedBy="empDesig")
	private PISEmployee employee;
	
	public int getId() {
		return id;
	}
	public String getDesig() {
		return desig;
	}
	public PISEmployee getEmployee() {
		return employee;
	}
}

