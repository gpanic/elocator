package feri.rvir.elocator.rest.resource.location;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

public interface LocationResource {
	
	@Get
	public Location retrieve();
	
	@Put
	public void store(Location location);
	
	@Delete
	public void remove(String username, Date timestamp);
	
	@Post
	public List<Location> accept(String username);

}
