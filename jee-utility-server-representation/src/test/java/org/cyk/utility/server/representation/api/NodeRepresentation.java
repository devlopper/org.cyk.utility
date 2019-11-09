package org.cyk.utility.server.representation.api;

import javax.ws.rs.Path;

import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.entities.NodeDto;

@Path(NodeRepresentation.PATH)
public interface NodeRepresentation extends RepresentationEntity<NodeDto> {
	
	String PATH = "/node/";
	
}
