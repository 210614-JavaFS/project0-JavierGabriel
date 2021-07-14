package com.revature.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import com.revature.models.AccountType;
import com.revature.models.Client;
import com.revature.models.Employee;
import com.revature.models.User;
import com.revature.models.UserType;
import com.revature.services.ClientService;

public class MenuController {

	private static Scanner scan = new Scanner(System.in);
	private static EmployeeController employeeController = new EmployeeController();
	private static ClientController clientController = new ClientController();
	 
	public static void menu() {	
		String userType = "";		
		while(!userType.equalsIgnoreCase("3")) {
			System.out.println("Welcome to Phoenix Banking System. \n");
			System.out.println("What type of user are you? \n"
				+ "1.Client \n"
				+ "2.Employee \n"
				+ "3.Quit");
			userType = scan.nextLine();
			switch(userType.toLowerCase()) {
				case "1":
					Client client = clientController.getUser();
					if(client != null) {
						clientController.clientMenu(client);
					}
					break;
				case "2":
					Employee employee = employeeController.login();
					if(employee != null && employee.getUserType().toString().equals("Employee")) {
						employeeController.employeeMenu(employee);
					}
					else if(employee != null && employee.getUserType().toString().equals("Administrator")) {
						employeeController.AdminMenu(employee);
					}
					else {
						System.out.println("Invalid Credentials");
					}
					break;
				case "3":
					return;
				default:
					System.out.println("Not a valid option. Try again\n");
					break;
			}
		}
			
	}
	
	
	
	
}
