import java.time.LocalDateTime;
import java.util.*;

public class BankAccount{
	
	private int id;
	private double balance;
	private double annualInterestRate;
	private LocalDateTime dateCreated = LocalDateTime.now();
	private String bankName;
	private ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	
	public BankAccount(){
	}
	
	public BankAccount(int id, double balance){
		this.id = id;
		this.balance = balance;
	}
	
	public BankAccount(String bankName, int id, double balance){
		this.bankName = bankName; 
		this.id = id;
		this.balance = balance;
	}
	
	public String getBankName(){
			return this.bankName;
	}
	
	public ArrayList getTransactions(){
		return this.transactions;
	}
	
	public void setID(int id){
		this.id=id;
	}
	public void setBalance(double balance){
		this.balance=balance;
	}
	public void setAnnualInterestRate( double annualInterestRate){
		this.annualInterestRate=annualInterestRate;
	}
	
	public int getID(){
		return this.id;
	}
	
	public double getBalance(){
		return this.balance;
	}
	
	public double getAnnualInterestRate(){
		return this.annualInterestRate;
	}
	
	public LocalDateTime getDate(){
		return this.dateCreated;
	}
		
			
	public double getMonthlyInterestRate(){
		double monthlyInterestRate;
		monthlyInterestRate =(annualInterestRate/12);
		return monthlyInterestRate;
	
	}
	
	
	public void withdraw(double cash){
		balance = balance-cash;
		transactions.add(new Transaction("W", cash, balance, "Withdrawing"));
	}
	
	public void deposit(double cash){
		balance = balance+cash;
		transactions.add(new Transaction("D", cash, balance, "Depositing"));
	}
	
	public String toString(){
		String details = "";
		details+= this.bankName;
		details += ", ";
		details += this.getID();
		details += ", ";
		details += this.getBalance();
		details += ", ";
		details += this.getMonthlyInterestRate();
		details += ", ";
		details += this.getAnnualInterestRate();
		details += ", ";
		details += this.getDate();
		details += "\n ";
		details += transactions.toString();
		
		
		
		return details;
	}
	
}
		