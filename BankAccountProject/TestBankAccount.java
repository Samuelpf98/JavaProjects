import java.util.*;

public class TestBankAccount{
	public static void main(String[] args){
		
		ArrayList<BankAccount> Accounts = new ArrayList<BankAccount>();
		
		SavingsAccount Save1 = new SavingsAccount("Adam", 0, 1000);
		CurrentAccount Current1 = new CurrentAccount("John", 1, 500, -300);
	
		Accounts.add(Save1);
		Accounts.add(Current1);
		
		Accounts.get(0).setAnnualInterestRate(0.06);
		Accounts.get(1).setAnnualInterestRate(0.06);
		
		Save1.deposit(20);
		Save1.withdraw(50);
		
		Current1.deposit(70);
		Current1.withdraw(200);
		
		System.out.println();
		System.out.print("Name, ID, Bal , MonIn, AnIn,    Date      \n");
		System.out.println(Save1.toString());
		
		System.out.println();
		
		System.out.print("Name, ID, Bal , MonIn, AnIn,         Date           , OvDrt    \n");
		System.out.println(Current1.toString());
		
	
	}
}
		