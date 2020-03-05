package com.sgrh.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.conf.component.Employee;
import com.sgrh.service.EmployeeFeedbackService;

@Controller
public class MainController {
	@Autowired
	EmployeeFeedbackService eFS;
	
	@RequestMapping("/")
	public String home(Model model,HttpSession session) {
		eFS.generatedQuestions();
		Employee emp = eFS.startEmployeeFeedback("GAA8664", "IT", "Software Programmer");
		eFS.saveFeedback(emp);
		session.setAttribute("user", emp);
		return "index";
	}
	
	@RequestMapping("feedback")
	public String feedback(Model model,HttpSession session) {
		model.addAttribute("user", session.getAttribute("user"));
		return "feedback";
	}
}
