package feri.rvir.elocator.rest.resource.location;

import java.io.Serializable;
import java.sql.Timestamp;

import feri.rvir.elocator.rest.resource.user.User;

public class Location implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private User user;
	private Timestamp timestamp;
	private double latitude;
	private double longitude;
	
	public Location() {
		
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	@Override
	public String toString() {
		return user.getAccountName()+" "+timestamp+" "+latitude+" "+longitude;
	}

}
