package org.cyk.utility.client.controller.data;

public interface DataIdentifiedByStringAndCoded extends DataIdentifiedByString {
	
	String getCode();
	Data setCode(String code);
	
	public static final String PROPERTY_CODE = "code";
	
}
