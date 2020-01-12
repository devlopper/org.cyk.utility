package org.cyk.utility.playground.server.representation.api;

import javax.ws.rs.Path;

import org.cyk.utility.playground.server.representation.entities.NamableDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(NamableRepresentation.PATH)
public interface NamableRepresentation extends RepresentationEntity<NamableDto> {
	
	String PATH = "/namable";
	
}
