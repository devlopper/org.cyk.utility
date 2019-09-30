package org.cyk.utility.playground.server.representation.entities;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.__kernel__.object.__static__.representation.hierarchy.AbstractHierarchyByStringImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class NodeHierarchyDto extends AbstractHierarchyByStringImpl<NodeDto> implements Serializable {	
	private static final long serialVersionUID = 1L;
	
	private NodeDto parent;
	private NodeDto child;
	
}
