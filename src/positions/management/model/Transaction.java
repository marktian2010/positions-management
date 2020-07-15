package positions.management.model;

public class Transaction {

//	private int transactionID;
	private int tradeID;
	private int version;
	private String sectionCode;
	private double quantity;
	private String action;
	private String buyOrSell;
	
	public Transaction(int tradeID,int version,
			String sectionCode,int quantity,String action,String buyOrSell){
//		this.transactionID=transactionID;
		this.tradeID=tradeID;
		this.version=version;
		this.sectionCode=sectionCode;
		this.quantity=quantity;
		this.action=action;
		this.buyOrSell=buyOrSell;
	}
	
//	public int getTransactionID() {
//		return transactionID;
//	}
//	public void setTransactionID(int transactionID) {
//		this.transactionID = transactionID;
//	}
	public int getTradeID() {
		return tradeID;
	}
	public void setTradeID(int tradeID) {
		this.tradeID = tradeID;
	}
	public String getSectionCode() {
		return sectionCode;
	}
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getBuyOrSell() {
		return buyOrSell;
	}
	public void setBuyOrSell(String buyOrSell) {
		this.buyOrSell = buyOrSell;
	}
	
	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	@Override
	public String toString() {
		return "Transaction [tradeID=" + tradeID + ", version=" + version
				+ ", sectionCode=" + sectionCode + ", quantity=" + quantity + ", action=" + action + ", buyOrSell="
				+ buyOrSell + "]";
	}
}
