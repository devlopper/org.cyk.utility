package org.cyk.utility.server.representation.api;

import javax.ws.rs.Path;

import org.cyk.utility.server.persistence.entities.Node;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.entities.NodeDto;
import org.cyk.utility.server.representation.entities.NodeDtoCollection;

@Path(NodeRepresentation.PATH)
public interface NodeRepresentation extends RepresentationEntity<Node,NodeDto,NodeDtoCollection> {
	
	String PATH = "/node/";
	
}
