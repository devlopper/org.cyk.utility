package org.cyk.utility.server.representation.api;

import javax.ws.rs.Path;

import org.cyk.utility.server.persistence.entities.Parent;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.entities.ParentDto;
import org.cyk.utility.server.representation.entities.ParentDtoCollection;

@Path(ParentRepresentation.PATH)
public interface ParentRepresentation extends RepresentationEntity<Parent,ParentDto,ParentDtoCollection> {
	
	String PATH = "/parents/";
	
}
