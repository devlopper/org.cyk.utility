package org.cyk.utility.playground.server.representation.entities;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.cyk.utility.server.representation.hierarchy.AbstractNodeCodedAndNamed;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@XmlSeeAlso(NodeDtoCollection.class)
public class NodeDto extends AbstractNodeCodedAndNamed<NodeDto,NodeDtoCollection> implements Serializable {	
	private static final long serialVersionUID = 1L;
	
	@Override
	public NodeDto setIdentifier(String identifier) {
		return (NodeDto) super.setIdentifier(identifier);
	}
	
	@Override
	public NodeDto setCode(String code) {
		return (NodeDto) super.setCode(code);
	}
	
	@Override
	public NodeDto setName(String name) {
		return (NodeDto) super.setName(name);
	}
	
	@Override
	public String toString() {
		return getIdentifier()+"/"+getCode()+"/"+getName()+":"+getParents();
	}
}
