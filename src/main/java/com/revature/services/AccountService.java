package com.revature.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.Account;
import com.revature.models.AccountType;
import com.revature.repos.AccountDAO;
import com.revature.repos.AccountDAOImpl;

public class AccountService {
	private static Logger log = LoggerFactory.getLogger(AccountService.class);
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
	
	public boolean accountApply(String username) {
		AccountType accountType = null;
		try {
			accountType = AccountType.valueOf("Client");
		}catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
		Account account = generateNewAccount(accountType);
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
	
	public double withdraw(String account, Double amount) {
		log.info("$" + amount +" was withdrawn from account #"+ account);
		return accountDao.withdraw(account, amount);
	}
	//Returns new balance
	public double deposit(String account, double amount) {
		log.info("$" + amount +" was deposited into account # "+ account);
		return accountDao.deposit(account, amount);
	}
	
	public double transfer(String sender, String receiver, double amount) {
		log.info(sender +" transfered $"+amount+" to "+ receiver + ".");
		return accountDao.transfer(sender, receiver , amount);
	}
	
	public boolean activateAccount(String username) {
		return accountDao.activateAccount(username);
	}
	
	public boolean deactivateAccount(String username) {
		return accountDao.deactivateAccount(username);
	}
	
	public boolean deleteAccount(String account) {
		return accountDao.deleteAccount(account);
	}
}
