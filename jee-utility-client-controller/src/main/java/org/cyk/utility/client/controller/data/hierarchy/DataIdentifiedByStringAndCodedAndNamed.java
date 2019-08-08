package org.cyk.utility.client.controller.data.hierarchy;

public interface DataIdentifiedByStringAndCodedAndNamed extends DataIdentifiedByStringAndCoded {
	
	String getName();
	DataIdentifiedByStringAndCodedAndNamed setName(String name);
	
	public static final String PROPERTY_NAME = "name";
	
}
