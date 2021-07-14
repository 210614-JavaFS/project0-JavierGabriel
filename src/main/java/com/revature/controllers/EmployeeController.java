package com.revature.controllers;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

import com.revature.models.Account;
import com.revature.models.Client;
import com.revature.models.Employee;
import com.revature.services.AccountService;
import com.revature.services.ClientService;
import com.revature.services.EmployeeService;

public class EmployeeController {
	
	private static Scanner scan = new Scanner(System.in);
	private ClientService clientService = new ClientService();
	private AccountService accountService = new AccountService();
	private EmployeeService employeeService = new EmployeeService();
	private DecimalFormat df = new DecimalFormat("#,###.00");

	public void employeeMenu(Employee employee) {
		String response = "";
		System.out.println("Welcome back "+ employee.getName() +". What do you want to do?");
		while(response != "4") {
			System.out.println("\nEmployee Menu:\n"
					+ "1.View all users account information \n"
					+ "2.Search for a specific user \n"
					+ "3.Approve/Deny a customer account application\n"
					+ "4.Log out \n");
			response = scan.nextLine();
			switch(response) {
			case "1":
				ArrayList<Client> clients = clientService.getAllClients();
				System.out.println(clients);
				break;
			case "2":
				getClient();
				break;
			case "3":
				approveClient();
				break;
			case "4":
				return;
			default:
				System.out.println("That is not a valid option. Please try again..\n");
				break;
			}
		}	
	}
	
	public void AdminMenu(Employee employee) {
		String response = "";
		System.out.println("\nWelcome back " + employee.getName() + ". What do you want to do?");
		while(response != "4") {
			System.out.println("\nAdmin Menu:\n"
					+ "1.View all users account information \n"
					+ "2.Search and edit account for a specific user\n"
					+ "3.Approve/Deny a customer account application\n"
					+ "4.Log out \n");
			response = scan.nextLine();
			switch(response) {
			case "1":
				ArrayList<Client> clients = clientService.getAllClients();
				System.out.println(clients);
				break;
			case "2":
				getClientAdmin();
				break;
			case "3":
				approveClient();
				break;
			case "4":
				return;
			default:
				System.out.println("That is not a valid option. Please try again..\n");
				break;
			}
		}	
	}
	
	public void getClientAdmin() {
		System.out.println("Enter client username: ");
		String username = scan.nextLine();
		Client client = clientService.getClient(username);
		if(client != null) {
			System.out.println(client);
			String selectedOption = "";
			System.out.println("Do you want to operate in users account? yes/no");
			selectedOption = scan.nextLine();
			if(selectedOption.toLowerCase().equals("yes")) {
				client = editClient(client);
			}
		}
		else {
			System.out.println("Client not found");
		}
		
		System.out.println("Do you wish to search another client? yes/no");
		String response = scan.nextLine();
		if(response.toLowerCase().equals("yes")){
			getClientAdmin();
		}
		else {
			return;
		}
		
	}
	
	public Client editClient(Client client) {
		String response = "";
		while(!response.equals("7")) {
			String accStatus = (client.getAccount() != null && client.getAccount().isActive()) ? "Deactivate": "Activate";
			System.out.println("\nClient editing options:\n"
				+ "1."+ accStatus  + " Account\n"
				+ "2.Withdraw\n"
				+ "3.Deposit\n"
				+ "4.Transfer\n"
				+ "5.Cancel Account\n"
				+ "6.View Info\n"
				+ "7.Exit Menu");
			response = scan.nextLine();
			switch(response) {
				case "1":
					if(client.getAccount().isActive()) {
						accountService.deactivateAccount(client.getName());
						client.getAccount().setActive(false);
					}
					else {
						accountService.activateAccount(client.getName());
						client.getAccount().setActive(true);
					}
					break;
				case "2":
					client = adminWithdraw(client);
					break;
				case "3":
					client = adminDeposit(client);
					break;
				case "4":
					client = AdminTransfer(client);
					break;
				case "5":
					client = AdminCancel(client);
				case "6":
					System.out.println(client);
					break;
				case "7":
					break;
				default:
					System.out.println("Not a valid option. Try Again\n");
					
			}
		}
		
		return null;
	}


