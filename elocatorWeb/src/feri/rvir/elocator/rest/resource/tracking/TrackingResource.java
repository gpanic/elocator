package feri.rvir.elocator.rest.resource.tracking;

import java.util.List;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

public interface TrackingResource {
	
	@Get
	public List<Tracking> retrieve();
	
	@Put
	public void store(Tracking tracking);

	@Delete
	void remove(String usernameTracker, String usernameBeingTracked);

}
