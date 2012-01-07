package feri.rvir.elocator.rest;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import feri.rvir.elocator.rest.resource.user.UserServerResource;


public class RestletApplication extends Application {
	
	@Override
	public Restlet createInboundRoot() {
		Router router=new Router(getContext());
		router.attach("/user",UserServerResource.class);
		return router;
		//comment
	}

}
