package org.cyk.utility.server.representation.api;

import javax.ws.rs.Path;

import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.entities.NodeHierarchyDto;

@Path(NodeHierarchyRepresentation.PATH)
public interface NodeHierarchyRepresentation extends RepresentationEntity<NodeHierarchyDto> {
	
	String PATH = "/nodehierarchy/";
	
}
