package feri.rvir.elocator;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.*;

import feri.rvir.elocator.dao.LocationDao;
import feri.rvir.elocator.dao.UserDao;
import feri.rvir.elocator.rest.resource.location.Location;
import feri.rvir.elocator.rest.resource.user.User;

@SuppressWarnings("serial")
public class ElocatorWebServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		UserDao udao = new UserDao();
		User u = new User();
		u = udao.getUser("test");
		System.out.println(u.getKey() + " je key");
		
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		Location l = new Location(u.getKey(),now,46.565617,15.62346);
		LocationDao ldao = new LocationDao();
		
		ldao.addLocation(l);
	}
}
