package feri.rvir.elocator.dao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
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
	
	public User getUserByAuthToken(String authToken) {
		EntityManager em = EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT u FROM User u WHERE u.authToken = :authToken");
		q.setParameter("authToken", authToken);
		User u = (User) q.getSingleResult();
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
	
	public void deleteUser(String authToken) {
		EntityManager em = EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT u FROM User u WHERE u.authToken = :authToken");
		q.setParameter("authToken", authToken);
		User u = (User) q.getSingleResult();
		em.remove(u);
		em.getTransaction().commit();
		em.close();
	}
	
}
