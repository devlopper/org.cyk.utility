package org.cyk.utility.server.representation.entities;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.cyk.utility.__kernel__.object.__static__.representation.AbstractCollectionOfIdentifiedByStringImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@XmlSeeAlso(NodeHierarchyDto.class)
public class NodeHierarchyDtoCollection extends AbstractCollectionOfIdentifiedByStringImpl<NodeHierarchyDto> implements Serializable {
	private static final long serialVersionUID = 1L;

	
}
