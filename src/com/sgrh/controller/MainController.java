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
	
	@RequestMapping("/")
	public String home(Model model) {
		
		//session.setAttribute("user", emp);
		return "index";
	}
	
	@RequestMapping("feedback")
	public String feedback(Model model,HttpSession session) {
		Employee emp = eFS.startEmployeeFeedback("GAA8664", "IT", "Software Programmer");
		eFS.generatedQuestions();
		eFS.saveFeedback(emp);
		System.out.println(emp.getEmpCode());
		model.addAttribute("emp", emp);
		return "feedback";
	}
	
	@RequestMapping(value = "submit_form", method=RequestMethod.POST)
	public String formSubmission(Model model, @ModelAttribute("emp") Employee emp) {
		System.out.println(emp.getEmpCode());
		System.out.println(emp.getFeedbackList().get(0));
		System.out.println(emp.getFeedbackList().get(0).getChoiceList().get(2).getAnswer());
		return "form_submitted";
	}
}
