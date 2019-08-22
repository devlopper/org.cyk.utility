package org.cyk.utility.playground.client.controller.entities;

import org.cyk.utility.client.controller.data.DataIdentifiedByStringAndCodedAndNamed;
import org.cyk.utility.playground.client.controller.entities.MyEntity;

public interface MyEntity extends DataIdentifiedByStringAndCodedAndNamed {

	@Override MyEntity setIdentifier(Object identifier);
	@Override MyEntity setCode(String code);
	@Override MyEntity setName(String name);
}
