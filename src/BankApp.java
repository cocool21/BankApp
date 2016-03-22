import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
public class BankApp {
	public static void main(String[] args){
		boolean quit=false;
		Scanner sc=new Scanner(System.in);
		String value;
		String name;
		Date date=new Date();
		Account newAccount = new Account();
		ArrayList<Account> accounts=new ArrayList<Account>();
		Transaction newTrans=new Transaction();
		ArrayList<Transaction> transactions=new ArrayList<Transaction>();
		Customer newCus=new Customer();

		System.out.println("Welcome to Dalton Corp Savings and Loan");
		while(quit==false){
			System.out.println("Please choose the service you want to use from the menu below");
			System.out.println("Create an account- Enter 1");
			System.out.println("Make transaction-- Enter 2");
			System.out.println("Quit ------------- Enter -1");
			value=sc.nextLine();
			if(value.equals("1")){
				//create an account
				System.out.println("Please enter your name");	
				name=sc.nextLine();

				//Customer newCus=new Customer();

				newCus.findCustomer(name);

				if(newCus.getCustomerID()==0){
					System.out.println("Customer not found. New record added.");
					newCus.setCustomerName(name);
					newCus.addCustomer(newCus);
					newCus.setCustomerID(newCus.findCustomer(name).getCustomerID());
					//creating an account
					System.out.println("Savings or checkings?(s/c)");
					value = sc.nextLine();
					if (value.equalsIgnoreCase("s"))
					{
						System.out.println("Account number for Savings: ");
						newAccount.setAccNum(sc.nextLine());
						newAccount.setFlag("s");
					}
					else //checking account
					{
						System.out.println("Account number for checking: ");
						newAccount.setAccNum(sc.nextLine());
						newAccount.setFlag("c");

					}

					newAccount.setCustomerID(newCus.getCustomerID());
					System.out.println(newAccount.getCustomerID());
					newAccount.addAccount(newAccount);

				}else{
					//if the customer has an account already
					newCus.findCustomer(name);
					int id=newCus.getCustomerID();
					accounts=newAccount.findCustomerAccount(id);
					if(accounts.size()==1){
						newAccount=accounts.get(0);
						if(newAccount.getFlag().equals("s")){
							System.out.println("Account number for checking: ");
							newAccount.setAccNum(sc.nextLine());
							newAccount.setFlag("c");
						}else{
							System.out.println("Account number for Savings: ");
							newAccount.setAccNum(sc.nextLine());
							newAccount.setFlag("s");
						}

						newAccount.setCustomerID(newCus.getCustomerID());
						newAccount.addAccount(newAccount);
					}else if(accounts.size()==2){
						System.out.println("You have two accounts already.");
					}else if(accounts.size()==0){
						System.out.println("Savings or checkings?(s/c)");
						value = sc.nextLine();
						if (value.equalsIgnoreCase("s"))
						{
							System.out.println("Account number for Savings: ");
							newAccount.setAccNum(sc.nextLine());
							newAccount.setFlag("s");
						}
						else //checking account
						{
							System.out.println("Account number for checking: ");
							newAccount.setAccNum(sc.nextLine());
							newAccount.setFlag("c");

						}

						newAccount.setCustomerID(newCus.getCustomerID());
						newAccount.addAccount(newAccount);
					}else{
						System.out.println("Error occurs, please fix it.");
					}

				}

			}else if(value.equals("2")){
				//create a transaction
				System.out.println("Enter the acct number: ");
				value=sc.nextLine();
				newAccount=newAccount.findAccount(value);
				int anyAccount;
				if(newAccount.getAccNum().equals(""))//NO Account yet
					anyAccount = 0;
				else
					anyAccount = 1;
				switch (anyAccount) {
				case 0:
					//need a new account
					System.out.println("Account not found.");
					break;
				case 1:
					//creating a transaction
					System.out.println("Enter a transaction type(Check(c),Deposit(d) or Withdrawl(w)) or -1 to finish: ");	
					value=sc.nextLine();
					if(value.equalsIgnoreCase("c")){
						newTrans.setFlag("c");
						newTrans.setDate(date);
						newTrans.setAccountID(newAccount.getAccID());
						System.out.println("Enter the amount of the check");
						double temp=sc.nextDouble();
						sc.nextLine();
						if((newTrans.CalculateBalance(newAccount.getAccID())-temp)>=0){

							newTrans.setAmount(-temp);

							System.out.println("Enter the check number");
							newTrans.setCheckNum(sc.nextLine());
							newTrans.addTransaction(newTrans);
						}else{
							Transaction fee=new Transaction();
							fee.setAccountID(newAccount.getAccID());
							fee.setAmount(-35);
							fee.setDate(date);
							fee.setFlag("f");
							fee.addTransaction(fee);
							newTrans.setAmount(-temp);
							System.out.println("Enter the check number");
							newTrans.setCheckNum(sc.nextLine());
							newTrans.addTransaction(newTrans);
						}


					}else if(value.equalsIgnoreCase("d")){
						newTrans.setFlag("d");
						newTrans.setDate(date);
						newTrans.setAccountID(newAccount.getAccID());
						System.out.println("Enter the amount of the deposit");
						newTrans.setAmount(sc.nextDouble());
						sc.nextLine();
						newTrans.addTransaction(newTrans);

					}else if(value.equalsIgnoreCase("w")){
						newTrans.setFlag("w");
						newTrans.setDate(date);
						newTrans.setAccountID(newAccount.getAccID());
						System.out.println("Enter the amount of the withdrawl");
						double temp=sc.nextDouble();
						sc.nextLine();
						if((newTrans.CalculateBalance(newAccount.getAccID())-temp)>=0){
							newTrans.setAmount(-temp);
							newTrans.addTransaction(newTrans);
						}else{
							Transaction fee=new Transaction();
							fee.setAccountID(newAccount.getAccID());
							fee.setAmount(-35);
							fee.setDate(date);
							fee.setFlag("f");
							fee.addTransaction(fee);
							newTrans.setAmount(-temp);
							newTrans.addTransaction(newTrans);

						}
					}else if(value.equals("-1")){
						quit=true;
						System.out.println("The balance for account "+newAccount.getAccNum()+" is: "+newTrans.CalculateBalance(newAccount.getAccID()));
					}else{
						System.out.println("Invalid input.");
					}
					break;
				}

			}else if(value.equals("-1")){
				quit=true;
				System.out.println("The balance for account "+newAccount.getAccNum()+" is: "+newTrans.CalculateBalance(newAccount.getAccID()));

			}else{
				System.out.println("Invalid input.");
			}

		}
		System.out.println("Thank you for using BankApp.");
		System.out.println("Good Bye.");
		System.out.println("");
		System.out.println("");
	}
}
