package org.cyk.utility.playground.server.representation.entities;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.playground.server.representation.entities.NodeDto;
import org.cyk.utility.playground.server.representation.entities.NodeHierarchyDto;
import org.cyk.utility.server.representation.hierarchy.AbstractHierarchy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class NodeHierarchyDto extends AbstractHierarchy<NodeDto> implements Serializable {	
	private static final long serialVersionUID = 1L;
	
	@Override
	public NodeHierarchyDto setParent(NodeDto parent) {
		return (NodeHierarchyDto) super.setParent(parent);
	}
	
	@Override
	public NodeHierarchyDto setChild(NodeDto child) {
		return (NodeHierarchyDto) super.setChild(child);
	}
	
}
