package feri.rvir.elocator.rest.resource.user;

import org.restlet.resource.ServerResource;

public class UserServerResource extends ServerResource implements UserResource {
	
	@Override
	public User retrieve() {
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
		if(operation.equals("register")) {
			System.out.println("REGISTER");
			return new UserErrorMessage(true, "Registration successful.");
		} else if (operation.equals("signin")) {
			System.out.println("SIGNIN");
			return new UserErrorMessage(true, "Signed in as: "+user.getUsername()+".");
		}
		return null;
	}
}
