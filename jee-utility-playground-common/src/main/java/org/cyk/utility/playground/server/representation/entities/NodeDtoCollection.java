package org.cyk.utility.playground.server.representation.entities;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.cyk.utility.__kernel__.object.__static__.representation.AbstractCollectionOfIdentifiedByStringAndCodedAndNamedImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@XmlRootElement @XmlSeeAlso(NodeDto.class)
public class NodeDtoCollection extends AbstractCollectionOfIdentifiedByStringAndCodedAndNamedImpl<NodeDto> implements Serializable {
	private static final long serialVersionUID = 1L;

	private ArrayList<NodeDto> elements;
	
}
