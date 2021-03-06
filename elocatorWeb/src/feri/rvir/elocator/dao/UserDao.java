package feri.rvir.elocator.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import feri.rvir.elocator.dao.EMF;
import feri.rvir.elocator.rest.resource.user.User;

public class UserDao {

	public void addUser(User u) {
		if(getUser(u.getUsername())==null) {
			EntityManager em = EMF.getInstance().createEntityManager();
			em.getTransaction().begin();
			em.persist(u);
			em.getTransaction().commit();
			em.close();
			System.out.println("User added");
		} else {
			System.out.println("User already exists");
		}
	}

	public User getUser(String username) {
		EntityManager em = EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		Query q = em
				.createQuery("SELECT u FROM User u WHERE u.username = :username");
		q.setParameter("username", username);

		User u = null;
		try {
			u = (User) q.getSingleResult();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		em.getTransaction().commit();
		em.close();
		return u;
	}

	
	public User getUser(Long key) {
		EntityManager em = EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT u FROM User u WHERE u.key = :key");
		q.setParameter("key", key);

		User u = null;
		try {
			u = (User) q.getSingleResult();
		} catch (Exception e) {
			System.out
					.println("No result for query with key " + key);
		}
		em.getTransaction().commit();
		em.close();
		return u;
	}

	
	public List<User> getAll() {
		EntityManager em = EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT u FROM User u");
		
		@SuppressWarnings("unchecked")
		List<User> users = q.getResultList();
		
		em.getTransaction().commit();
		em.close();
		return users;
	}

	public void deleteUser(Long key) {
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
		EntityManager em = EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT u FROM User u WHERE u.username = :username");
		q.setParameter("username", u.getUsername());
		User p = (User) q.getSingleResult();
		em.merge(p);
		em.getTransaction().commit();
	}

}
