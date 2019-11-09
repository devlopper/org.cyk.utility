package org.cyk.utility.playground.server.representation.api;

import javax.ws.rs.Path;

import org.cyk.utility.playground.server.representation.entities.NodeHierarchyDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(NodeHierarchyRepresentation.PATH)
public interface NodeHierarchyRepresentation extends RepresentationEntity<NodeHierarchyDto> {
	
	String PATH = "/nodehierarchy/";
	
}
