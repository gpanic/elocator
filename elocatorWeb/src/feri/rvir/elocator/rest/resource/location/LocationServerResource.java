package feri.rvir.elocator.rest.resource.location;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
		String timestamp=(String)getRequest().getAttributes().get("timestamp");
		System.out.println(username);
		System.out.println(timestamp);
		
		User u = userdao.getUser(username);
		List<Location> l = locdao.getLocation(u.getKey(), new Date(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,Calendar.HOUR,Calendar.MINUTE,Calendar.SECOND));
		
		return null;
		//return new Location(new User("usernameExample", "passwordExample"), new Date(), 223, 346);
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
	public List<Location> accept(String username) {
		// TODO Auto-generated method stub
		User u = userdao.getUser(username);
		if (u == null) return null;
		Calendar now = Calendar.getInstance();
		Date nowd = now.getTime();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -24);
		Date d = cal.getTime();
		List<Location> locations = locdao.getLocations(u.getKey(), d, nowd);  
		return locations;
	}

}
