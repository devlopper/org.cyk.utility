package org.cyk.utility.server.representation.api;

import javax.ws.rs.Path;

import org.cyk.utility.server.persistence.entities.Child;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.entities.ChildDto;
import org.cyk.utility.server.representation.entities.ChildDtoCollection;

@Path(ChildRepresentation.PATH)
public interface ChildRepresentation extends RepresentationEntity<Child,ChildDto,ChildDtoCollection> {
	
	String PATH = "/children/";
	
}
