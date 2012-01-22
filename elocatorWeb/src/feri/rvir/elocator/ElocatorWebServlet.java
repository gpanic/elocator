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
import feri.rvir.elocator.util.Crypto;

@SuppressWarnings("serial")
public class ElocatorWebServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		/*
		UserDao udao=new UserDao();
		User u = new User("username","password");
		//udao.addUser(u);
		
		User p = udao.getUser("username");
		System.out.println(p.getKey());
		LocationDao ldao = new LocationDao();
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		Location l = new Location(p.getKey(),now,12541.0,45292.0);
		ldao.addLocation(l);
		l = ldao.getLastLocation(p.getKey());
		System.out.println(l.getTimestamp().toString());
		
		udao.addUser(new User("childOfTest",Crypto.hash("test", "SHA-1")));
		
		
		User parent=udao.getUser("test");
		User child=udao.getUser("childOfTest");
		TrackingDao tdao=new TrackingDao();
		tdao.addTracking(new Tracking(parent.getKey(), child.getKey()));


*/
		/*
		
		System.out.println(u.getUsername());
		System.out.println(u.getPassword());
		
		Tracking t = new Tracking();
		TrackingDao tdao = new TrackingDao();
		tdao.addTracking(t);
		System.out.print("tracking addded");
		

		*/
		
/*
		PrintWriter w = resp.getWriter();
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");

		UserDao udao = new UserDao();
		@SuppressWarnings("deprecation")
		Calendar cal = Calendar.getInstance();
		Date time = cal.getTime();
		Location l = new Location(udao.getAll().get(0).getKey(),time,435435.0,3454.0);
		LocationDao ldao = new LocationDao();
		ldao.addLocation(l);
		
		List<Location> z = ldao.getLocation(udao.getAll().get(0).getKey(),time);
		w.println("Locations " + z.size());
		
		@SuppressWarnings("deprecation")
		Date ovi = new Date(2012,1,14,15,51,6);
		
		User p = udao.getUser("test");
		
		if (p == null) w.println("ni userja");
		
		List<Location> locations = ldao.getAll();
		
		for (Location h : locations) {
			w.println(h.getTimestamp().toString());
		}
		
		List<Location> j = ldao.getLocation(udao.getAll().get(0).getKey(), ovi);
		w.println(j.size());
		w.println("Na�el sem objekt location na podlagi date");*/
	}
}
