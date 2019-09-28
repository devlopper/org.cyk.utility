package org.cyk.utility.__kernel__.object.__static__.representation;

import java.io.Serializable;

public abstract class AbstractCollectionOfIdentifiedByStringAndCodedImpl<IDENTIFIED> extends AbstractCollectionOfIdentifiedByStringImpl<IDENTIFIED> implements CollectionOfIdentifiedByStringAndCoded<IDENTIFIED>,Serializable {
	private static final long serialVersionUID = 120190653223178348L;
	
	@Override
	public void writeCode(IDENTIFIED identified, String code) {
		if(identified instanceof IdentifiedByStringAndCoded)
			((IdentifiedByStringAndCoded)identified).setCode(code);
		else
			CollectionOfIdentifiedByStringAndCoded.super.writeCode(identified, code);
	}

}
