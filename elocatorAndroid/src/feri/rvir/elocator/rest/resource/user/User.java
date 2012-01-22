package feri.rvir.elocator.rest.resource.user;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long key;
	private String username;
	private String password;

	public User() {
		
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public User(Long key, String username, String password) {
		this.key = key;
		this.username = username;
		this.password = password;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
