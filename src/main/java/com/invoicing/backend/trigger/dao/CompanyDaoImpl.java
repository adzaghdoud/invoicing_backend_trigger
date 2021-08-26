package com.invoicing.backend.trigger.dao;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.invoicing.backend.trigger.model.Company;



@Repository("CompanyDao")
public class CompanyDaoImpl extends  AbstractDao implements CompanyDao {


	public List<Company> getlistcompany() {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Company> criteria = builder.createQuery(Company.class);
		Root<Company> root = criteria.from(Company.class);
		criteria.select(root);
		Query<Company> q=getSession().createQuery(criteria);
		return q.list();
	}

	
	

}
