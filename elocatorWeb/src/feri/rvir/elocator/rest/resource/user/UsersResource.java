package feri.rvir.elocator.rest.resource.user;

import java.util.List;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

public interface UsersResource {
	
	@Get
	public List<User> retrieve();
	
	@Delete
	public void remove();
	
	@Post
	public List<User> accept(User u);

}
