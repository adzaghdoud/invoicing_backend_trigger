package com.invoicing.backend.trigger.service;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoicing.backend.trigger.dao.CompanyDao;
import com.invoicing.backend.trigger.model.Company;
@Service("CompanyService")
@Transactional
public class CompanyServiceImpl implements CompanyService {
	@Autowired
    private CompanyDao dao;

	public List<Company> getlistcompany() {
		return dao.getlistcompany();
	}
	
	
}
