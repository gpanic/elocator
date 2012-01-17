package feri.rvir.elocator.rest.resource.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long key;
	private String username;
	private String password;

//	@ManyToMany
//	@JoinTable(name="USER_FAN",
//	joinColumns=@JoinColumn(name="parent_id", referencedColumnName="USER_KEY"),
//	inverseJoinColumns=@JoinColumn(name="child_id",referencedColumnName="USER_KEY"))
//  not supported by GAE
	
	public User() {
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
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
	
	@Override
	public String toString() {
		return username;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public Long getKey() {
		return key;
	}

}
