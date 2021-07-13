package com.revature.services;

import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.AccountType;
import com.revature.repos.AccountDAO;
import com.revature.repos.AccountDAOImpl;

public class AccountService {
	private static AccountDAO accountDao = new AccountDAOImpl();
	
	public List<Account> getAllAccounts() {
		return accountDao.findAll();
	}
	
	public List<Account> getUserAccounts(String accountNumber){
		return accountDao.findByUser(accountNumber);
	}
	
	public boolean addAccount(Account account, String username) {
		return accountDao.addAccount(account, username);
	}
	
	public Account generateNewAccount(AccountType accountType) {
		Account account = new Account();
		//TODO: Generate random number and check it not already created 
		account.setAccountNumber(generateAccountNumber());
		account.setCheckingsBalance(0);
		account.setActive(false);
		account.setAccountType(accountType);
		
		return account;

	}
	
	public String generateAccountNumber() {
		int n = 10;
		
		 // chose a Character random from this String
        String AlphaNumericString = "0123456789";
  
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);
  
        for (int i = 0; i < n; i++) {
  
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                = (int)(AlphaNumericString.length()
                        * Math.random());
  
            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                          .charAt(index));
        }
        ArrayList<String> allAccounts = getAccountNumbers();
        if(!allAccounts.contains(sb.toString())) {
        	return sb.toString();
        }else {
        	return generateAccountNumber();
        }
        
	}
	
	public ArrayList<String> getAccountNumbers(){
		return accountDao.getAccountNumbers();
	}
	
	public Double withdraw(String account, Double amount) {
		return accountDao.withdraw(account, amount);
	}
	//Returns new balance
	public Double deposit(String account, Double amount) {
		return accountDao.deposit(account, amount);
	}
	
	public Double transfer(String sender, String receiver, double amount) {
		return accountDao.transfer(sender, receiver , amount);
	}
}
