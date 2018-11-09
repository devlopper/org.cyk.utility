package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.crud;

import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.client.controller.data.Data;

public interface MyEntityData extends Data {

	String getCode();
	MyEntityData setCode(String code);
	
	String getName();
	MyEntityData setName(String name);
	
	String getDescription();
	MyEntityData setDescription(String description);
	
	/**/
	
	public static final String PROPERTY_CODE = "code";
	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_DESCRIPTION = "description";
	
	/**/
	
	Collection<MyEntityData> COLLECTION = new ArrayList<>();
}
