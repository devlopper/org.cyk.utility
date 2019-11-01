package org.cyk.utility.__kernel__.object.marker;

/**
 * Capable of being system identified.
 * @author CYK
 *
 * @param <IDENTIFIER>
 */
public interface IdentifiableSystem<IDENTIFIER> extends Identifiable<IDENTIFIER> {

	IDENTIFIER getSystemIdentifier();
	IdentifiableSystem<IDENTIFIER> setSystemIdentifier(IDENTIFIER identifier);
	
}
