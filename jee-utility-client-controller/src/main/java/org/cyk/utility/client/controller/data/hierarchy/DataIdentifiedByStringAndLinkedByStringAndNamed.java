package org.cyk.utility.client.controller.data.hierarchy;

public interface DataIdentifiedByStringAndLinkedByStringAndNamed<NODE> extends DataIdentifiedByStringAndLinkedByString<NODE> {
	
	String getName();
	DataIdentifiedByStringAndLinkedByStringAndNamed<NODE> setName(String name);
	
	public static final String PROPERTY_NAME = "name";
	
}
