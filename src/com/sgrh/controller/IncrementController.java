package com.sgrh.controller;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sgrh.service.IncrementService;

@Controller
public class IncrementController {
	@Autowired
	IncrementService incrementService;
	
	Map<String, String> romanNumericMap;
	
	//------------------------------------- Increment Details --------------------------------------------
	@RequestMapping("due_increment")
	public String incrementDetails(Model model, @RequestParam(name="month", required=false)String date) {
		date = "December-2020";
		if(date != null) {
			String[] monthYear = date.split("-");
			int year = Integer.valueOf(monthYear[1]);
			Month month = Month.valueOf(monthYear[0].toUpperCase());
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
		}
		return "increment_process";
	}

//----------------------------------------------- End of Increment Logic -----------------------------------------

//----------------------------------------------- Utility Method -------------------------------------------------
	public double newBasicFromPayMatrix(String basic, String level, Map<Integer,List<Double>> paymatrixMap ) {
		System.out.println("LEVEL " +level + "BASIC "+basic);
		int levelNum = Integer.valueOf(getLevel(level));
		double amount = 0.0;
		List<Double> payband = paymatrixMap.get(levelNum);
		int index = payband.indexOf(Double.valueOf(basic));
		System.out.println(index);
		if(index != -1) {
			index = (index == payband.size()) ? index : index + 1; 
			amount = payband.get(index);
		}
		return  amount;
	}
	
	@PostConstruct
	public void levelMap() {
		System.out.println("Initializing map");
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
		
	}
	
	public String getLevel(String roman) {
		
		return romanNumericMap.get(roman) != null ? romanNumericMap.get(roman) : "0";
	}

//----------------------------------------------- End Utility Method ---------------------------------------------

}