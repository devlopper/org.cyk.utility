package org.cyk.utility.server.representation.api;

import javax.ws.rs.Path;

import org.cyk.utility.server.persistence.entities.NodeHierarchy;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.entities.NodeHierarchyDto;
import org.cyk.utility.server.representation.entities.NodeHierarchyDtoCollection;

@Path(NodeHierarchyRepresentation.PATH)
public interface NodeHierarchyRepresentation extends RepresentationEntity<NodeHierarchy,NodeHierarchyDto,NodeHierarchyDtoCollection> {
	
	String PATH = "/nodehierarchy/";
	
}
