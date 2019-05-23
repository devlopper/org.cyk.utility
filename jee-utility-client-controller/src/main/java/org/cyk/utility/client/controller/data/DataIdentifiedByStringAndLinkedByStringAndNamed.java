package org.cyk.utility.client.controller.data;

public interface DataIdentifiedByStringAndLinkedByStringAndNamed extends DataIdentifiedByStringAndLinkedByString {
	
	String getName();
	DataIdentifiedByStringAndLinkedByStringAndNamed setName(String name);
	
	public static final String PROPERTY_NAME = "name";
	
}
