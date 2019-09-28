package org.cyk.utility.__kernel__.object.__static__.representation;

import java.io.Serializable;

public abstract class AbstractCollectionOfIdentifiedByStringAndCodedAndNamedImpl<IDENTIFIED> extends AbstractCollectionOfIdentifiedByStringAndCodedImpl<IDENTIFIED> implements CollectionOfIdentifiedByStringAndCodedAndNamed<IDENTIFIED>,Serializable {
	private static final long serialVersionUID = 120190653223178348L;

	@Override
	public void writeName(IDENTIFIED identified, String name) {
		if(identified instanceof IdentifiedByStringAndCodedAndNamed)
			((IdentifiedByStringAndCodedAndNamed)identified).setName(name);
		else
			CollectionOfIdentifiedByStringAndCodedAndNamed.super.writeName(identified, name);
	}

}
