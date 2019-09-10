package org.cyk.utility.playground.client.controller.entities;

import org.cyk.utility.client.controller.data.DataIdentifiedByStringAndCoded;

public interface Person extends DataIdentifiedByStringAndCoded {

	String getFirstName();
	Person setFirstName(String firstName);
	
	String getLastNames();
	Person setLastNames(String lastNames);
	
	String PROPERTY_FIRST_NAME = "firstName";
	String PROPERTY_LAST_NAMES = "lastNames";
	
}
