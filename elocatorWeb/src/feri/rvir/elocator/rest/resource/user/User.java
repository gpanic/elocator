package feri.rvir.elocator.rest.resource.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.appengine.api.datastore.Key;

@XmlRootElement
@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;
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

	@XmlElement
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getAccountName() {
		return accountName;
	}

	@XmlElement
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountType() {
		return accountType;
	}

	@XmlElement
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	@Override
	public String toString() {
		return accountName + " " + accountType + " " + authToken;
	}

}
