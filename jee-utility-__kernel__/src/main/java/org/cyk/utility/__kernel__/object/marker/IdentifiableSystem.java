package org.cyk.utility.__kernel__.object.marker;

public interface IdentifiableSystem<IDENTIFIER> extends Identifiable<IDENTIFIER> {

	IDENTIFIER getSystemIdentifier();
	IdentifiableSystem<IDENTIFIER> setSystemIdentifier(IDENTIFIER identifier);
	
}
