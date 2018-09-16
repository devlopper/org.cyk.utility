package org.cyk.utility.server.representation;

import javax.ws.rs.Path;

@Path("/myrep")
public interface MyRepresentation extends RepresentationGeneric<MyEntityDto> {

}
