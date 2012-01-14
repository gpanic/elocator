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
	private Key trackerKey;
	private Key child;
	
	
	
	public Tracking(Key trackerKey, Key child) {
		super();
		this.trackerKey = trackerKey;
		this.child = child;
	}
	
	public Tracking() {
		
	}
	
	public Key getTrackerKey() {
		return trackerKey;
	}
	public void setTrackerKey(Key trackerKey) {
		this.trackerKey = trackerKey;
	}
	public Key getChild() {
		return child;
	}
	public void setChild(Key child) {
		this.child = child;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public Key getKey() {
		return key;
	}
	
}
