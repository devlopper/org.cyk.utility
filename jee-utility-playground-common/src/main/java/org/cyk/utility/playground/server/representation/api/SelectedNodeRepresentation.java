package org.cyk.utility.playground.server.representation.api;

import javax.ws.rs.Path;

import org.cyk.utility.playground.server.representation.entities.SelectedNodeDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(SelectedNodeRepresentation.PATH)
public interface SelectedNodeRepresentation extends RepresentationEntity<SelectedNodeDto> {
	
	String PATH = "/selectednode";
	
}
