package feri.rvir.elocator.rest.resource.location;

import java.io.Serializable;
import java.util.Date;

public class Location implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long key;
	
	private Long userKey;
	private Date timestamp;
	private double latitude;
	private double longitude;

	public Location() {
	}

	public Location(Long userKey, Date timestamp, double latitude,
			double longitude) {
		this.userKey = userKey;
		this.timestamp = timestamp;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Location(Long key, Long userKey, Date timestamp, double latitude,
			double longitude) {
		this.key = key;
		this.userKey = userKey;
		this.timestamp = timestamp;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Long getUserKey() {
		return userKey;
	}

	public void setUserKey(Long userKey) {
		this.userKey = userKey;
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

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
		
	}

}
