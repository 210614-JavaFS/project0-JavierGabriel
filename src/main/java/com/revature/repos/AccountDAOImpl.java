package com.revature.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.AccountType;
import com.revature.models.Client;
import com.revature.models.UserType;
import com.revature.utils.ConnectionUtil;

public class AccountDAOImpl implements AccountDAO {

	@Override
	public ArrayList<Account> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Account> findByUser(String username) {
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM accounts WHERE account_user= ? ;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, username);
			
			ResultSet result = statement.executeQuery();
			
			ArrayList<Account> list = new ArrayList<>();
			
			//ResultSets have a cursor similarly to Scanners or other I/O classes. 
			while(result.next()) {
				Account account = new Account();
				account.setAccountNumber(result.getString("account_number"));
				account.setCheckingsBalance(result.getDouble("balance"));
				account.setActive(result.getBoolean("active"));
				try {
					account.setAccountType(AccountType.valueOf(result.getString("account_type")));
				}catch(IllegalArgumentException e) {
					e.printStackTrace();
				}
				list.add(account);
			}
			
			return list;
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean updateAccount() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAccount(Account account, String username) {
		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = "INSERT INTO accounts (account_number, balance, active, account_type, account_user)"
					+ " VALUES (?,?,?,?,?);";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			int index = 0;
			statement.setString(++index, account.getAccountNumber());
			statement.setDouble(++index, account.getCheckingsBalance());
			statement.setBoolean(++index, account.isActive());
			statement.setString(++index, account.getAccountType().toString());
			statement.setString(++index, username);
			
			statement.execute();
			
			return true;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public ArrayList<String> getAccountNumbers() {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT account_number FROM accounts;";
			
			Statement statement = conn.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			ArrayList<String> list = new ArrayList<>();
			
			//ResultSets have a cursor similarly to Scanners or other I/O classes. 
			while(result.next()) {
				String number = new String(result.getString("account_number"));
				list.add(number);
			}
			
			return list;
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public double getBalance(String account) {
		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT balance FROM accounts WHERE account_number=? ;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, account);
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				return result.getDouble("balance");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public double withdraw(String account, double amount) {
		double balance = getBalance(account);
		try (Connection conn = ConnectionUtil.getConnection()){
			
			
				Double newBalance = balance - amount;
				
				String sql = "UPDATE accounts SET balance= ? WHERE account_number = ?;";
				
				PreparedStatement statement1 = conn.prepareStatement(sql);
				
				statement1.setDouble(1, newBalance);
				statement1.setString(2, account);
				statement1.execute();
				
				return newBalance;
				
			}catch(SQLException e) {
			e.printStackTrace();
		}
		return balance;
	}

	@Override
	public double deposit(String account, double amount) {
		double balance = getBalance(account);
		try (Connection conn = ConnectionUtil.getConnection()){

				Double newBalance = balance + amount;
				
				String sql = "UPDATE accounts SET balance= ? WHERE account_number = ?;";
				
				PreparedStatement statement1 = conn.prepareStatement(sql);
				
				statement1.setDouble(1, newBalance);
				statement1.setString(2, account);
				statement1.execute();
				
				return newBalance;
				
			}catch(SQLException e) {
			e.printStackTrace();
		}
		return balance;
	}
	
	@Override
	public double transfer(String sender, String receiver, double amount) {
		double newBalance = withdrawByUser(sender, amount);
		depositByUser(receiver, amount);
		return newBalance;
	}

	@Override
	public double depositByUser(String username, double amount) {
		Double balance = getBalanceByUser(username);
		try (Connection conn = ConnectionUtil.getConnection()){

				Double newBalance = balance + amount;
				
				String sql = "UPDATE accounts SET balance= ? WHERE account_user = ?;";
				
				PreparedStatement statement1 = conn.prepareStatement(sql);
				
				statement1.setDouble(1, newBalance);
				statement1.setString(2, username);
				statement1.execute();
				
				return newBalance;
				
			}catch(SQLException e) {
			e.printStackTrace();
		}
		return balance;
	}

	@Override
	public double withdrawByUser(String username, double amount) {
		double balance = getBalanceByUser(username);
		try (Connection conn = ConnectionUtil.getConnection()){
			
			
				Double newBalance = balance - amount;
				
				String sql = "UPDATE accounts SET balance= ? WHERE account_user = ?;";
				
				PreparedStatement statement1 = conn.prepareStatement(sql);
				
				statement1.setDouble(1, newBalance);
				statement1.setString(2, username);
				statement1.execute();
				
				return newBalance;
				
			}catch(SQLException e) {
			e.printStackTrace();
		}
		return balance;
	}

	@Override
	public double getBalanceByUser(String username) {
		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT balance FROM accounts WHERE account_user=? ;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, username);
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				return result.getDouble("balance");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public boolean activateAccount(String username) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "UPDATE accounts SET active = TRUE WHERE account_user= ? ;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, username);
			
			statement.execute();
			
			return true;
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean deactivateAccount(String username) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "UPDATE accounts SET active = FALSE WHERE account_user= ? ;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, username);
			
			statement.execute();
			
			return true;
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean deleteAccount(String account) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "DELETE FROM accounts WHERE account_number= ? ;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, account);
			
			statement.execute();
			
			return true;
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
