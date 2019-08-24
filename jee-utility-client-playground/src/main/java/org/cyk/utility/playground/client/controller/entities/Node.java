package org.cyk.utility.playground.client.controller.entities;

import org.cyk.utility.client.controller.data.hierarchy.DataIdentifiedByStringAndCodedAndNamed;

public interface Node extends DataIdentifiedByStringAndCodedAndNamed<Node> {

	@Override Node setIdentifier(Object identifier);
	@Override Node setCode(String code);
	@Override Node setName(String name);
	
	@Override Node add__parents__(Node... __parents__);
}
