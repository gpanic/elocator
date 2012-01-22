package feri.rvir.elocator.rest;

import java.util.List;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.data.ChallengeScheme;
import org.restlet.routing.Router;
import org.restlet.security.ChallengeAuthenticator;
import org.restlet.security.SecretVerifier;

import feri.rvir.elocator.dao.UserDao;
import feri.rvir.elocator.rest.resource.location.LocationServerResource;
import feri.rvir.elocator.rest.resource.tracking.TrackingServerResource;
import feri.rvir.elocator.rest.resource.user.User;
import feri.rvir.elocator.rest.resource.user.UserServerResource;
import feri.rvir.elocator.rest.resource.user.UsersServerResource;
import feri.rvir.elocator.util.Crypto;


public class RestletApplication extends Application {
	
	@Override
	public Restlet createInboundRoot() {
		
		/*
		ChallengeAuthenticator guard=new ChallengeAuthenticator(getContext(), ChallengeScheme.HTTP_BASIC, "eLocator");
		DBVerifier verifier=new DBVerifier();
		guard.setVerifier(verifier);*/
		
		Router router=new Router(getContext());
		router.attach("/users",UsersServerResource.class);
		router.attach("/users/{username}",UserServerResource.class);
		router.attach("/users/{username}/{operation}",UserServerResource.class);
		router.attach("/users/{username}/tracking",TrackingServerResource.class);
		router.attach("/users/{username}/location/{timestamp}",LocationServerResource.class);
		
		//guard.setNext(router);
		//return guard;
		return router;
	}
	
	public class DBVerifier extends SecretVerifier {

		@Override
		public int verify(String username, char[] password) {
			String dbPassword=new String(password);
			if(!(username.equals("registrator")&&dbPassword.equals(Crypto.hash("ImkZh75dKLdo2MwEMByv", "SHA-1")))) {
				UserDao udao=new UserDao();
				List<User> users=(List<User>)udao.getAll();
				boolean credentialsMatch=false;
				for(User u:users) {
					if(u.getUsername().equals(username)) {
						String userPassword=u.getPassword();
						dbPassword=new String(password);
						if(userPassword.equals(dbPassword)) {
							credentialsMatch=true;
						}
					}
				}
				
				if(credentialsMatch) {
					return SecretVerifier.RESULT_VALID;
				} else {
					return SecretVerifier.RESULT_INVALID;
				}
			} else {
				return SecretVerifier.RESULT_VALID;
			}
		}
		
	}

}
