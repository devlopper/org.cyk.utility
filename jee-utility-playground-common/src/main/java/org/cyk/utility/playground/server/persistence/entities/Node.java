package org.cyk.utility.playground.server.persistence.entities;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.cyk.utility.server.persistence.jpa.hierarchy.AbstractIdentifiedByStringAndCodedAndNamed;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity @Getter @Setter @Accessors(chain=true) @Access(AccessType.FIELD)
public class Node extends AbstractIdentifiedByStringAndCodedAndNamed<Node,Nodes> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Node addParents(Node... children) {
		return (Node) super.addParents(children);
	}
	
	@Override
	public Node setIdentifier(String identifier) {
		return (Node) super.setIdentifier(identifier);
	}
	
	@Override
	public Node setCode(String code) {
		return (Node) super.setCode(code);
	}
	
	@Override
	public Node setName(String name) {
		return (Node) super.setName(name);
	}
	
	@Override
	public String toString() {
		return identifier+"/"+code+"/"+name+":"+parents;
	}
}
