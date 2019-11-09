package org.cyk.utility.server.representation.api;

import javax.ws.rs.Path;

import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.entities.ParentDto;

@Path(ParentRepresentation.PATH)
public interface ParentRepresentation extends RepresentationEntity<ParentDto> {
	
	String PATH = "/parents/";
	
}
