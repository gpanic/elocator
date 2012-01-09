package feri.rvir.elocator.rest.resource.tracking;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import feri.rvir.elocator.rest.resource.user.User;

@XmlRootElement
public class Tracking implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private User tracker;
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

	@XmlElement
	public void setTracker(User tracker) {
		this.tracker = tracker;
	}

	public ArrayList<User> getUserList() {
		return userList;
	}

	@XmlElement(name="beingTracked")
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

}
