package feri.rvir.elocator.rest.resource.tracking;

import java.io.Serializable;
import java.util.ArrayList;

import feri.rvir.elocator.rest.resource.user.User;

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
		String s=tracker.getAccountName()+": ";
		for(User u:userList) {
			s+=u.getAccountName()+" ";
		}
		return s;
	}

}
