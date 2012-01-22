package feri.rvir.elocator.rest.resource.tracking;

import java.util.ArrayList;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

import feri.rvir.elocator.rest.resource.RestletErrorMessage;

public interface TrackingResource {
	
	@Get
	public ArrayList<Tracking> retrieve();
	
	@Put
	public void store(Tracking tracking);

	@Delete
	void remove(String usernameTracker, String usernameBeingTracked);
	
	@Post
	public RestletErrorMessage accept(String tracker, String child);

}
