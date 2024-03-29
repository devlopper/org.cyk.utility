package org.cyk.utility.server.representation.entities;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.__kernel__.object.__static__.representation.hierarchy.AbstractIdentifiedByStringAndCodedAndNamedImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class NodeDto extends AbstractIdentifiedByStringAndCodedAndNamedImpl<NodeDto> implements Serializable {	
	private static final long serialVersionUID = 1L;

	private ArrayList<NodeDto> parents;
	private ArrayList<NodeDto> children;
	
	@Override
	public NodeDto setIdentifier(String identifier) {
		return (NodeDto) super.setIdentifier(identifier);
	}
	
	@Override
	public NodeDto setName(String name) {
		return (NodeDto) super.setName(name);
	}
	
	@Override
	public NodeDto setCode(String code) {
		return (NodeDto) super.setCode(code);
	}
	
	@Override
	public NodeDto addParents(NodeDto... parents) {
		return (NodeDto) super.addParents(parents);
	}
	
	@Override
	public NodeDto addChildren(NodeDto... children) {
		return (NodeDto) super.addChildren(children);
	}
	
}
