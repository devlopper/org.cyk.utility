package org.cyk.utility.server.representation.api;

import javax.ws.rs.Path;

import org.cyk.utility.server.persistence.entities.ParentType;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.entities.ParentTypeDto;
import org.cyk.utility.server.representation.entities.ParentTypeDtoCollection;

@Path(ParentTypeRepresentation.PATH)
public interface ParentTypeRepresentation extends RepresentationEntity<ParentType,ParentTypeDto,ParentTypeDtoCollection> {
	
	String PATH = "/parenttypes/";
	
}
