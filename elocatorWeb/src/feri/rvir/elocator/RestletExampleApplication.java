package feri.rvir.elocator;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class RestletExampleApplication extends Application {
	
	@Override
	public Restlet createInboundRoot() {
		Router router=new Router(getContext());
		router.attachDefault(RestletExampleResource.class);
		
		return router;
	}

}
