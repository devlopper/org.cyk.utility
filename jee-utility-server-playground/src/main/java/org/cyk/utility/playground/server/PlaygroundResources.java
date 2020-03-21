package org.cyk.utility.playground.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("pgr")
public class PlaygroundResources {

	@Path("hi")
	@GET
	public Response sayHi(String message) {
		return Response.ok("Hi jesus!").build();
	}
	
}