package org.cyk.utility.playground.client.controller.entities;

import org.cyk.utility.client.controller.data.DataIdentifiedByStringAndCodedAndNamed;

public interface Node extends DataIdentifiedByStringAndCodedAndNamed {

	@Override Node setIdentifier(Object identifier);
	@Override Node setCode(String code);
	@Override Node setName(String name);
}
