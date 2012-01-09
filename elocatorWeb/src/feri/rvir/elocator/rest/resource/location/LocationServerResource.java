package feri.rvir.elocator.rest.resource.location;

import java.sql.Timestamp;
import java.util.Calendar;

import org.restlet.resource.ServerResource;

import feri.rvir.elocator.rest.resource.user.User;

public class LocationServerResource extends ServerResource implements LocationResource {

	@Override
	public Location retrieve() {
		System.out.println("RETRIEVE LocationServerResource");
		String username=(String)getRequest().getAttributes().get("username");
		String timestamp=(String)getRequest().getAttributes().get("timestamp");
		System.out.println(username);
		System.out.println(timestamp);
		return new Location(new User("usernameExample", "passwordExample"), Calendar.getInstance(), 223, 346);
	}

	@Override
	public void store(Location location) {
		System.out.println("STORE LocationServerResource");
	}

	@Override
	public void remove(String username, Timestamp timestamp) {
		System.out.println("REMOVE LocationServerResource");
	}

}
