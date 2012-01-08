package feri.rvir.elocator.rest.resource.tracking;

import org.restlet.resource.ServerResource;

public class TrackingServerResource extends ServerResource implements TrackingResource {

	@Override
	public Tracking retrieve() {
		System.out.println("RETRIEVE TrackingServerResource");
		String authToken=(String)getRequest().getAttributes().get("authToken");
		System.out.println(authToken);
		// TODO pridobi objekt Tracking glede na authToken uporabnika
		return null;
	}

	@Override
	public void store(Tracking tracking) {
		System.out.println("STORE TrackingServerResource");
		// TODO zapise Tracking objekt v bazo

	}

	@Override
	public void store(String authTokenTracker, String authTokenBeingTracked) {
		System.out.println("STORE2 TrackingServerResource");
		// TODO uporabniku s prvim authToken v Tracking objektu doda uporabnika z drugim authToken

	}

	@Override
	public void remove(String authToken) {
		System.out.println("REMOVE TrackingServerResource");
		// TODO izbriše vse uporabnike, ki katerim uprabnik s authToken sledi

	}

	@Override
	public void remove(String authTokenTracker, String authTokenBeingTracked) {
		System.out.println("REMOVE2 TrackingServerResource");
		// TODO izbriše uporabnika s drugim authToken, in sicer na seznamu uporabnika s prvim authToken

	}

}
