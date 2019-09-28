package org.cyk.utility.__kernel__.object.__static__.representation;

import java.io.Serializable;

public abstract class AbstractCollectionOfIdentifiedByStringImpl<IDENTIFIED> extends AbstractCollectionOfIdentifiedImpl<IDENTIFIED,String> implements CollectionOfIdentifiedByString<IDENTIFIED>,Serializable {
	private static final long serialVersionUID = 120190653223178348L;

	@Override
	public void writeIdentifier(IDENTIFIED identified, String identifier) {
		if(identified instanceof Identified)
			((IdentifiedByString) identified).setIdentifier(identifier);
		else
			CollectionOfIdentifiedByString.super.writeIdentifier(identified, identifier);
	}
	
}
