package org.cyk.utility.playground.server.representation.entities;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.cyk.utility.playground.server.representation.entities.NodeDto;
import org.cyk.utility.server.representation.hierarchy.AbstractNodeCollection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@XmlRootElement @XmlSeeAlso(NodeDto.class)
public class NodeDtoCollection extends AbstractNodeCollection<NodeDto> implements Serializable {
	private static final long serialVersionUID = 1L;

	
}
