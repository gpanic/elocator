package feri.rvir.elocator.rest;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.data.ChallengeScheme;
import org.restlet.routing.Router;
import org.restlet.security.ChallengeAuthenticator;
import org.restlet.security.MapVerifier;

import feri.rvir.elocator.rest.resource.location.LocationServerResource;
import feri.rvir.elocator.rest.resource.tracking.TrackingServerResource;
import feri.rvir.elocator.rest.resource.user.UserServerResource;
import feri.rvir.elocator.rest.resource.user.UsersServerResource;


public class RestletApplication extends Application {
	
	@Override
	public Restlet createInboundRoot() {
		
		ChallengeAuthenticator guard=new ChallengeAuthenticator(getContext(), ChallengeScheme.HTTP_BASIC, "eLocator");
		MapVerifier verifier=new MapVerifier();
		verifier.getLocalSecrets().put("tests", "testw".toCharArray());
		guard.setVerifier(verifier);
		
		Router router=new Router(getContext());
		router.attach("/users",UsersServerResource.class);
		router.attach("/users/{username}",UserServerResource.class);
		router.attach("/users/{username}/{operation}",UserServerResource.class);
		router.attach("/users/{username}/tracking",TrackingServerResource.class);
		router.attach("/users/{username}/location/{timestamp}",LocationServerResource.class);
		
		guard.setNext(router);
		return guard;
	}

}
