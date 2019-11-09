package org.cyk.utility.playground.server.representation.api;

import javax.ws.rs.Path;

import org.cyk.utility.playground.server.representation.entities.NodeDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(NodeRepresentation.PATH)
public interface NodeRepresentation extends RepresentationEntity<NodeDto> {
	
	String PATH = "/node/";
	
}
