package feri.rvir.elocator.rest.resource.user;

import org.restlet.resource.ServerResource;

import feri.rvir.elocator.dao.UserDao;
import feri.rvir.elocator.rest.resource.RestletErrorMessage;

public class UserServerResource extends ServerResource implements UserResource {
	
	private UserDao userdao = new UserDao();
	
	@Override
	public User retrieve() {
		System.out.println("RETRIEVE");
		String username=(String)getRequest().getAttributes().get("username");
		User u = userdao.getUser(username);
		User u2=null;
		if(u!=null) {
			u2=new User(u.getKey(), u.getUsername(),u.getPassword());
		}
		return u2;
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
	public RestletErrorMessage accept(User user) {
		System.out.println("ACCEPT UserServerResource");
		String operation=(String)getRequest().getAttributes().get("operation");
		if(operation.equals("register")) {
			System.out.println("REGISTER");
			User u = userdao.getUser(user.getUsername());
			
			if (u != null) {
				return new RestletErrorMessage(false, "Username already taken.");
			}
			userdao.addUser(user);
			return new RestletErrorMessage(true, "Registration successful.");
		}
		return null;
	}
}
