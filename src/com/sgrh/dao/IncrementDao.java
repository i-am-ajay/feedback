package com.sgrh.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class IncrementDao {
	/**
	 *  Create a method to fetch paymatrix in the program. Instead of finding each employee increment from
	 *  database better approach will be to store paymatrix in memory and then search employee increment.
	 *  Logic:
	 *  	* Run a query to select complete paymatrix.
	 *      * At program level we'll get an array of arrays. Traverse through first array, store first index
	 *      * in a map and rest of the values in a list.
	 */
	
	@Autowired
	@Qualifier("saral_factory")
	SessionFactory saralFactory;
	
	@Autowired
	@Qualifier("feedback_factory")
	SessionFactory payMatrixFactory;
	
	
	
	@Transactional("feedback")
	public List getPaymatrix(){
		Session session = payMatrixFactory.getCurrentSession();
		Query paymatrixQuery = session.createNativeQuery("SELECT * FROM Paymatrix");
		return paymatrixQuery.getResultList();
	}
	
	@Transactional("saral")
	public List employeeList(LocalDate sDate, LocalDate eDate){
		Session session = saralFactory.getCurrentSession();
		String incrementQueryString = "SELECT \r\n" + 
				"	 MAS_EMPLOYEE.REFNO EMPCODE,\r\n" + 
				"	 MAS_EMPLOYEE.EMPNAME NAME,\r\n" + 
				"	 (SELECT TOP 1 BASIC FROM SLC_SALARYRATE_E WHERE EMPID = SLC_INCRDUE.EMPID ORDER BY SALRATEID DESC) BASIC,\r\n" + 
				"	 MAS_EMPLOYEEDET.GRADE LEVEL,\r\n" + 
				"	 CAST(SLC_INCRDUE.DATE AS DATE) INC_DATE,\r\n" + 
				"	 CAST(DATEADD(year, 1, SLC_INCRDUE.DATE) AS DATE) NEXT_INCREMENT\r\n" + 
				"FROM \r\n" + 
				"	SLC_INCRDUE(NOLOCK)\r\n" + 
				"JOIN MAS_EMPLOYEE ON 1=1\r\n" + 
				"	AND MAS_EMPLOYEE.EMPID = SLC_INCRDUE.EMPID\r\n" + 
				"	AND MAS_EMPLOYEE.DOL IS NULL\r\n" + 
				"JOIN MAS_EMPLOYEEDET ON 1=1\r\n" + 
				"	AND MAS_EMPLOYEEDET.EMPID = MAS_EMPLOYEE.EMPID\r\n" + 
				"	AND MAS_EMPLOYEEDET.EFFTODATE IS NULL\r\n" + 
				"	AND MAS_EMPLOYEEDET.STRUCTID = 1\r\n" + 
				"WHERE 1=1\r\n" + 
				"	AND DATE BETWEEN :startDate AND :endDate AND STATUS = 0"
				+ "Order By INC_DATE, EMPCODE ASC";
		Query incrementQuery = session.createNativeQuery(incrementQueryString);
		incrementQuery.setParameter("startDate", sDate);
		incrementQuery.setParameter("endDate", eDate);
		return incrementQuery.getResultList();
	}
}
