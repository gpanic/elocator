package feri.rvir.elocator.rest.resource.user;

import java.util.ArrayList;
import java.util.List;

import org.restlet.resource.ServerResource;

import com.google.appengine.api.datastore.Key;

import feri.rvir.elocator.dao.TrackingDao;
import feri.rvir.elocator.dao.UserDao;
import feri.rvir.elocator.rest.resource.tracking.Tracking;

public class UsersServerResource extends ServerResource implements
		UsersResource {

	UserDao userDao = new UserDao();

	TrackingDao tdao = new TrackingDao();

	@Override
	public List<User> retrieve() {
		System.out.println("RETRIEVE UsersServerResource");
		List<User> users = userDao.getAll();
		return users;
	}

	@Override
	public void remove() {
		System.out.println("REMOVE UsersServerResource");
	}

	@Override
	public List<User> accept(String username) {
		System.out.println("ACCEPT");
		User u=userDao.getUser(username);
		if(u!=null) {
			List<Tracking> trackings=tdao.getTrackingsByUser(u.getKey());
			List<User> users=new ArrayList<User>();
			for(Tracking t:trackings) {
				Key key=t.getChild();
				users.add(userDao.getUser(key));
			}
			return users;
		} else {
			return null;
		}
	}

}
