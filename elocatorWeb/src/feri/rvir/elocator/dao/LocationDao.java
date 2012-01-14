package feri.rvir.elocator.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import com.google.appengine.api.datastore.Key;

import feri.rvir.elocator.dao.EMF;
import feri.rvir.elocator.rest.resource.location.Location;
import feri.rvir.elocator.rest.resource.user.User;
public class LocationDao {

	public void addLocation(Location l) {
		EntityManager em = EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		em.persist(l);
		em.getTransaction().commit();
		em.close();
	}
	
	public List<Location> getAll() {
		EntityManager em = EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT l FROM Location l");
		List<Location> locations = q.getResultList();
		em.getTransaction().commit();
		em.close();
		return locations;
	}
	
	public void deleteLocationByKey(Key key) {
		EntityManager em = EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT l FROM Location l WHERE l.key = :key");
		q.setParameter("key", key);
		Location l = (Location) q.getSingleResult();
		em.remove(l);
		em.getTransaction().commit();
		em.close();
	}
	
	public void deleteLocationByTimestampAndUser(Key userKey, Date timestamp) {
		EntityManager em = EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT l FROM Location l WHERE l.userKey = :userKey AND l.timestamp = :timestamp");
		q.setParameter("userKey", userKey);
		q.setParameter("timestamp", timestamp);
		Location l = (Location) q.getSingleResult();
		em.remove(l);
		em.getTransaction().commit();
		em.close();
	}
	
	public List<Location> getLocation(Key userKey, Date timestamp) {
		EntityManager em = EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT l FROM Location l WHERE l.userKey = :userKey AND l.timestamp BETWEEN :start AND :end");
		q.setParameter("userKey", userKey);
		q.setParameter("start", timestamp, TemporalType.DATE);
		q.setParameter("end", timestamp,TemporalType.DATE);
		List<Location> locations = q.getResultList();
		em.getTransaction().commit();
		em.close();
		return locations;
	}
	
}
