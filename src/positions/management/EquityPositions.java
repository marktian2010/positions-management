package positions.management;

import java.util.List;

import positions.management.dao.TransactionDao;
import positions.management.dao.impl.TransactionDaoImpl;
import positions.management.model.Transaction;

public class EquityPositions {
	
	private static boolean started = false;
    private static PositionsHandler handler;
	
	public static void main(String[] args) {
	  TransactionDao transactionDao=new TransactionDaoImpl();
	  List<Transaction> inputTransactions=transactionDao.getTransactions();

	  start(new PositionsHandler());

	  inputTransactions.forEach(trade->{
		  handler.handleTrade(trade);
	  });
	  
	  handler.listPositions();
	}
	
	public static boolean start(PositionsHandler handler) throws IllegalArgumentException {
        if (handler == null) {
            throw new IllegalArgumentException("PositionsHandler was null in start()");
        }

        if (!started) {
            started = true;
            EquityPositions.handler = handler;
            return true;
        } else {
            return false;
        }
    }
}
