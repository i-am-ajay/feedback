package com.sgrh.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.conf.component.Employee;
import com.sgrh.service.EmployeeFeedbackService;

@Controller
@SessionAttributes("emp")
public class MainController{
	@Autowired
	EmployeeFeedbackService eFS;
	
	Employee empGlobal;
	
	int feedbackId;
	
	Employee emp;
	
	@RequestMapping("/")
	public String home(Model model){
		Employee emp = new Employee();
		model.addAttribute("emp", emp);
		return "index";
	}
	
	@RequestMapping("feedback")
	public String feedback(Model model,HttpSession session,@ModelAttribute("emp") Employee empInit){
		eFS.generatedQuestions();
		empGlobal = eFS.startEmployeeFeedback(empInit.getEmpCode(), empInit.getDepartment(), empInit.getDesignation());
		eFS.saveFeedback(empGlobal);
		System.out.println("Feedback Id"+empGlobal.getCurrentFeedbackId());
		//System.out.println("Size of list: "+empGlobal.getFeedbackList().get(0).getChoiceList().size());
		//System.out.println("Feedback Employee"+empGlobal.getFeedbackList().get(0).getEmployee().getEmpCode());
		//feedbackId = empGlobal.getFeedbackList().get(0).getId();
		System.out.println(empGlobal.getFeedbackList().size());
		System.out.println(empGlobal.getFeedbackList().get(0).getChoiceList().get(1));
		model.addAttribute("emp", empGlobal);
		return "feedback";
	}
	
	@RequestMapping(value = "submit_form", method=RequestMethod.POST)
	public String formSubmission(Model model, @ModelAttribute("emp") Employee emp){
		System.out.println(emp.getEmpCode());
		//System.out.println(emp.getFeedbackList().get(0).getEmployee().getEmpCode());
		System.out.println(emp.getFeedbackList().get(0).getChoiceList().get(2).getQuestionid());
		eFS.updateFeeback(emp);
		return "form_submitted";
	}
}
