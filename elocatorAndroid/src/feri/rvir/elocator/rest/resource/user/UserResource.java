package feri.rvir.elocator.rest.resource.user;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

public interface UserResource {
	
	@Get
	public User retrieve();
	
	@Put
	public void store(User user);
	
	@Delete
	public void remove(String username);
	
	@Post
	public UserErrorMessage accept(User user);
}
