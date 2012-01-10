package feri.rvir.elocator.rest.resource.user;

import org.restlet.resource.ServerResource;

import feri.rvir.elocator.dao.UserDao;

public class UserServerResource extends ServerResource implements UserResource {

	UserDao userDao = new UserDao();
	
	@Override
	public User retrieve() {
		System.out.println("RETRIEVE UserServerResource");
		String authToken=(String)getRequest().getAttributes().get("authToken");
		System.out.println(authToken);
		//TODO preko authTokena se pridobi uporabnik iz baze
		
		User u = userDao.getUserByAuthToken(authToken);
		return u;
		//new User("authTokenExample","accountNameExample","accountTypeExampleeee");
	}

	@Override
	public void store(User user) {
		System.out.println("STORE UserServerResource");
		System.out.println(user.getAuthToken());
		//TODO uporabnik se hrani v bazo
		userDao.addUser(user);
	}

	@Override
	public void remove(String authKey) {
		System.out.println("REMOVE UserServerResource");
		System.out.println(authKey);
		//TODO uporabnik se izbrise iz baze
		userDao.deleteUser(authKey);
	}

}
