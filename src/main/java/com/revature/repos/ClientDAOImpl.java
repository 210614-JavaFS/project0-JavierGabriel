package com.revature.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.Client;
import com.revature.models.User;
import com.revature.models.UserType;
import com.revature.utils.ConnectionUtil;

public class ClientDAOImpl implements ClientDAO {
	
	private AccountDAO accountDao = new AccountDAOImpl();

	@Override
	public ArrayList<Client> findAll() {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM users WHERE user_type='Client' ;";
			
			Statement statement = conn.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			ArrayList<Client> list = new ArrayList<>();
			
			//ResultSets have a cursor similarly to Scanners or other I/O classes. 
			while(result.next()) {
				Client client = new Client();
				client.setName(result.getString("username"));
				client.setPassword(result.getString("user_pass"));
				try {
					client.setUserType(UserType.valueOf(result.getString("user_type")));
				}catch(IllegalArgumentException e) {
					e.printStackTrace();
				}
				//TODO: Get accounts lists using AccountDAO
				client.setAccounts(accountDao.findByUser(client.getName()));
				list.add(client);
			}
			
			return list;
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Client findClient(String username) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM users WHERE username = ? AND user_type='Client' ;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, username);
			
			ResultSet result = statement.executeQuery();
			
			Client client = new Client();
			
			//ResultSets have a cursor similarly to Scanners or other I/O classes. 
			while(result.next()) {
				client.setName(result.getString("username"));
				client.setPassword(result.getString("user_pass"));
				try {
					client.setUserType(UserType.valueOf(result.getString("user_type")));
				}catch(IllegalArgumentException e) {
					e.printStackTrace();
				}
				//TODO: Get accounts lists using AccountDAO
				client.setAccounts(accountDao.findByUser(client.getName()));
			}
			if(client.getName() == null)
				client = null;
			return client;
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean addClient(Client client) {
		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = "INSERT INTO users (username, user_pass, user_type)"
					+ " VALUES (?,?,?);";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			int index = 0;
			statement.setString(++index, client.getName());
			statement.setString(++index, client.getPassword());
			statement.setString(++index, client.getUserType().toString());
			statement.execute();
			for(Account account: client.getAccounts()) {
				accountDao.addAccount(account, client.getName());
			}
			
			
			return true;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Client login(String username, String password) {
		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM users WHERE username=? AND user_pass=? ;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			int index = 0;
			statement.setString(++index, username);
			statement.setString(++index, password);
			ResultSet result = statement.executeQuery();
			Client client = new Client();
			while(result.next() && result.getString("username") != "null") {
				client.setName(result.getString("username"));
				client.setPassword(result.getString("user_pass"));
				try {
					client.setUserType(UserType.valueOf(result.getString("user_type")));
				}catch(IllegalArgumentException e) {
					e.printStackTrace();
				}	
			}
			//Get accounts list using AccountDAO
			client.setAccounts(accountDao.findByUser(client.getName()));
			
			if(client.getName() != null) {
				return client;
			}
			else {
				client = null;
				return client;
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<String> getAllClientUsernames() {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT username FROM users WHERE user_type = 'Client';";
			
			Statement statement = conn.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			ArrayList<String> list = new ArrayList<>();
			
			//ResultSets have a cursor similarly to Scanners or other I/O classes. 
			while(result.next()) {
				String username = new String(result.getString("username"));
				list.add(username);
			}
			
			return list;
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<String> findAllUsers() {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT username FROM users;";
			
			Statement statement = conn.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			ArrayList<String> list = new ArrayList<>();
			
			//ResultSets have a cursor similarly to Scanners or other I/O classes. 
			while(result.next()) {
				String username = new String(result.getString("username"));
				list.add(username);
			}
			
			return list;
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

}
