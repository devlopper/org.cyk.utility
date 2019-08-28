package org.cyk.utility.playground.client.controller.entities;

import java.io.Serializable;

import org.cyk.utility.client.controller.data.AbstractDataIdentifiedByStringImpl;

public class SelectedNodeImpl extends AbstractDataIdentifiedByStringImpl implements SelectedNode,Serializable {
	private static final long serialVersionUID = 1L;

	private Node node;
	
	@Override
	public Node getNode() {
		return node;
	}
	
	@Override
	public SelectedNode setNode(Node node) {
		this.node = node;
		return this;
	}
}
