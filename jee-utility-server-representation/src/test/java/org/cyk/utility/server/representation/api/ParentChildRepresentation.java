package org.cyk.utility.server.representation.api;

import javax.ws.rs.Path;

import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.entities.ParentChildDto;

@Path(ParentChildRepresentation.PATH)
public interface ParentChildRepresentation extends RepresentationEntity<ParentChildDto> {
	
	String PATH = "/parentchild/";
	
}
