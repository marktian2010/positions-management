package positions.management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import positions.management.model.Action;
import positions.management.model.Transaction;

public class PositionsHandler {
	
	private final HashMap<Integer, Transaction> trades;
    private final HashMap<String, Double> positions;
    private final HashMap<String, Double> prevPositions;
    private final List<Transaction> historyLog;

    public PositionsHandler() {
        trades = new HashMap<>();
        positions = new HashMap<>();
        prevPositions = new HashMap<>();
        historyLog = new ArrayList<>();
    }

    public synchronized boolean handleTrade(Transaction trade) throws IllegalArgumentException {
        validateTradeData(trade);

        historyLog.add(trade);

        if (trades.containsKey(trade.getTradeID())) {
        	Transaction oldTrade = trades.get(trade.getTradeID());
            if (oldTrade.getVersion() < trade.getVersion()) {
                if (oldTrade.getAction() != Action.CANCEL.name()) {
                    trades.put(trade.getTradeID(), trade);
                    refreshPosition(trade);
                } else {
                    // INSERT will always be 1st version of a Trade,
                    // CANCEL will always be last version of Trade.
                    // So we can't allow any INSERT/UPDATE after a CANCEL
                    // TODO: log an error? throw an exception ?
                }
            }
        } else {
            trades.put(trade.getTradeID(), trade);
            refreshPosition(trade);
        }

        return true;
    }

    private void validateTradeData(Transaction trade) {
        if (trade.getSectionCode() == null) {

            String wrongSecCode = String.format("Security code was wrong: %s", trade.getSectionCode());
            System.out.println(wrongSecCode);
            throw new IllegalArgumentException(wrongSecCode);
        }
    }

    /**
     * Simple output of all received trades, in order they arrived...idempotent
     */
    public void showHistoryLog() {
        System.out.println("Historical trades");
        for (Transaction trade : historyLog) {
            System.out.println(trade);
        }
    }

    /**
     * Simple function to list securities and the shares of the security. No changes are made to the positions.
     */
    public void listPositions() {
        for (Entry<String, Double> entry : positions.entrySet()) {
            System.out.println(String.format("%s : %f", entry.getKey(), entry.getValue()));
        }
    }

    /**
     * Get the Position of a specific secCode
     * @param secCode can be null/empty, should be the 3-letter rep of the seccode
     * @return the seccode Position, or 0.0 if the secCode is not found
     */
    public Double getPosition(String secCode) {
        if (positions.containsKey(secCode)) {
            return positions.get(secCode);
        } else {
            // TODO: debug log that this was empty/not found?
            return 0.0;
        }
    }

    private void refreshPosition(Transaction trade) {
        if (positions.containsKey(trade.getSectionCode())) {
            if (trade.getAction() == Action.CANCEL.name()) {
                positions.put(trade.getSectionCode(), prevPositions.get(trade.getSectionCode()));
            } else if (trade.getAction() == Action.UPDATE.name()) {
                double quant = prevPositions.get(trade.getSectionCode());
                quant += trade.getBuyOrSell().equals("Buy") ? trade.getQuantity() : -trade.getQuantity();
                positions.put(trade.getSectionCode(), quant);
            } else { // INSERT
                double quant = positions.get(trade.getSectionCode());
                quant += trade.getBuyOrSell().equals("Buy") ? trade.getQuantity() : -trade.getQuantity();
                positions.put(trade.getSectionCode(), quant);
            }
        } else { 
            prevPositions.put(trade.getSectionCode(), 0.0);
            if (trade.getAction() != Action.CANCEL.name()) {
                positions.put(trade.getSectionCode(), trade.getBuyOrSell().equals("Buy") ? trade.getQuantity() : -trade.getQuantity());
            } else {
                positions.put(trade.getSectionCode(), 0.0);
            }
        }
    }

}
