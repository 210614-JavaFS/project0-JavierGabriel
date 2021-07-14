package com.revature.repos;

import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;

public interface AccountDAO {
	public ArrayList<Account> findAll();
	public ArrayList<Account> findByUser(String username);
	public boolean updateAccount();
	public boolean addAccount(Account account, String username);
	public ArrayList<String> getAccountNumbers();
	public double getBalance(String account);
	public double getBalanceByUser(String username);
	public double withdraw(String account, double amount);
	public double deposit(String account, double amount);
	public double transfer(String sender, String receiver, double amount);
	public double depositByUser(String account, double amount);
	public double withdrawByUser(String account, double amount);
	public boolean activateAccount(String username);
	public boolean deactivateAccount(String username);
	public boolean deleteAccount(String account);
}
