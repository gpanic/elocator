package feri.rvir.elocator;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class RestletExampleResource extends ServerResource {
	
	@Get
	public String represent() {
		return "hello world (from the cloud)";
	}

}
