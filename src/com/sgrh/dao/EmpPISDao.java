package com.sgrh.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.stat.SessionStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.conf.pis.component.DeptMaster;
import com.conf.pis.component.DesigMaster;
import com.conf.pis.component.PISEmployee;

@Repository
public class EmpPISDao {
	@Autowired
	@Qualifier(value="PIS_factory")
	SessionFactory factory;
	
	@Transactional("PIS")
	public PISEmployee getEmpFromPis(String empCode){
		//SessionFactory factory = factoryBean.getObject();
		Session session = factory.getCurrentSession();
		PISEmployee emp = session.get(PISEmployee.class, empCode);
		session.flush();
		//CriteriaBuilder builder = session.getCriteriaBuilder();
		//CriteriaQuery<PISEmployee> criteriaQuery = builder.createQuery(PISEmployee.class);
		//Root<PISEmployee> pisEmployee = criteriaQuery.from(PISEmployee.class);
		
		return emp;
	}
	
	@Transactional("PIS")
	public List<String> getDeptMasterList(){
		//SessionFactory factory = factoryBean.getObject();
		Session session = factory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<String> criteriaQuery = builder.createQuery(String.class);
		Root<DeptMaster> pisRoot = criteriaQuery.from(DeptMaster.class);
		criteriaQuery.select(pisRoot.get("dept"));
		
		TypedQuery<String> deptQuery = session.createQuery(criteriaQuery);
		return deptQuery.getResultList();
	}
	
	@Transactional("PIS")
	public List<String> getDesigList(){
		//SessionFactory factory = factoryBean.getObject();
		Session session = factory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<String> criteriaQuery = builder.createQuery(String.class);
		Root<DesigMaster> pisRoot = criteriaQuery.from(DesigMaster.class);
		criteriaQuery.select(pisRoot.get("desig"));
		
		TypedQuery<String> desigQuery = session.createQuery(criteriaQuery);
		return desigQuery.getResultList();
	}
}