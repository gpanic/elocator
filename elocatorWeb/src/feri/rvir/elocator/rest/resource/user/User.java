package feri.rvir.elocator.rest.resource.user;

import java.io.Serializable;

public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String authToken;
	private String accountName;
	private String accountType;
	
	public User() {
		
	}

	public User(String authToken, String accountName, String accountType) {
		this.authToken = authToken;
		this.accountName = accountName;
		this.accountType = accountType;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	@Override
	public String toString() {
		return accountName+" "+accountType+" "+authToken;
	}
	
}
