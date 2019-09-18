package org.cyk.utility.playground.server.representation.entities;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.cyk.utility.server.representation.hierarchy.AbstractNodeCollection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@XmlRootElement @XmlSeeAlso(NodeDto.class)
//@JsonIgnoreProperties(ignoreUnknown=true)
public class NodeDtoCollection extends AbstractNodeCollection<NodeDto> implements Serializable {
	private static final long serialVersionUID = 1L;

	
}
