package feri.rvir.elocator.rest.resource.tracking;

import java.util.ArrayList;
import java.util.List;

import org.restlet.resource.ServerResource;

import feri.rvir.elocator.dao.TrackingDao;
import feri.rvir.elocator.dao.UserDao;
import feri.rvir.elocator.rest.resource.RestletErrorMessage;
import feri.rvir.elocator.rest.resource.user.User;

public class TrackingServerResource extends ServerResource implements TrackingResource {

	TrackingDao tdao = new TrackingDao();
	UserDao udao = new UserDao();	
	
	
	@Override
	public ArrayList<Tracking> retrieve() {
		System.out.println("RETRIEVE TrackingServerResource");
		String username=(String)getRequest().getAttributes().get("username");
		System.out.println(username);

		User u = udao.getUser(username);
		
		if (u == null) {
			return null;
		} else {
			List<Tracking> trackings = tdao.getTrackingsByUser(u.getKey());
			ArrayList<Tracking> trackings2=new ArrayList<Tracking>();
			for(Tracking t:trackings) {
				trackings2.add(new Tracking(t.getKey(), t.getKey(), t.getChild()));
			}
			return trackings2;
		}
	}

	@Override
	public void store(Tracking tracking) {
		System.out.println("STORE TrackingServerResource");
		tdao.addTracking(tracking);
	}

	@Override
	public void remove(String child) {
		System.out.println("REMOVE TrackingServerResource");
		String username=(String)getRequest().getAttributes().get("username");
		User uTracker = udao.getUser(username);
		User uChild = udao.getUser(child);
		
		if (uTracker == null || uChild == null) return;
		
		tdao.deleteUserFromBeingTracked(uTracker.getKey(),uChild.getKey());
	}

	@Override
	public RestletErrorMessage accept(String child) {
		System.out.println("ACCEPT TrackingServerResource");
		String operation=(String)getRequest().getAttributes().get("operation");
		
		String username=(String)getRequest().getAttributes().get("username");
		User uTracker = udao.getUser(username);
		User uChild = udao.getUser(child);
		
		if(uTracker!=null&&uChild!=null) {
			if(operation.equals("add")) {
				if(!uTracker.getKey().equals(uChild.getKey())) {
					List<Tracking> userTrackings = tdao.getTrackingsByUser(uTracker.getKey());
					for (Tracking t : userTrackings) {
						if (t.getChild().equals(uChild.getKey())) {
							return new RestletErrorMessage(false, "You are already tracking "+uChild.getUsername()+".");
						}
					}
					Tracking t = new Tracking(uTracker.getKey(),uChild.getKey());
					tdao.addTracking(t);
					return new RestletErrorMessage(true, "You are now tracking "+uChild.getUsername()+".");
				} else {
					return new RestletErrorMessage(false, "Cannot add yourself.");
				}
			} else if(operation.equals("remove")) {
				if(!uTracker.getKey().equals(uChild.getKey())) {
					tdao.deleteUserFromBeingTracked(uTracker.getKey(),uChild.getKey());
					return new RestletErrorMessage(true, "You have stopped tracking "+uChild.getUsername()+".");
				} else {
					return new RestletErrorMessage(false, "Cannot remove yourself.");
				}
			} else {
				return new RestletErrorMessage(false, "Unknown opreation.");
			}
		} else {
			return new RestletErrorMessage(false, "User does not exist.");
		}
	}

}
