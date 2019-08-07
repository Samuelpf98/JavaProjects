public class CurrentAccount extends BankAccount{
	
	private double overdraftLimit;
	
	public CurrentAccount(){
	}
	
	public CurrentAccount(int id, double balance, double overdraftLimit){
	super(id, balance);
	this.overdraftLimit = overdraftLimit;	
	
	}
	
	public CurrentAccount(String bankName, int id, double balance, double overdraftLimit){
		super(bankName, id, balance);
		this.overdraftLimit = overdraftLimit;
	}
	
	@Override
	public void withdraw(double cash){
		double balance = this.getBalance();
		if(overdraftLimit<balance-cash){
			super.withdraw(cash);
		}
		else if(overdraftLimit>balance-cash){
			System.out.println("Exceeds Overdraft Limit For Account: "+this.getID());
		}
	}
	
	@Override
	public String toString(){
		String details = "";
		details+= this.getBankName();
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
		details += ",";
		details += this.overdraftLimit;
		details += "\n Transaction History: \n";
		details += this.getTransactions().toString();
		
		
		return details;
	}
		
}