package com.conf.pis.component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name="DeptMast")
@Immutable
public class DeptMaster {
	@Id
	@Column(name="DeptCode",updatable=false, insertable=false)
	private int id;
	
	@Column(name="dept",updatable=false, insertable=false)
	private String dept;
	
	@OneToOne(mappedBy="empDept")
	private PISEmployee pisEmp;
		
	public int getId(){
		return id;
	}
	public String getDept(){
		return dept;
	}
	public PISEmployee getPisEmp() {
		return pisEmp;
	}
	/*public void setPisEmp(PISEmployee pisEmp) {
		this.pisEmp = pisEmp;
	}*/
	
	
}
