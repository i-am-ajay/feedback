package com.sgrh.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.conf.component.Employee;
import com.sgrh.service.EmployeeFeedbackService;

@Controller
public class MainController {
	@Autowired
	EmployeeFeedbackService eFS;
	Employee empGlobal;
	
	@RequestMapping("/")
	public String home(Model model) {
		Employee empTemp = new Employee();
		model.addAttribute("empInit", empTemp);
		return "index";
	}
	
	@RequestMapping("feedback")
	public String feedback(Model model,HttpSession session,@ModelAttribute("empInit") Employee empInit) {
		eFS.generatedQuestions();
		empGlobal = eFS.startEmployeeFeedback(empInit.getEmpCode(), empInit.getDepartment(), empInit.getDesignation());
		eFS.saveFeedback(empGlobal);
		System.out.println(empGlobal.getEmpCode());
		System.out.println("Size of list: "+empGlobal.getFeedbackList().get(0).getChoiceList().size());
		model.addAttribute("emp", empGlobal);
		return "feedback";
	}
	
	@RequestMapping(value = "submit_form", method=RequestMethod.POST)
	public String formSubmission(Model model, @ModelAttribute("emp") Employee emp) {
		System.out.println(emp.getEmpCode());
		System.out.println(emp.getFeedbackList().get(0));
		System.out.println(emp.getFeedbackList().get(0).getChoiceList().get(2).getAnswer());
		eFS.saveFeedback(emp);
		
		return "form_submitted";
	}
}
