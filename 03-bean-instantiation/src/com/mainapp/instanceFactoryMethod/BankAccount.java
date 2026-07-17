package com.mainapp.instanceFactoryMethod;

public class BankAccount {

	private int accountNumber;

	private String bankName;

	public BankAccount() {
		super();
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Override
	public String toString() {
		return "BankAccount [accountNumber=" + accountNumber + ", bankName=" + bankName + "]";
	}

}
