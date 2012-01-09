package feri.rvir.elocator.rest.resource.tracking;

import java.util.ArrayList;

import org.restlet.resource.ServerResource;

import feri.rvir.elocator.rest.resource.user.User;

public class TrackingServerResource extends ServerResource implements TrackingResource {

	@Override
	public Tracking retrieve() {
		System.out.println("RETRIEVE TrackingServerResource");
		String accountName=(String)getRequest().getAttributes().get("accountName");
		System.out.println(accountName);
		// TODO pridobi objekt Tracking glede na accountName uporabnika
		ArrayList<User> userList=new ArrayList<User>();
		userList.add(new User("accountName", "accountName", "accountType"));
		userList.add(new User("accountName", "accountName", "accountType"));
		return new Tracking(new User("accountName", "accountName", "accountType"), userList);
	}

	@Override
	public void store(Tracking tracking) {
		System.out.println("STORE TrackingServerResource");
		// TODO zapise Tracking objekt v bazo

	}

	@Override
	public void store(String accountNameTracker, String accountNameBeingTracked) {
		System.out.println("STORE2 TrackingServerResource");
		// TODO uporabniku s prvim accountName v Tracking objektu doda uporabnika z drugim accountName

	}

	@Override
	public void remove(String accountName) {
		System.out.println("REMOVE TrackingServerResource");
		// TODO izbriše vse uporabnike, ki katerim uprabnik s accountName sledi

	}

	@Override
	public void remove(String accountNameTracker, String accountNameBeingTracked) {
		System.out.println("REMOVE2 TrackingServerResource");
		// TODO izbriše uporabnika s drugim accountName, in sicer na seznamu uporabnika s prvim accountName

	}

}
