package org.cyk.utility.client.controller.entities.myentity;

import org.cyk.utility.client.controller.data.DataIdentifiedByStringAndCoded;

public interface MyEntity extends DataIdentifiedByStringAndCoded {

	@Override MyEntity setIdentifier(Object identifier);
	@Override MyEntity setCode(String code);
	
	String getName();
	MyEntity setName(String name);
	
	String getDescription();
	MyEntity setDescription(String description);
	
	/**/
	
	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_DESCRIPTION = "description";
	
}
