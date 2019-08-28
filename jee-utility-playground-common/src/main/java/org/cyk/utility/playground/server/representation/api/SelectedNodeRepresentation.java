package org.cyk.utility.playground.server.representation.api;

import javax.ws.rs.Path;

import org.cyk.utility.playground.server.persistence.entities.SelectedNode;
import org.cyk.utility.playground.server.representation.entities.SelectedNodeDto;
import org.cyk.utility.playground.server.representation.entities.SelectedNodeDtoCollection;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.playground.server.representation.api.SelectedNodeRepresentation;

@Path(SelectedNodeRepresentation.PATH)
public interface SelectedNodeRepresentation extends RepresentationEntity<SelectedNode,SelectedNodeDto,SelectedNodeDtoCollection> {
	
	String PATH = "/selectednode";
	
}
