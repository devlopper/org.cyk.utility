package org.cyk.utility.playground.client.controller.entities;

import org.cyk.utility.client.controller.data.DataIdentifiedByString;

public interface SelectedNode extends DataIdentifiedByString {

	Node getNode();
	SelectedNode setNode(Node node);
	
	String PROPERTY_NODE = "node";
}
