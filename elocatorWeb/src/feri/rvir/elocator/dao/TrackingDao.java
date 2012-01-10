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
	
	public void updateTracking(Tracking t) {
		EntityManager em = EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		em.merge(t);
		em.getTransaction().commit();
		em.close();
	}
	
	public void deleteTrackByUserId(Key key) {
		EntityManager em = EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT t FROM Tracking t WHERE t.key = :key");
		q.setParameter("key", key);
		Tracking t = (Tracking) q.getSingleResult();
		em.remove(t);
		em.getTransaction().commit();
		em.close();
	}
	
}
