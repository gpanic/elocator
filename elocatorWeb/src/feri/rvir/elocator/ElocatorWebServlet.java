package feri.rvir.elocator;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.Key;

import feri.rvir.elocator.dao.LocationDao;
import feri.rvir.elocator.dao.TrackingDao;
import feri.rvir.elocator.dao.UserDao;
import feri.rvir.elocator.rest.resource.location.Location;
import feri.rvir.elocator.rest.resource.tracking.Tracking;
import feri.rvir.elocator.rest.resource.user.User;

@SuppressWarnings("serial")
public class ElocatorWebServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		PrintWriter w = resp.getWriter();
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");

		UserDao userDao = new UserDao();
		TrackingDao trackingDao = new TrackingDao();
		LocationDao ldao = new LocationDao();

		List<Tracking> trackings = trackingDao.getAllTrackings();
		List<User> users = userDao.getAll();
		List<Location> locations = ldao.getAll();

		w.println("Number of saved users: " + users.size());
		w.println("Number of saved trackings: " + trackings.size());
		w.println("Number of saved locations: " + locations.size());

		//User u = new User("tokenR", "accnamesthfadsfds", "typeR");

		Tracking t = new Tracking();
		//t.setTracker(u);
		// trackingDao.addTracking(t);

		for (User j : users) {
			// userDao.deleteUser(j.getAuthToken());
			//w.println(j.getAuthToken());
			// w.println("Deleted");
		}

		Location l = new Location();
		//User c = new User("sec", "locationName", "type1");
		//userDao.deleteUser("sec");
		// l.setUser(c);
		// l.setLatitude(4514.0);
		// ldao.addLocation(l);
		
//		Tracking novi = new Tracking();
//		User sec = userDao.getUserByAuthToken("sec");
//		novi.setTracker(sec);
//		trackingDao.addTracking(novi);

		/*
		 * User u = new User("tokenR", "accnamesthfadsfds", "typeR");
		 * 
		 * Tracking t = new Tracking(); t.setTracker(u);
		 * trackingDao.addTracking(t);
		 * 
		 * w.println("Trackings: " + trackings.size()); for (Tracking p :
		 * trackings) { trackingDao.deleteTrackByUserId(p.getKey());
		 * w.println("Deleted"); }
		 * 
		 * User ena = new User("token1", "accountname1", "type1"); User dva =
		 * new User("token2", "accountname2", "type2");
		 * 
		 * userji.add(ena); userji.add(dva);
		 * 
		 * Location l = new Location(); User c = new User("security",
		 * "locationName", "type1"); l.setUser(c); l.setLatitude(4514.0);
		 * 
		 * Date date = new Date();
		 * 
		 * 
		 * l.setTimestamp(date); ldao.addLocation(l);
		 * 
		 * for (Location j : locations) { ldao.deleteLocationByKey(j.getKey());
		 * w.println("Deleted"); }
		 */
	}
}
