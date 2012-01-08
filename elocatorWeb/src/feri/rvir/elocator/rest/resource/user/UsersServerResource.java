package feri.rvir.elocator.rest.resource.user;

import java.util.ArrayList;

import org.restlet.resource.ServerResource;

public class UsersServerResource extends ServerResource implements UsersResource {

	@Override
	public ArrayList<User> retrieve() {
		System.out.println("RETRIEVE UsersServerResource");
		// TODO iz baze prebere vse uporabnike in jih vrne
		return null;
	}

	@Override
	public void remove() {
		System.out.println("REMOVE UsersServerResource");
		// TODO iz baze izbrise vse uporabnike

	}

}
