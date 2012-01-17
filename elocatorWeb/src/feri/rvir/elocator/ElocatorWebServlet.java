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
		
		UserDao udao=new UserDao();
		User u = new User("keks","keks");
		//udao.addUser(u);
		User h = udao.getUser("keks");
		
		//System.out.println(h.getKey());
		System.out.println("size " + udao.getAll().size());
			

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
