package org.cyk.utility.client.controller.data;

public interface DataIdentifiedByString extends Data {
	
	String getIdentifier();

	public static final String PROPERTY_IDENTIFIER = "identifier";
	
}
