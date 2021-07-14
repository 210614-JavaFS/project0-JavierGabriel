package com.revature.controllers;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

import com.revature.models.Account;
import com.revature.models.AccountType;
import com.revature.models.Client;
import com.revature.models.UserType;
import com.revature.services.AccountService;
import com.revature.services.ClientService;

public class ClientController {
	
	private static Scanner scan = new Scanner(System.in);
	private ClientService clientService = new ClientService();
	private AccountService accountService = new AccountService();
	private DecimalFormat df = new DecimalFormat("#,##0.00");
	
	//LogIn or Create Account Menu
	public Client getUser() {
		String clientSelection;
		System.out.println("Do you wish to log in or open a new account?\n"
				+ "1. Log In \n"
				+ "2. Create a new account \n"
				+ "3. Back to user selection menu ");
		clientSelection = scan.nextLine();
		Client client = null;
		while(clientSelection != "3") {
			
			switch(clientSelection) {
				case "1":
					client = login();
					if(client != null  && client.getUserType().toString().equals("Client"))
						return client;
					else
						System.out.println("Invalid credentials.\n");
						return getUser();
				case "2":
					client = newClientBuilder();
					if(client != null) {
						System.out.println(client);
						System.out.println("Your account was created succesfully!");
						return client;
					} else if(client == null){
						System.out.println("Error creating account. Try Again\n");
						return getUser();	
					}
					break;
				case "3":
					return null;
				default:
					System.out.println("That is not a valid choice, please try again");
					return getUser();
			}
		}
		return null;
}

	private Client newClientBuilder() {
	//Get all data to send to user service where client will be created
		ArrayList<Client> clients = clientService.getAllClients();
		ArrayList<String> usernames = new ArrayList<>();
		for(Client client: clients) {
			usernames.add(client.getName());
		}
	Client client = null;
	String user = "";
	String pass1 = "";
	String pass2 = "";
	do{
		if(usernames.contains(user)) {
			System.out.println("Username already in use.");
		}
		System.out.println("Enter a username for your new account.");
		user = scan.nextLine();
	}while(user.isEmpty() || usernames.contains(user));
	while(pass1.isEmpty()) {
		System.out.println("Enter a password for your account.");
		pass1 = scan.nextLine();
	}
	while(pass2.isEmpty()) {
		System.out.println("Verify the password you entered previously.");
		pass2 = scan.nextLine();
	}
	
	AccountType accountType = null;
	UserType userType = null;
	if(pass1.compareTo(pass2) == 0) {
		try {
			accountType = AccountType.valueOf("Client");
			try {
				userType = UserType.valueOf("Client");
				client = clientService.createNewClient(user, pass1, accountType, userType);
			}catch(IllegalArgumentException e) {
				client = newClientBuilder();
			}
			
		}catch(IllegalArgumentException e) {
			client = newClientBuilder();
		}
	}else {
		System.out.println("Your passwords do not match. TRY AGAIN\n");
		client = newClientBuilder();
	}
	
	return client;
	
	
	}
	
	public Client login() {
		System.out.println("Enter your username: ");
		String username = scan.nextLine();
		System.out.println("Enter your password: ");
		String password = scan.nextLine();
		Client client = clientService.login(username, password);
		if(client != null)
			return client;
		else
			return null;
	}
	
