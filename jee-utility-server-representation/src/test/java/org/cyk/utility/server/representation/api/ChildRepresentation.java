package org.cyk.utility.server.representation.api;

import javax.ws.rs.Path;

import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.entities.ChildDto;

@Path(ChildRepresentation.PATH)
public interface ChildRepresentation extends RepresentationEntity<ChildDto> {
	
	String PATH = "/children/";
	
}
