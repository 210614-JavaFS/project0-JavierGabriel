package com.revature.services;

import java.util.ArrayList;

import com.revature.models.Account;
import com.revature.models.AccountType;
import com.revature.models.Client;
import com.revature.models.UserType;
import com.revature.repos.ClientDAO;
import com.revature.repos.ClientDAOImpl;

public class ClientService {
	
	private static ClientDAO clientDao = new ClientDAOImpl();
	private static AccountService accountService = new AccountService();
	
	public Client createNewClient(String username, String password, AccountType accountType, UserType userType) {
		Account account = accountService.generateNewAccount(accountType);
		Client client = new Client(username, password, userType, account);
		if(clientDao.addClient(client)) {
			return client;
		}else {
			return null;
		}
			
		
	}
	
	public ArrayList<Client> getAllClients(){
		return clientDao.findAll();
	}
	
	public Client login(String username, String password) {
		Client client = clientDao.login(username , password);
		return client;
		
	}
	
	public ArrayList<String> getUsernames(){
		return clientDao.getAllClientUsernames();
	}
	
	public Client getClient(String username) {
		return clientDao.findClient(username);
	}
	
	public ArrayList<String> findAllUsernames(){
		return clientDao.findAllUsers();
	}
	
}
