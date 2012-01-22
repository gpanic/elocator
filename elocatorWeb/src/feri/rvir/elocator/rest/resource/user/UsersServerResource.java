package feri.rvir.elocator.rest.resource.user;

import java.util.ArrayList;
import java.util.List;

import org.restlet.resource.ServerResource;

import feri.rvir.elocator.dao.TrackingDao;
import feri.rvir.elocator.dao.UserDao;
import feri.rvir.elocator.rest.resource.tracking.Tracking;

public class UsersServerResource extends ServerResource implements UsersResource {

	UserDao userDao = new UserDao();

	TrackingDao tdao = new TrackingDao();

	@Override
	public ArrayList<User> retrieve() {
		System.out.println("RETRIEVE UsersServerResource");
		List<User> users = userDao.getAll();
		ArrayList<User> users2=new ArrayList<User>();
		for(User u:users) {
			users2.add(new User(u.getKey(), u.getUsername(), u.getPassword()));
		}
		return users2;
	}

	@Override
	public void remove() {
		System.out.println("REMOVE UsersServerResource");
	}

	@Override
	public ArrayList<User> accept(String username) {
		System.out.println("ACCEPT UsersServerResource");
		User u=userDao.getUser(username);
		if(u!=null) {
			List<Tracking> trackings=tdao.getTrackingsByUser(u.getKey());
			ArrayList<User> users=new ArrayList<User>();
			for(Tracking t:trackings) {
				Long key=t.getChild();
				User child=userDao.getUser(key);
				users.add(new User(child.getKey(), child.getUsername(), child.getPassword()));
			}
			return users;
		} else {
			return new ArrayList<User>();
		}
	}

}
