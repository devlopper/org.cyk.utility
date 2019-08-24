package org.cyk.utility.playground.server.representation.api;

import javax.ws.rs.Path;

import org.cyk.utility.playground.server.persistence.entities.NodeHierarchy;
import org.cyk.utility.playground.server.representation.entities.NodeHierarchyDto;
import org.cyk.utility.playground.server.representation.entities.NodeHierarchyDtoCollection;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.playground.server.representation.api.NodeHierarchyRepresentation;

@Path(NodeHierarchyRepresentation.PATH)
public interface NodeHierarchyRepresentation extends RepresentationEntity<NodeHierarchy,NodeHierarchyDto,NodeHierarchyDtoCollection> {
	
	String PATH = "/nodehierarchy/";
	
}
