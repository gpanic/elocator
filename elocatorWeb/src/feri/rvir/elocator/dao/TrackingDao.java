package feri.rvir.elocator.dao;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.appengine.api.datastore.Key;

import feri.rvir.elocator.dao.EMF;
import feri.rvir.elocator.rest.resource.tracking.*;
import feri.rvir.elocator.rest.resource.user.User;

public class TrackingDao {

	public void addTracking(Tracking t) {
		EntityManager em = EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		em.persist(t);
		em.getTransaction().commit();
		em.close();
	}
	
	public List<Tracking> getAllTrackings() {
		EntityManager em = EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT t FROM Tracking t");
		List<Tracking> trackings = q.getResultList();
		em.getTransaction().commit();
		em.close();
		return trackings;
	}
	
	public List<Tracking> getTrackingsByUser(Long userKey) {
		EntityManager em = EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT t FROM Tracking t WHERE t.trackerKey = :key");
		q.setParameter("key", userKey);
		List<Tracking> trackings = q.getResultList();
		em.getTransaction().commit();
		em.close();
		return trackings;
	}
	
	public void updateTracking(Tracking t) {
		EntityManager em = EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		em.merge(t);
		em.getTransaction().commit();
		em.close();
	}
	
	public void deleteTrackByUserKey(Long key) {
		EntityManager em = EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT t FROM Tracking t WHERE t.trackerKey = :trackerKey");
		q.setParameter("trackerKey", key);
		Tracking t = (Tracking) q.getSingleResult();
		em.remove(t);
		em.getTransaction().commit();
		em.close();
	}
	
	public void deleteUserFromBeingTracked(Long trackerKey, Long childKey) {
		EntityManager em = EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT t FROM Tracking t WHERE t.trackerKey = :trackerKey AND t.child = :child");
		q.setParameter("trackerKey", trackerKey);
		q.setParameter("child", childKey);
		Tracking t = (Tracking) q.getSingleResult();
		em.remove(t);
		em.getTransaction().commit();
		em.close();
	}
	
}
