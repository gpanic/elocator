package feri.rvir.elocator.rest.resource.user;

import org.restlet.resource.ServerResource;

import feri.rvir.elocator.dao.UserDao;

public class UserServerResource extends ServerResource implements UserResource {

	UserDao userDao = new UserDao();
	
	@Override
	public User retrieve() {
		System.out.println("RETRIEVE UserServerResource");
		String authToken=(String)getRequest().getAttributes().get("username");
		System.out.println(authToken);
		return new User("usernameExample","passwordExample");
	}

	@Override
	public void store(User user) {
		System.out.println("STORE UserServerResource");
	}

	@Override
	public void remove(int id) {
		System.out.println("REMOVE UserServerResource");
	}

	@Override
	public UserErrorMessage accept(User user) {
		String operation=(String)getRequest().getAttributes().get("operation");
		return null;
	}
}
