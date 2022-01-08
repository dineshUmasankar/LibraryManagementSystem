package org.psu.dUmasankar.LMSModel;

public class StaffModel {
	private int account_ID = -1;
	private String username = null;
	
	public StaffModel() {
	}
	
	public StaffModel(int account_ID, String username)
	{
		this.setAccount_ID(account_ID);
		this.setUsername(username);
	}

	public int getAccount_ID() {
		return account_ID;
	}

	public void setAccount_ID(int account_ID) {
		this.account_ID = account_ID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
