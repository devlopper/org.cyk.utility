package org.cyk.utility.server.representation.api;

import javax.ws.rs.Path;

import org.cyk.utility.server.persistence.entities.ParentChild;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.entities.ParentChildDto;
import org.cyk.utility.server.representation.entities.ParentChildDtoCollection;

@Path(ParentChildRepresentation.PATH)
public interface ParentChildRepresentation extends RepresentationEntity<ParentChild,ParentChildDto,ParentChildDtoCollection> {
	
	String PATH = "/parentchild/";
	
}
