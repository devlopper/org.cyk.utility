package org.cyk.utility.persistence.entity;

import org.cyk.utility.__kernel__.field.FieldHelper;

public interface IdentifiableSystemScalarString {

	String getIdentifier();
	IdentifiableSystemScalarString setIdentifier(String identifier);
	
	default IdentifiableSystemScalarString copyStringFromStrings(String stringFieldName,String stringsFieldName) {
		FieldHelper.copyStringFromStrings(this, stringFieldName, stringsFieldName);
		return this;
	}
	
	default IdentifiableSystemScalarString copyStringFromStrings(String stringFieldName) {
		FieldHelper.copyStringFromStrings(this, stringFieldName);
		return this;
	}
}