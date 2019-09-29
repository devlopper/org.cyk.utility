package org.cyk.utility.__kernel__.object.__static__.representation;

import java.io.Serializable;

public abstract class AbstractCollectionOfIdentifiedImpl<IDENTIFIED,IDENTIFIER> extends AbstractCollectionImpl<IDENTIFIED> implements CollectionOfIdentified<IDENTIFIED,IDENTIFIER>,Serializable {
	private static final long serialVersionUID = 120190653223178348L;

	@SuppressWarnings("unchecked")
	@Override
	public void writeIdentifier(IDENTIFIED identified, IDENTIFIER identifier) {
		if(identified instanceof Identified)
			((Identified<IDENTIFIER>) identified).setIdentifier(identifier);
		else
			CollectionOfIdentified.super.writeIdentifier(identified, identifier);
	}
}
