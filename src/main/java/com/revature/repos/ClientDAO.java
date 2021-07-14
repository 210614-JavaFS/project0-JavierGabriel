package com.revature.repos;

import java.util.ArrayList;

import com.revature.models.Client;
import com.revature.models.User;

public interface ClientDAO {

	public ArrayList<Client> findAll();
	public Client findClient(String username);
	public boolean addClient(Client client);
	public Client login(String username, String password);
	public ArrayList<String> getAllClientUsernames();
	public ArrayList<String> findAllUsers();
	
}
