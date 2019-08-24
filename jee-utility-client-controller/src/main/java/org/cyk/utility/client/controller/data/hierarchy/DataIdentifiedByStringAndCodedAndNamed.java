package org.cyk.utility.client.controller.data.hierarchy;

public interface DataIdentifiedByStringAndCodedAndNamed<NODE> extends DataIdentifiedByStringAndCoded<NODE> {
	
	String getName();
	DataIdentifiedByStringAndCodedAndNamed<NODE> setName(String name);
	
	public static final String PROPERTY_NAME = "name";
	
}
