package com.invoicing.backend.trigger.service;

import java.sql.Timestamp;

import java.util.List;

import com.invoicing.backend.trigger.model.Transaction;


public interface TransactionsService {
	void addtransaction(Transaction t);
	List<Transaction> getlist();
	boolean checkexistancetransaction(String transactionID);
	long countnbtransaction();


}
