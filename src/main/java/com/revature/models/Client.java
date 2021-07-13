package com.revature.models;

import java.util.ArrayList;
import java.util.Objects;

public class Client extends User{
	
	private ArrayList<Account> accounts = new ArrayList<Account>();

	public Client(String name, String password, UserType userType, Account account) {
		super(name, password, userType);
		this.accounts.add(account);
	}
	
	
	
	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public ArrayList<Account> getAccounts() {
		return accounts;
	}


	public void setAccounts(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}
	
	public void addAccount(Account account) {
		this.accounts.add(account);
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((accounts == null) ? 0 : accounts.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (accounts == null) {
			if (other.accounts != null)
				return false;
		} else if (!accounts.equals(other.accounts))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "\n username: " + this.name +"\n password: " + password + "\n userType: " + userType + "\n " + this.name + " Accounts: " + accounts + "\n";
	}

	

	
	
	
	
	
	



	
	

}
