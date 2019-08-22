package org.cyk.utility.playground.server.representation.api;

import javax.ws.rs.Path;

import org.cyk.utility.playground.server.persistence.entities.Node;
import org.cyk.utility.playground.server.representation.entities.NodeDto;
import org.cyk.utility.playground.server.representation.entities.NodeDtoCollection;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.playground.server.representation.api.NodeRepresentation;

@Path(NodeRepresentation.PATH)
public interface NodeRepresentation extends RepresentationEntity<Node,NodeDto,NodeDtoCollection> {
	
	String PATH = "/node/";
	
}
