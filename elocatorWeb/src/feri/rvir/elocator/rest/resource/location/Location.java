package feri.rvir.elocator.rest.resource.location;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.appengine.api.datastore.Key;

import feri.rvir.elocator.rest.resource.user.User;

@Entity
public class Location implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;

	@OneToOne(cascade = CascadeType.ALL)
	private User user;
	
	private Date timestamp;
	private double latitude;
	private double longitude;

	public Location() {

	}

	public Location(User user, Date timestamp, double latitude,
			double longitude) {
		this.user = user;
		this.timestamp = timestamp;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
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
		return user.getUsername()+" "+timestamp+" "+latitude+" "+longitude;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
		
	}

}
