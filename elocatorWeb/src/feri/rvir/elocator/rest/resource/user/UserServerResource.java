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
		//TODO preko authTokena se pridobi uporabnik iz baze
		return new User("usernameExample","passwordExample");
	}

	@Override
	public void store(User user) {
		System.out.println("STORE UserServerResource");
		//TODO uporabnik se hrani v bazo
		userDao.addUser(user);
	}

	@Override
	public void remove(int id) {
		System.out.println("REMOVE UserServerResource");
		//TODO uporabnik se izbrise iz baze
	}

	@Override
	public UserErrorMessage register(User user) {
		System.out.println(user);
		return new UserErrorMessage(false,"Ni slo.");
	}

}