	public void approveClient() {
		ArrayList<Client> clients = clientService.getAllClients();
		ArrayList<String> usernames = new ArrayList<>();
		clients.removeIf(client -> client.getAccount() == null || client.getAccount().isActive() == true);
		if(clients.isEmpty()) {
			System.out.println("No clients currently waiting for approval.");
		}else{
			for(Client client: clients) {
				usernames.add(client.getName());
			}
			System.out.println("Clients waiting for account approval:\n" + clients);
			System.out.println("Enter the username of account you wish to approve:");
			String username = scan.nextLine();
			if(!usernames.contains(username)) {
				System.out.println("That user does not exist or is not in the list. Try again");
				approveClient();
			}
			else if(usernames.contains(username)) {
				String decision = "";
				Client client = clientService.getClient(username);
				while(!decision.toLowerCase().equals("approve") && !decision.toLowerCase().equals("deny")) {
					System.out.println("Enter your decision: (approve/deny)");
					decision = scan.nextLine();
					if(decision.toLowerCase().equals("approve") && accountService.activateAccount(username)) {
						System.out.println(username + "'s account application has been denied.");
					}
					else if(decision.toLowerCase().equals("deny") && accountService.deleteAccount(client.getAccount().getAccountNumber())) {
						System.out.println(username + "'s account application has been denied.");
					}
				}
				
				
				
				System.out.println("Do you wish to activate/deny another account? yes/no");
				String response = scan.nextLine();
				if(response.equals("yes")) {
					approveClient();
				}
				else {
					return;
				}
			}else {
				System.out.println("Could not activate that user.");
				System.out.println("Do you wish to activate another account? yes/no");
				String response = scan.nextLine();
				if(response.toLowerCase().equals("yes")) {
					approveClient();
				}
				else {
					return;
				}
			}
			
		}
	}
	
	public Employee login() {
		System.out.println("Enter your username: ");
		String username = scan.nextLine();
		System.out.println("Enter your password: ");
		String password = scan.nextLine();
		Employee employee = employeeService.login(username, password);
		if(employee != null)
			return employee;
		else
			return null;
	}
	
	public void getClient() {
		System.out.println("Enter client username: ");
		String username = scan.nextLine();
		Client client = clientService.getClient(username);
		if(client != null) {
			System.out.println(client);
		}
		else {
			System.out.println("Client not found");
		}
		
		System.out.println("Do you wish to search another client? yes/no");
		String response = scan.nextLine();
		if(response.toLowerCase().equals("yes")){
			getClient();
		}
		else {
			return;
		}
	}
	
	public Client adminWithdraw(Client client) {
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
	
	private Client adminDeposit(Client client) {
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
			System.out.println("Current Client Balance: $" + df.format(accounts.get(0).getCheckingsBalance()) + "\n");
			return client;
		}
		else if(deposit < 0) {
			System.out.println("Must enter a positive amount to deposit.");
			System.out.println("Current Client Balance: $" + df.format(accounts.get(0).getCheckingsBalance()) + "\n");
			return client;
		}
		else {
			Double newBalance = accountService.deposit(accounts.get(0).getAccountNumber(), deposit);
			System.out.println("You deposited $" + df.format(deposit));
			System.out.println("Current Client Balance: $" + df.format(newBalance) + "\n");
			accounts.get(0).setCheckingsBalance(newBalance);
			client.setAccounts(accounts);
			return client;
		}
	}
	
	private Client AdminTransfer(Client client) {
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
			System.out.println("Current Balance: $" + accounts.get(0).getCheckingsBalance() + "\n");
			return client;
		}
		else if(transfer < 0) {
			System.out.println("Must enter a positive amount to withdraw.");
			System.out.println("Current Balance: $" + accounts.get(0).getCheckingsBalance() + "\n");
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
	
	
	private Client AdminCancel(Client client) {
		if(accountService.deleteAccount(client.getAccount().getAccountNumber())) {
			System.out.println("Account canceled successfully");
			client = clientService.getClient(client.getName());
			return client;
		}else {
			System.out.println("Error ocurred while trying to delete account. Try Again.");
		}
		return null;
	}
	
	
}
