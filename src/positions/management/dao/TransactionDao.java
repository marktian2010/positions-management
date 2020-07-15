package positions.management.dao;

import java.util.List;

import positions.management.model.Transaction;

public interface TransactionDao {
	List<Transaction> getTransactions();
}
