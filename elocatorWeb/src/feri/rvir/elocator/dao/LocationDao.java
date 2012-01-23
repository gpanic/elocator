package feri.rvir.elocator.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import feri.rvir.elocator.dao.EMF;
import feri.rvir.elocator.rest.resource.location.Location;
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
		
		@SuppressWarnings("unchecked")
		List<Location> locations = q.getResultList();
		
		em.getTransaction().commit();
		em.close();
		return locations;
	}
	
	public List<Location> getAllUserLocations(Long userKey) {
		EntityManager em = EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT l FROM Location l WHERE l.userKey = :userKey");
		q.setParameter("userKey", userKey);
		List<Location> locations = q.getResultList();
		em.getTransaction().commit();
		em.close();
		return locations;
	}
	
	public void deleteLocationByKey(Long key) {
		EntityManager em = EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT l FROM Location l WHERE l.key = :key");
		q.setParameter("key", key);
		Location l = (Location) q.getSingleResult();
		em.remove(l);
		em.getTransaction().commit();
		em.close();
	}
	
	public void deleteLocationByTimestampAndUser(Long userKey, Date timestamp) {
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
	
	public List<Location> getLocation(Long userKey, Date timestamp) {
		EntityManager em = EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT l FROM Location l WHERE l.userKey = :userKey AND l.timestamp BETWEEN :start AND :end");
		q.setParameter("userKey", userKey);
		q.setParameter("start", timestamp, TemporalType.DATE);
		q.setParameter("end", timestamp,TemporalType.DATE);
		
		@SuppressWarnings("unchecked")
		List<Location> locations = q.getResultList();
		
		em.getTransaction().commit();
		em.close();
		return locations;
	}
	
	@SuppressWarnings("unchecked")
	public List<Location> getLocations(Long userKey, Date start, Date end) {
		EntityManager em = EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT l FROM Location l WHERE l.userKey = :userKey AND l.timestamp BETWEEN :start AND :end");
		q.setParameter("userKey", userKey);
		q.setParameter("start", start, TemporalType.DATE);
		q.setParameter("end", end,TemporalType.DATE);
		List<Location> locations = null;
		
		try {
			locations = q.getResultList();
		} catch (Exception e) {
			
		}
		em.getTransaction().commit();
		em.close();
		return locations;
	}
	
	public Location getLastLocation(Long userKey) {
		EntityManager em = EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT l FROM Location l WHERE l.userKey = :userKey ORDER BY l.key DESC");
		q.setMaxResults(1);
		q.setParameter("userKey", userKey);
		Location l = (Location) q.getSingleResult();
		
		em.getTransaction().commit();
		em.close();
		return l;
	}
	
}
