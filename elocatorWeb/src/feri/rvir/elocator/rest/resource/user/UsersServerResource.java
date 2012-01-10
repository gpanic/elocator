package feri.rvir.elocator.rest.resource.user;

import java.util.ArrayList;

import org.restlet.resource.ServerResource;

import feri.rvir.elocator.dao.UserDao;

public class UsersServerResource extends ServerResource implements
		UsersResource {

	UserDao userDao = new UserDao();

	@Override
	public ArrayList<User> retrieve() {
		System.out.println("RETRIEVE UsersServerResource");
		// TODO iz baze prebere vse uporabnike in jih vrne
		ArrayList<User> users = (ArrayList<User>) userDao.getAll();
		return users;
	}

	@Override
	public void remove() {
		System.out.println("REMOVE UsersServerResource");
		// TODO iz baze izbrise vse uporabnike
		ArrayList<User> users = (ArrayList<User>) userDao.getAll();
		for (User j : users) {
			userDao.deleteUser(j.getAuthToken());
			System.out.println("User " + j.getAuthToken() + " deleted");
		}
	}

}
