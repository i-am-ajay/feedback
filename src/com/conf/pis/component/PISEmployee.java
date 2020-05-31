package com.conf.pis.component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Table(name="EmpMast")
@Immutable
public class PISEmployee {
	@Id
	@Column(name="EmpCode",updatable=false, insertable=false)
	private String empCode;
	@Column(name="EmpName",updatable=false, insertable=false)
	private String empName;
	
	@OneToOne
	@JoinColumn(name="AppDeptCode")
	private DeptMaster empDept;
	
	@OneToOne
	@JoinColumn(name="AppDesigCode")
	private DesigMaster empDesig;
	
	public String getEmpCode() {
		return empCode;
	}
	
	public String getEmpName() {
		return empName;
	}
	
	public DeptMaster getEmpDept() {
		return empDept;
	}
	
	public DesigMaster getEmpDesig() {
		return empDesig;
	}
}
