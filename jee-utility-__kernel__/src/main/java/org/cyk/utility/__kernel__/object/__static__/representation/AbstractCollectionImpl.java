package org.cyk.utility.__kernel__.object.__static__.representation;

import java.io.Serializable;

public abstract class AbstractCollectionImpl<ELEMENT> extends org.cyk.utility.__kernel__.object.AbstractObject implements Collection<ELEMENT>,Serializable {
	private static final long serialVersionUID = 120190653223178348L;

	@Override
	public String toString() {
		java.util.Collection<ELEMENT> elements = getElements();
		return elements == null ? null : elements.toString();
	}
}
