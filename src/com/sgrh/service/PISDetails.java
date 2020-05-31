package com.sgrh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conf.pis.component.PISEmployee;
import com.sgrh.dao.EmpPISDao;

@Service
public class PISDetails {
	@Autowired
	EmpPISDao pisDao;
	
	public PISEmployee getEmployee(String empCode) {
		PISEmployee pisEmp = pisDao.getEmpFromPis(empCode);
		return pisEmp;
	}
	
	public List<String> getDeptList(){
		return pisDao.getDeptMasterList();
	}
	
	public List<String> getDesigList(){
		return pisDao.getDesigList();
	}
	
}
