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
		User u = new User("username","password");
		List<User> users = userDao.getAll();
	    userDao.addUser(u);
		//LocationDao ldao = new LocationDao();
		
		//User u = userDao.getUser("username1");

		// userDao.merge(u);
		
		for (User h:users) {
			w.println(h.getUsername());
		}

	}
}
