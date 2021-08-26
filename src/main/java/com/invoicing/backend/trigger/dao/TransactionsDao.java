package com.invoicing.backend.trigger.dao;

import java.util.List;

import com.invoicing.backend.trigger.model.Transaction;


public interface TransactionsDao {

void addtransaction(Transaction t);
List<Transaction> getlist();
boolean checkexistancetransaction(String transactionID);
}
