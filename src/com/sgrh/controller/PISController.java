package com.sgrh.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.conf.pis.component.PISEmployee;
import com.sgrh.service.PISService;

@Controller
public class PISController {
	@Autowired
	private PISService pisDetails;
	@RequestMapping(value="pisEmp")
	public @ResponseBody String getPisEmployee(@RequestParam(name="empCode")String empCode) {
		System.out.println("For compiling");
		PISEmployee emp = pisDetails.getEmployee(empCode);
		JSONObject object = new JSONObject();
		object.put("code", emp.getEmpCode());
		object.put("dept", emp.getEmpDept().getDept());
		object.put("desig", emp.getEmpDesig().getDesig());
		return object.toString();
	}
	// method to get pis dept list
	
	// method to get pis desig list
}
