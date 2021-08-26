package com.invoicing.backend.trigger.service;

import java.sql.Timestamp;

import java.text.ParseException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoicing.backend.trigger.dao.TransactionsDao;
import com.invoicing.backend.trigger.model.Transaction;


@Service("TransactionsService")
@Transactional
public class TransactionsServiceImpl implements TransactionsService {
	@Autowired
    private TransactionsDao dao;

	public void addtransaction(Transaction t) {
		// TODO Auto-generated method stub
		dao.addtransaction(t);
	}

	public List<Transaction> getlist() {
		// TODO Auto-generated method stub
		return dao.getlist();
	}

	public boolean checkexistancetransaction(String transactionID) {
		// TODO Auto-generated method stub
		return dao.checkexistancetransaction(transactionID);
	}

	
	public long countnbtransaction() {
		return 0;
	}





}
