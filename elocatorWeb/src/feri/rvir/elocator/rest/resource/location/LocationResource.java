package feri.rvir.elocator.rest.resource.location;

import java.sql.Timestamp;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

public interface LocationResource {
	
	@Get
	public Location retrieve();
	
	@Put
	public void store(Location location);
	
	@Delete
	public void remove(String accountName, Timestamp timestamp);

}
