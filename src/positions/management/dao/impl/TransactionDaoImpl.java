package positions.management.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import positions.management.dao.TransactionDao;
import positions.management.model.Transaction;

public class TransactionDaoImpl implements TransactionDao {
 
	public List<Transaction> getTransactions() {
		List<Transaction> transactions=new ArrayList<Transaction>();
		transactions.add(new Transaction(1,1,"REL",50,"INSERT","Buy"));
		transactions.add(new Transaction(2,1,"ITC",40,"INSERT","Sell"));
		transactions.add(new Transaction(3,1,"INF",70,"INSERT","Buy"));
		transactions.add(new Transaction(1,2,"REL",60,"UPDATE","Buy"));
		transactions.add(new Transaction(2,2,"ITC",30,"CANCEL","Buy"));
		transactions.add(new Transaction(4,1,"INF",20,"INSERT","Sell"));
		Collections.shuffle(transactions);
		return transactions;
	}

}