	public void clientMenu(Client client) {
		System.out.println("\nWelcome back " + client.getName() + "\n");
		if(client.getAccount() != null && client.getAccounts().get(0).isActive()) {
			String response = "";
			while(response != "5") {
				System.out.println("What do you wish to do?\n"
				+ "1.View Account Info\n"
				+ "2.Withdraw\n"
				+ "3.Deposit\n"
				+ "4.Transfer\n"
				+ "5.Log out");
				response = scan.nextLine();
				switch(response) {
					case "1":
						System.out.println(clientService.login(client.getName(), client.getPassword()));
						break;
					case "2":
						client = withdraw(clientService.login(client.getName(), client.getPassword()));
						break;
					case "3":
						client = deposit(clientService.login(client.getName(), client.getPassword()));
						break;
					case "4":
						client = transfer(clientService.login(client.getName(), client.getPassword()));
						break;
					case "5":
						return;
					default:
						System.out.println("Not a valid option. Try Again.\n");
						clientMenu(client);
				}
			}
			
			
		}
		else if(client.getAccount() != null && !client.getAccounts().get(0).isActive()){
			String response = "";
			while(response != "2") {
				System.out.println("Your account approval is pending. This might take up to 7 bussiness days. \n");
				System.out.println("What do you wish to do?\n"
					+ "1.Refresh\n"
					+ "2.Log Out");
				response = scan.nextLine();
				switch(response) {
				case "1":
					client = clientService.login(client.getName(), client.getPassword());
					clientMenu(client);
					return;
				case "2":
					return;
				default:
					System.out.println("Not a valid option. Try Again\n");
				}
			}
		
		}
		else if(client.getAccount() == null){
			String response = "";
			while(response != "3") {
				System.out.println("Your account has been canceled. \n");
				System.out.println("What do you wish to do?\n"
					+ "1.Refresh\n"
					+ "2.Apply for new Account\n"
					+ "3.Log Out");
				response = scan.nextLine();
				switch(response) {
					case "1":
						client = clientService.login(client.getName(), client.getPassword());
						clientMenu(client);
						return;
					case "2":
						if(accountService.accountApply(client.getName())) {
							System.out.println("You've applied successfully for an account.");
							client = clientService.login(client.getName(), client.getPassword());
							clientMenu(client);
						}
						else {
							System.out.println("An error occured during your application. Try Again");
							client = clientService.login(client.getName(), client.getPassword());
							clientMenu(client);
						}
						return;
					case "3":
						return;
					default:
						System.out.println("Not a valid option. Try Again\n");
				}
			}
		}
	}
	
	
	private Client transfer(Client client) {
		System.out.println("How much money would you like to transfer? \n");
		Double transfer;
		while(!scan.hasNextDouble()) {
			scan.next();
			System.out.println("You must enter a valid amount. ");
		}
		transfer = scan.nextDouble();
		scan.nextLine();
		ArrayList<Account> accounts= client.getAccounts();
		if(accounts.get(0).getCheckingsBalance() < transfer) {
			System.out.println("Insuficient funds");
			System.out.println("Current Balance: $" + df.format(accounts.get(0).getCheckingsBalance()) + "\n");
			return client;
		}
		else if(transfer < 0) {
			System.out.println("Must enter a positive amount to withdraw.");
			System.out.println("Current Balance: $" + df.format(accounts.get(0).getCheckingsBalance()) + "\n");
			return client;
		}
		else {
			//transfer
			System.out.println("Which user would you like to transfer money to? \n");
			String receiver = scan.nextLine();
			ArrayList<String> users = clientService.getUsernames();
			
			if(users.contains(receiver)) {
				//Process transfer
				Double newBalance = accountService.transfer(client.getName(), receiver, transfer);
				System.out.println("You transfered " + receiver + " $" + df.format(transfer));
				System.out.println("Current Balance: $" + df.format(newBalance) + "\n");
				accounts.get(0).setCheckingsBalance(newBalance);
				client.setAccounts(accounts);
				return client;
			}else {
				System.out.println("The user you entered does not exists");
				return client;
			}
		}
	}

	private Client deposit(Client client) {
		System.out.println("How much would you like to deposit? \n");
		Double deposit;
		while(!scan.hasNextDouble()) {
			scan.next();
			System.out.println("You must enter a valid amount. ");
		}
		deposit = scan.nextDouble();
		scan.nextLine();
		ArrayList<Account> accounts= client.getAccounts();
		if(deposit <= 0 ) {
			System.out.println("Nothing was deposited");
			System.out.println("Current Balance: $" + df.format(accounts.get(0).getCheckingsBalance()) + "\n");
			return client;
		}
		else if(deposit < 0) {
			System.out.println("Must enter a positive amount to deposit.");
			System.out.println("Current Balance: $" + df.format(accounts.get(0).getCheckingsBalance()) + "\n");
			return client;
		}
		else {
			Double newBalance = accountService.deposit(accounts.get(0).getAccountNumber(), deposit);
			System.out.println("You deposited $" + df.format(deposit));
			System.out.println("Current Balance: $" + df.format(newBalance) + "\n");
			accounts.get(0).setCheckingsBalance(newBalance);
			client.setAccounts(accounts);
			return client;
		}
	}

	public Client withdraw(Client client) {
		System.out.println("How much would you like to withdraw? \n");
		Double withdrawal;
		while(!scan.hasNextDouble()) {
			scan.next();
			System.out.println("You must enter a valid amount. ");
		}
		withdrawal = scan.nextDouble();
		scan.nextLine();
		ArrayList<Account> accounts= client.getAccounts();
		if(accounts.get(0).getCheckingsBalance() < withdrawal) {
			System.out.println("Insuficient funds");
			System.out.println("Current Balance: $" + df.format(accounts.get(0).getCheckingsBalance()) + "\n");
			return client;
		}
		else if(withdrawal < 0) {
			System.out.println("Must enter a positive amount to withdraw.");
			System.out.println("Current Balance: $" + df.format(accounts.get(0).getCheckingsBalance()) + "\n");
			return client;
		}
		else {
			Double newBalance = accountService.withdraw(accounts.get(0).getAccountNumber(), withdrawal);
			System.out.println("You withdrew $" + df.format(withdrawal));
			System.out.println("Current Balance: $" + df.format(newBalance) + "\n");
			accounts.get(0).setCheckingsBalance(newBalance);
			client.setAccounts(accounts);
			return client;
		}
	}

}
