package feri.rvir.elocator.rest.resource.tracking;

import java.util.ArrayList;

import org.restlet.resource.ServerResource;

import feri.rvir.elocator.rest.resource.user.User;

public class TrackingServerResource extends ServerResource implements TrackingResource {

	@Override
	public Tracking retrieve() {
		System.out.println("RETRIEVE TrackingServerResource");
		String username=(String)getRequest().getAttributes().get("username");
		System.out.println(username);
		// TODO pridobi objekt Tracking glede na username uporabnika
		ArrayList<User> userList=new ArrayList<User>();
		userList.add(new User("usernameExample","passwordExample"));
		userList.add(new User("usernameExample","passwordExample"));
		return null;
	}

	@Override
	public void store(Tracking tracking) {
		System.out.println("STORE TrackingServerResource");

	}

	@Override
	public void store(String usernameTracker, String usernameBeingTracked) {
		System.out.println("STORE2 TrackingServerResource");

	}

	@Override
	public void remove(String username) {
		System.out.println("REMOVE TrackingServerResource");

	}

	@Override
	public void remove(String usernameTracker, String usernameBeingTracked) {
		System.out.println("REMOVE2 TrackingServerResource");

	}

}
