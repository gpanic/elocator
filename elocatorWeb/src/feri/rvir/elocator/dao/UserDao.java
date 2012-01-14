package feri.rvir.elocator.dao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.appengine.api.datastore.Key;

import feri.rvir.elocator.dao.EMF;
import feri.rvir.elocator.rest.resource.location.Location;
import feri.rvir.elocator.rest.resource.user.User;


public class UserDao {

	public void addUser(User u) {
		EntityManager em = EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		em.persist(u);
		em.getTransaction().commit();
		em.close();
	}
	
	public User getUser(String username) {
		EntityManager em = EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT u FROM User u WHERE u.username = :username");
		q.setParameter("username", username);
		
		User u = null;
		try {
			 u = (User) q.getSingleResult();
		} catch (Exception e) {
			System.out.println("No result for query with username " + username);
		}
		em.getTransaction().commit();
		em.close();
		return u;
	}
	
	public List<User> getAll() {
		EntityManager em = EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT u FROM User u");
		List<User> users = q.getResultList();
		em.getTransaction().commit();
		em.close();
		return users;
	}
	
	public void deleteUser(Key key) {
		EntityManager em = EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT u FROM User u WHERE u.key = :key");
		q.setParameter("key", key);
		User u = (User) q.getSingleResult();
		em.remove(u);
		em.getTransaction().commit();
		em.close();
	}

	public void merge(User u) {
		// TODO Auto-generated method stub
		EntityManager em = EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT u FROM User u WHERE u.username = :username");
		q.setParameter("username", u.getUsername());
		User p = (User) q.getSingleResult();
		em.merge(p);
		em.getTransaction().commit();
	}
	
}
