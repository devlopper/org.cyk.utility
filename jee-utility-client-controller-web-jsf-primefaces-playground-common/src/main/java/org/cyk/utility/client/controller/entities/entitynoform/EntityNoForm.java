package org.cyk.utility.client.controller.entities.entitynoform;

import org.cyk.utility.client.controller.data.Data;

public interface EntityNoForm extends Data {

	@Override EntityNoForm setIdentifier(Object identifier);
	@Override EntityNoForm setCode(String code);
	
	String getName();
	EntityNoForm setName(String name);
	
	String getDescription();
	EntityNoForm setDescription(String description);
	
	/**/
	
	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_DESCRIPTION = "description";
	
}
