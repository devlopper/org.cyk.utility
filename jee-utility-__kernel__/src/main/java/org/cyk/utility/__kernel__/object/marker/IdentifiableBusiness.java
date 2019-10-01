package org.cyk.utility.__kernel__.object.marker;

public interface IdentifiableBusiness<IDENTIFIER> extends Identifiable<IDENTIFIER> {

	IDENTIFIER getBusinessIdentifier();
	IdentifiableBusiness<IDENTIFIER> setBusinessIdentifier(IDENTIFIER identifier);
	
}
