package com.revature.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import com.revature.models.AccountType;
import com.revature.models.Client;
import com.revature.models.User;
import com.revature.models.UserType;
import com.revature.services.ClientService;

public class MenuController {

	private static Scanner scan = new Scanner(System.in);
	private static EmployeeController employeeController = new EmployeeController();
	private static ClientController clientController = new ClientController();
	 
	public static void menu() {	
		String userType = "";		
		while(!userType.equalsIgnoreCase("quit")) {
			System.out.println("Welcome to Phoenix Banking System. \n");
			System.out.println("What type of user are you? \n"
				+ "Client \n"
				+ "Employee \n"
				+ "Quit \n");
			userType = scan.nextLine();
			switch(userType.toLowerCase()) {
				case "client":
					Client client = clientController.getUser();
					if(client != null) {
						clientController.clientMenu(client);
					}
					break;
				case "employee":
					System.out.println("Welcome back employee. What do you want to do? \n");
					employeeController.employeeMenu();
					menu();
					break;
				case "quit":
					return;
				default:
					break;
			}
		}
			
	}
	
	
	
	
}
