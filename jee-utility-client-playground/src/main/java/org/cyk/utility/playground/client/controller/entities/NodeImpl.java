package org.cyk.utility.playground.client.controller.entities;

import java.io.Serializable;

import org.cyk.utility.client.controller.data.hierarchy.AbstractDataIdentifiedByStringAndCodedAndNamedImpl;

public class NodeImpl extends AbstractDataIdentifiedByStringAndCodedAndNamedImpl<Node> implements Node,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Node add__parents__(Node... __parents__) {
		return (Node) super.add__parents__(__parents__);
	}
	
	@Override
	public Node setIdentifier(Object identifier) {
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
}
