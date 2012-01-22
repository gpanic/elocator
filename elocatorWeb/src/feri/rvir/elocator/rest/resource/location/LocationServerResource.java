package feri.rvir.elocator.rest.resource.location;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.restlet.resource.ServerResource;

import feri.rvir.elocator.dao.LocationDao;
import feri.rvir.elocator.dao.UserDao;
import feri.rvir.elocator.rest.resource.user.User;

public class LocationServerResource extends ServerResource implements LocationResource {
	
	UserDao userdao = new UserDao();
	LocationDao locdao = new LocationDao();

	@Override
	public Location retrieve() {
		System.out.println("RETRIEVE LocationServerResource");
		String username=(String)getRequest().getAttributes().get("username");
		//String timestamp=(String)getRequest().getAttributes().get("timestamp");
		System.out.println(username);
		
		User u = userdao.getUser(username);
		if (u == null) return null;
		
		Location l = locdao.getLastLocation(u.getKey());
		return new Location(l.getUserKey(),l.getTimestamp(),l.getLatitude(),l.getLongitude());
	}

	@Override
	public void store(Location location) {
		locdao.addLocation(location);
	}

	@Override
	public void remove(String username, Date timestamp) {
		User u = userdao.getUser(username);
		locdao.deleteLocationByTimestampAndUser(u.getKey(),timestamp);
	}

	@Override
	public ArrayList<Location> accept(String username) {
		User u = userdao.getUser(username);
		if (u == null) return null;
		Calendar now = Calendar.getInstance();
		Date nowd = now.getTime();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -24);
		Date d = cal.getTime();
		List<Location> locations = locdao.getLocations(u.getKey(), d, nowd);  
		ArrayList<Location> locations2=new ArrayList<Location>();
		for(Location l:locations) {
			locations2.add(new Location(l.getKey(), l.getTimestamp(), l.getLatitude(), l.getLongitude()));
		}
		return locations2;
	}

}
