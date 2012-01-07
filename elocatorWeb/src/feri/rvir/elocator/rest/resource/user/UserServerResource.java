package feri.rvir.elocator.rest.resource.user;

import org.restlet.resource.ServerResource;

public class UserServerResource extends ServerResource implements UserResource {
	
	private static volatile User user=new User("gregor.panic@gmail.com", "Gregor Panic");

	@Override
	public User retrieve() {
		System.out.println("RETRIEVE USER REQUEST");
		return user;
	}

	@Override
	public void store(User user) {
		System.out.println("STORE USER REQUEST");
		UserServerResource.user=user;
	}

	@Override
	public void remove() {
		System.out.println("REMOVE USER REQUEST");
		user=null;
	}

}
