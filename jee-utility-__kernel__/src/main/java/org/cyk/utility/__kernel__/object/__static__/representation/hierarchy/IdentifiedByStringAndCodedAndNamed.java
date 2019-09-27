package org.cyk.utility.__kernel__.object.__static__.representation.hierarchy;

import org.cyk.utility.__kernel__.field.FieldHelper;

public interface IdentifiedByStringAndCodedAndNamed<IDENTIFIED> extends IdentifiedByStringAndCoded<IDENTIFIED> {

	default String getName() {
		return (String) FieldHelper.read(this, PROPERTY_NAME);
	}
	
	default IdentifiedByStringAndCodedAndNamed<IDENTIFIED> setName(String name) {
		FieldHelper.write(this, PROPERTY_NAME, name);
		return this;
	}
	
	/**/
	
	String PROPERTY_NAME = "name";
	
}
