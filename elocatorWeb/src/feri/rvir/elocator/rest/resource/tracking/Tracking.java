package feri.rvir.elocator.rest.resource.tracking;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.appengine.api.datastore.Key;

import feri.rvir.elocator.rest.resource.user.User;

@Entity
public class Tracking implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;
	
	@OneToOne(cascade = CascadeType.ALL)
	private User tracker;
	@OneToMany
	private ArrayList<User> userList;

	public Tracking() {

	}

	public Tracking(User tracker, ArrayList<User> userList) {
		this.tracker = tracker;
		this.userList = userList;
	}

	public User getTracker() {
		return tracker;
	}

	public void setTracker(User tracker) {
		this.tracker = tracker;
	}

	public ArrayList<User> getUserList() {
		return userList;
	}

	public void setUserList(ArrayList<User> userList) {
		this.userList = userList;
	}

	@Override
	public String toString() {
		String s=tracker.getUsername()+": ";
		for(User u:userList) {
			s+=u.getUsername()+" ";
		}
		return s;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
	
}
