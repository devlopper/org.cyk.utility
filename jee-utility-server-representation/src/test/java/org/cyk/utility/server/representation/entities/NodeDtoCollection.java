package org.cyk.utility.server.representation.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@XmlSeeAlso(NodeDto.class)
public class NodeDtoCollection implements org.cyk.utility.__kernel__.object.__static__.representation.CollectionOfIdentifiedByStringAndCodedAndNamed<NodeDto>,Serializable {
	private static final long serialVersionUID = 1L;

	private Class<NodeDto> elementClass;
	private Collection<NodeDto> elements;
	
}
