package feri.rvir.elocator.rest.resource.location;

import java.io.Serializable;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import feri.rvir.elocator.rest.resource.user.User;

@XmlRootElement
public class Location implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private User user;
	private Calendar timestamp;
	private double latitude;
	private double longitude;
	
	public Location() {
		
	}
	
	public Location(User user, Calendar timestamp, double latitude, double longitude) {
		this.user = user;
		this.timestamp = timestamp;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public User getUser() {
		return user;
	}

	@XmlElement
	public void setUser(User user) {
		this.user = user;
	}

	public Calendar getTimestamp() {
		return timestamp;
	}

	@XmlElement
	public void setTimestamp(Calendar timestamp) {
		this.timestamp = timestamp;
	}

	public double getLatitude() {
		return latitude;
	}

	@XmlElement
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	@XmlElement
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return user.getAccountName()+" "+timestamp+" "+latitude+" "+longitude;
	}

}
