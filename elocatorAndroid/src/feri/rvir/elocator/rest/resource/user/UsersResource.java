package feri.rvir.elocator.rest.resource.user;

import java.util.ArrayList;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

public interface UsersResource {
	
	@Get
	public ArrayList<User> retrieve();
	
	@Delete
	public void remove();
	
	@Post
	public ArrayList<User> accept(String username);

}
