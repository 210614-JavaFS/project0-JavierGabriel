package com.revature.models;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Account {

	private String accountNumber;
	private double checkingsBalance;
	private boolean active;
	private AccountType accountType;
	private DecimalFormat df = new DecimalFormat("#,##0.00");
	private static Logger log = LoggerFactory.getLogger(Account.class);
	
	public Account(String accountNumber, double checkingsBalance, boolean active) {
		super();
		this.accountNumber = accountNumber;
		if(checkingsBalance >= 0) {
			this.checkingsBalance = checkingsBalance;
		}else {
			log.warn("An account with a negative value was tried to be created at constructor");
			this.checkingsBalance = 0;
		}
		this.active = active;
	}

	public Account() {
		super();
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getCheckingsBalance() {
		return checkingsBalance;
	}

	public void setCheckingsBalance(double checkingsBalance) {
		this.checkingsBalance = checkingsBalance;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result + ((accountType == null) ? 0 : accountType.hashCode());
		result = prime * result + (active ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(checkingsBalance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (accountType != other.accountType)
			return false;
		if (active != other.active)
			return false;
		if (Double.doubleToLongBits(checkingsBalance) != Double.doubleToLongBits(other.checkingsBalance))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "\n accountNumber:" + accountNumber + "\n checkingsBalance: $" + df.format(checkingsBalance) + "\n active:"
				+ active + "\n accountType:" + accountType;
	}
	
	
	
	
	
	
	
	
}
