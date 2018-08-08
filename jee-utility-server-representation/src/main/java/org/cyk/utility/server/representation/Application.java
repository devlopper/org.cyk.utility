package org.cyk.utility.server.representation;

@javax.ws.rs.ApplicationPath("/")
@javax.ws.rs.Path("/")
@javax.enterprise.context.ApplicationScoped
public class Application extends javax.ws.rs.core.Application {

	@javax.ws.rs.GET
	@javax.ws.rs.Path("/")
	@javax.ws.rs.Produces(javax.ws.rs.core.MediaType.TEXT_PLAIN)
	public String getRootResource() {
		return "Hi from workflow system server. Time is "+new java.util.Date();
	}
	
}