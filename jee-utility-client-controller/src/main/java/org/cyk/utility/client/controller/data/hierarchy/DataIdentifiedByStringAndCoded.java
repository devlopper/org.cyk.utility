package org.cyk.utility.client.controller.data.hierarchy;

public interface DataIdentifiedByStringAndCoded<NODE> extends DataIdentifiedByString<NODE> {
	
	String getCode();
	DataIdentifiedByStringAndCoded<NODE> setCode(String code);
	
	public static final String PROPERTY_CODE = "code";
	
}
