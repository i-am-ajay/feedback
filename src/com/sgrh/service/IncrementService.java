package com.sgrh.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgrh.dao.IncrementDao;

@Service
public class IncrementService {
	 @Autowired
	 IncrementDao incrementDao;
	 
	 public Map<Integer,List<Double>> getPayMatrix(){
		 Map<Integer,List<Double>> hashMap = new HashMap<>();
		 List<Object[]> listObj = incrementDao.getPaymatrix();
		 /** There will be 1 row for each level. and in our map we'll have key as level and all the 
		  * available paybands of that level in list.
		  */
		 for(Object[] payBandObj : listObj) {
			 final List<Double> listOfValues = new ArrayList<>();
			 Arrays.stream(Arrays.copyOfRange(payBandObj, 1, payBandObj.length)).forEach(e -> {
				 double v = Double.valueOf(e.toString());
				 listOfValues.add(v);
			 });
			 hashMap.put(Integer.valueOf(payBandObj[0].toString()), listOfValues); 
		 }
	
		 return hashMap;
	 }
	 
	 public List<Object[]> getDueIncrement(LocalDate startDate, LocalDate endDate){
		 return incrementDao.employeeList(startDate, endDate);
	 }
}
