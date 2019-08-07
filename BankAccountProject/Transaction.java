import java.time.LocalDateTime;

public class Transaction{
	
	private LocalDateTime date = LocalDateTime.now();;
	private String type = "";
	private double amount;
	private double balance;
	private String description;
	
	public Transaction(){
	}
	
	public Transaction(String type, double amount, double balance, String description){
		this.type = type;
		this.amount = amount;
		this.balance = balance;
		this.description = description;
	}
	
	public String toString(){
		String details ="";
		details+= "Type: "+this.type+ "\n";
		
		details+= "Amount: "+this.amount+ "\n";
	
		details+= "Balance: "+this.balance+ "\n";
		
		details+= "Description: "+this.description+ "\n";
		
		details+= "Date: "+this.date+"\n";
		
		return details;
		
		
	}
	
	
}