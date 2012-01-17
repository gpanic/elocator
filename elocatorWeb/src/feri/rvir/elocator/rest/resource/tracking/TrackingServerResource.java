package feri.rvir.elocator.rest.resource.tracking;

import java.util.List;

import org.restlet.resource.ServerResource;

import feri.rvir.elocator.dao.TrackingDao;
import feri.rvir.elocator.dao.UserDao;
import feri.rvir.elocator.rest.resource.user.User;

public class TrackingServerResource extends ServerResource implements TrackingResource {

	TrackingDao tdao = new TrackingDao();
	UserDao udao = new UserDao();	
	
	
	@Override
	public List<Tracking> retrieve() {
		System.out.println("RETRIEVE TrackingServerResource");
		String username=(String)getRequest().getAttributes().get("username");
		System.out.println(username);

		User u = udao.getUser(username);
		
		if (u == null) return null;
		List<Tracking> trackings = tdao.getTrackingsByUser(u.getKey());
		return trackings;
	}

	@Override
	public void store(Tracking tracking) {
		System.out.println("STORE TrackingServerResource");
		tdao.addTracking(tracking);
	}

	@Override
	public void remove(String usernameTracker, String usernameBeingTracked) {
		System.out.println("REMOVE2 TrackingServerResource");
		User tracker = udao.getUser(usernameTracker);
		User child = udao.getUser(usernameBeingTracked);
		
		if (tracker == null || child == null) return;
		
		tdao.deleteUserFromBeingTracked(tracker.getKey(),child.getKey());
	}

	@Override
	public void accept(String tracker, String child) {
		User uTracker = udao.getUser(tracker);
		User uChild = udao.getUser(child);
		
		if (uTracker == null || uChild == null) return;
		
		Tracking t = new Tracking(uTracker.getKey(),uChild.getKey());
		tdao.addTracking(t);
	}

}
