package org.cyk.utility.server.representation.api;

import javax.ws.rs.Path;

import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.entities.ParentTypeDto;

@Path(ParentTypeRepresentation.PATH)
public interface ParentTypeRepresentation extends RepresentationEntity<ParentTypeDto> {
	
	String PATH = "/parenttypes/";
	
}
