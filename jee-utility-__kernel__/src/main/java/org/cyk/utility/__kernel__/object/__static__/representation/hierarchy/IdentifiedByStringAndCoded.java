package org.cyk.utility.__kernel__.object.__static__.representation.hierarchy;

import org.cyk.utility.__kernel__.field.FieldHelper;

public interface IdentifiedByStringAndCoded<IDENTIFIED> extends IdentifiedByString<IDENTIFIED> {

	default String getCode() {
		return (String) FieldHelper.read(this, PROPERTY_CODE);
	}
	
	default IdentifiedByStringAndCoded<IDENTIFIED> setCode(String code) {
		FieldHelper.write(this, PROPERTY_CODE, code);
		return this;
	}
	
	/**/
	
	String PROPERTY_CODE = "code";
	
}
