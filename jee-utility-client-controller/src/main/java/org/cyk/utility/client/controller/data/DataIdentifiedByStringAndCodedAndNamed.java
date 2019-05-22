package org.cyk.utility.client.controller.data;

public interface DataIdentifiedByStringAndCodedAndNamed extends DataIdentifiedByStringAndCoded {
	
	String getName();
	DataIdentifiedByStringAndCodedAndNamed setName(String name);
	
	public static final String PROPERTY_NAME = "name";
	
}
