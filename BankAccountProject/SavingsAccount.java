public class SavingsAccount extends BankAccount{
	
	
	public SavingsAccount(){
	}
	public SavingsAccount(int id, double balance){
		super(id, balance);
	}
	
	public SavingsAccount(String bankName, int id, double balance){
		super(bankName, id, balance);
	}
	
	@Override 
	public void withdraw(double cash){
	double balance = this.getBalance();
	if(0<=balance-cash){
		super.withdraw(cash);
	} else if((balance - cash)<0){
		System.out.println("Cannot withdraw For Account: "+this.getID());
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
		details += "\n Transaction History: \n ";
		details += this.getTransactions().toString();;
		
		return details;
		
	}
}
