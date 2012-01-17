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
    private Long key;
	private Long trackerKey;
	private Long child;
	
	
	
	public Tracking(Long trackerKey, Long child) {
		super();
		this.trackerKey = trackerKey;
		this.child = child;
	}
	
	public Tracking() {
		
	}
	
	public Long getTrackerKey() {
		return trackerKey;
	}
	public void setTrackerKey(Long trackerKey) {
		this.trackerKey = trackerKey;
	}
	public Long getChild() {
		return child;
	}
	public void setChild(Long child) {
		this.child = child;
	}
	public void setKey(Long key) {
		this.key = key;
	}
	public Long getKey() {
		return key;
	}
	
}
