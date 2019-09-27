package org.cyk.utility.__kernel__.object.__static__.representation;

import org.cyk.utility.__kernel__.field.FieldHelper;

public interface Identified<IDENTIFIER> extends Object {

	@SuppressWarnings("unchecked")
	default IDENTIFIER getIdentifier() {
		return (IDENTIFIER) FieldHelper.read(this, PROPERTY_IDENTIFIER);
	}
	
	default Identified<IDENTIFIER> setIdentifier(IDENTIFIER identifier) {
		FieldHelper.write(this, PROPERTY_IDENTIFIER, identifier);
		return this;
	}
	
	/**/
	
	String PROPERTY_IDENTIFIER = "identifier";
}
