package feri.rvir.elocator.rest.resource.tracking;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

public interface TrackingResource {
	
	@Get
	public Tracking retrieve();
	
	@Put
	public void store(Tracking tracking);
	
	@Put
	public void store(String accountNameTracker, String accountNameBeingTracked);
	
	@Delete
	public void remove(String accountName);
	
	@Delete
	public void remove(String accountNameTracker, String accountNameBeingTracked);

}
