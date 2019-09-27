package org.cyk.utility.__kernel__.object.__static__.representation;

import org.cyk.utility.__kernel__.field.FieldHelper;

public interface IdentifiedByStringAndCoded extends IdentifiedByString {

	default String getCode() {
		return (String) FieldHelper.read(this, PROPERTY_CODE);
	}
	
	default IdentifiedByStringAndCoded setCode(String code) {
		FieldHelper.write(this, PROPERTY_CODE, code);
		return this;
	}
	
	/**/
	
	String PROPERTY_CODE = "code";
	
}
