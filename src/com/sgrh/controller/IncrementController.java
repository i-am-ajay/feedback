package com.sgrh.controller;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sgrh.service.IncrementService;

@Controller
public class IncrementController {
	@Autowired
	IncrementService incrementService;
	
	Map<String, String> romanNumericMap;
	List<String> listMonth;
	
	//------------------------------------- Increment Details --------------------------------------------
	@RequestMapping("due_increment")
	public String incrementDetails(Model model, @RequestParam(name="month", required=false)String monthStr,
			@RequestParam(name="year", required=false, defaultValue="0")int year) {
		//date = "December-2020";
		model.addAttribute("month",getMonth());
		model.addAttribute("current_month", YearMonth.now().getMonth().toString());
		YearMonth now = YearMonth.now(); 
		model.addAttribute("year",new int[] {now.getYear(),now.getYear()-1});
		System.out.println(monthStr);
		System.out.println(year);
		if(monthStr != null) {
			Month month = Month.valueOf(monthStr.trim());
			YearMonth yearMonth = YearMonth.of(year, month);
			LocalDate startDate = yearMonth.atDay(1);
			LocalDate endDate = yearMonth.atEndOfMonth();
			Map<Integer,List<Double>> payMatrixMap = incrementService.getPayMatrix();
			List<Object[]> listEmp = incrementService.getDueIncrement(startDate, endDate);
			List<Object> updatedList = new ArrayList<>();
			for(Object[] emp : listEmp) {
				double amount = newBasicFromPayMatrix(emp[2].toString(), emp[3].toString(), payMatrixMap);
				updatedList.add(new Object[] {emp, amount});
			}
			
			model.addAttribute("emp_list",updatedList);
			model.addAttribute("current_month", monthStr);
			model.addAttribute("current_year",year);
		}
		model.addAttribute("status","success");
		return "increment_process";
	}

//----------------------------------------------- End of Increment Logic -----------------------------------------

//----------------------------------------------- Utility Method -------------------------------------------------
	public double newBasicFromPayMatrix(String basic, String level, Map<Integer,List<Double>> paymatrixMap ) {
		//int levelNum = Integer.valueOf(getLevel(level));
		double amount = 0.0;
		List<Double> payband = paymatrixMap.get(Integer.parseInt(level));
		int index = payband.indexOf(Double.valueOf(basic));
		if(index != -1) {
			index = (index == payband.size()) ? index : index + 1; 
			amount = payband.get(index);
		}
		return  amount;
	}
	
	/*@PostConstruct
	public void levelMap() {
		romanNumericMap = new HashMap<>();
		romanNumericMap.put("I", "1");
		romanNumericMap.put("II", "2");
		romanNumericMap.put("III", "3");
		romanNumericMap.put("IV", "4");
		romanNumericMap.put("V", "5");
		romanNumericMap.put("VI", "6");
		romanNumericMap.put("VII", "7");
		romanNumericMap.put("VIII", "8");
		romanNumericMap.put("IX", "9");
		romanNumericMap.put("X", "10");
		romanNumericMap.put("XI","11");
		romanNumericMap.put("XII", "12");
		romanNumericMap.put("Consolidated", "2");
		
	}*/
	
	public String getLevel(String roman) {
		
		return romanNumericMap.get(roman) != null ? romanNumericMap.get(roman) : "0";
	}
	
	public List<String> getMonth() {
		if(listMonth == null || listMonth.size() <= 0) {
			listMonth = new ArrayList<>();
			Month[] month = Month.values();
			Arrays.stream(month).forEach(e ->{
				listMonth.add(e.toString());
			});
		}
		return listMonth;
	}

//----------------------------------------------- End Utility Method ---------------------------------------------

//----------------------------------------------- Exception Handling -------------------------------------------
	@ExceptionHandler(Exception.class)
	public String errorHandler(Model model) {
		model.addAttribute("status","failed");
		return "increment_process";
	}
	
//----------------------------------------------End Exception Handling --------------------------------------------
}