package org.cyk.utility.__kernel__.object.__static__.representation;

import org.cyk.utility.__kernel__.field.FieldHelper;

public interface IdentifiedByStringAndCodedAndNamed extends IdentifiedByStringAndCoded {

	default String getName() {
		return (String) FieldHelper.read(this, PROPERTY_NAME);
	}
	
	default IdentifiedByStringAndCodedAndNamed setName(String name) {
		FieldHelper.write(this, PROPERTY_NAME, name);
		return this;
	}
	
	/**/
	
	String PROPERTY_NAME = "name";
	
}
