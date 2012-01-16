package feri.rvir.elocator.rest.resource.user;

import org.restlet.resource.ServerResource;

import com.google.appengine.api.datastore.Key;

import feri.rvir.elocator.dao.UserDao;

public class UserServerResource extends ServerResource implements UserResource {
	
	UserDao userdao = new UserDao();
	
	@Override
	public User retrieve() {
		String username=(String)getRequest().getAttributes().get("username");
		User u = userdao.getUser(username);
		return u;
	}

	@Override
	public void store(User user) {
		//User u = userdao.
	}

	@Override
	public void remove(String username) {
		User u = userdao.getUser(username);
		
		if (u != null) {
			userdao.deleteUser(u.getKey());
		}
	}

	@Override
	public UserErrorMessage accept(User user) {
		String operation=(String)getRequest().getAttributes().get("operation");
		if(operation.equals("register")) {
			System.out.println("REGISTER");
			User u = userdao.getUser(user.getUsername());
			
			if (u != null) {
				return new UserErrorMessage(false, "Username already taken.");
			}
			userdao.addUser(user);
			return new UserErrorMessage(true, "Registration successful.");
		}
		return null;
	}
}
