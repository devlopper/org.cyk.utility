package org.cyk.utility.__kernel__.object.marker;

/**
 * Capable of being business identified.
 * @author CYK
 *
 * @param <IDENTIFIER>
 */
public interface IdentifiableBusiness<IDENTIFIER> extends Identifiable<IDENTIFIER> {

	IDENTIFIER getBusinessIdentifier();
	IdentifiableBusiness<IDENTIFIER> setBusinessIdentifier(IDENTIFIER identifier);
	
}
