package org.cyk.utility.client.controller.data.hierarchy;

public interface DataIdentifiedByStringAndCoded extends DataIdentifiedByString {
	
	String getCode();
	DataIdentifiedByStringAndCoded setCode(String code);
	
	public static final String PROPERTY_CODE = "code";
	
}
