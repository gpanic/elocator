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
		// TODO iz baze izbrise vse uporabnike

	}

	@Override
	public List<User> accept(User u) {
		Key trackerKey = u.getKey();
		List<Tracking> userTrackings = tdao.getTrackingsByUser(trackerKey);
		
		if (userTrackings == null) return null;
		
		List<User> childs = new ArrayList<User>();
		User temp = null;
		for (Tracking t:userTrackings) {
			temp = userDao.getUser(t.getChild());
			childs.add(temp);
		}
		return childs;
	}

}
