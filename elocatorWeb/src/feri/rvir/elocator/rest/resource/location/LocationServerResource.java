package feri.rvir.elocator.rest.resource.location;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.restlet.resource.ServerResource;

import feri.rvir.elocator.rest.resource.user.User;

public class LocationServerResource extends ServerResource implements LocationResource {

	@Override
	public Location retrieve() {
		System.out.println("RETRIEVE LocationServerResource");
		String authToken=(String)getRequest().getAttributes().get("authToken");
		String timestamp=(String)getRequest().getAttributes().get("timestamp");
		System.out.println(authToken);
		System.out.println(timestamp);
		// TODO pridobi lokacijo iz baze glede na authToken uporabnika in Timestamp
		return new Location(new User("authToken", "accountName", "accountType"), Calendar.getInstance(), 223, 346);
	}

	@Override
	public void store(Location location) {
		System.out.println("STORE LocationServerResource");
		// TODO zapise lokacijo v bazo

	}

	@Override
	public void remove(String authToken, Timestamp timestamp) {
		System.out.println("REMOVE LocationServerResource");
		// TODO izbrise lokacijo ustreznega uporabnika in glede na cas

	}

}
