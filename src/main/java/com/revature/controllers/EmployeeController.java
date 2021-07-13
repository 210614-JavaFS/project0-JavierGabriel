package com.revature.controllers;

import java.util.ArrayList;
import java.util.Scanner;

import com.revature.models.Client;
import com.revature.services.ClientService;

public class EmployeeController {
	
	private static Scanner scan = new Scanner(System.in);
	private ClientService clientService = new ClientService();

	public void employeeMenu() {
		System.out.println("Employee Menu:"
				+ "1. View all users account information \n"
				+ "2. Search for a specific user \n"
				+ "3. Approve a customer account \n"
				+ "4. Log out \n");
		String response = scan.nextLine();
		
		while(response != "4") {
			switch(response) {
			case "1":
				ArrayList<Client> clients =clientService.getAllClients();
				System.out.println(clients);
				employeeMenu();
				break;
			case "4":
				//TODO: Log user out
				return;
			default:
				System.out.println("That is not a valid option. Please try again..\n");
				employeeMenu();
				break;
			}
		}	
	}
}
