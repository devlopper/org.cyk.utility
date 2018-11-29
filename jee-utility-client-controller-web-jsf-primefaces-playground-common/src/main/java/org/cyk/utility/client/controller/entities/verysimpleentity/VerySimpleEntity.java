package org.cyk.utility.client.controller.entities.verysimpleentity;

import org.cyk.utility.client.controller.data.Data;

public interface VerySimpleEntity extends Data {

	@Override VerySimpleEntity setIdentifier(Object identifier);
	@Override VerySimpleEntity setCode(String code);
	
	String getName();
	VerySimpleEntity setName(String name);
	
	String getDescription();
	VerySimpleEntity setDescription(String description);
	
	/**/
	
	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_DESCRIPTION = "description";

}
