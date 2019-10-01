package org.cyk.utility.__kernel__.object.__static__.controller;

public interface Identified<IDENTIFIER> extends Object {

	IDENTIFIER getIdentifier();
	Identified<IDENTIFIER> setIdentifier(IDENTIFIER identifier);
	
}
