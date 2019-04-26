package org.cyk.utility.client.controller.data;

import java.io.Serializable;

public abstract class AbstractDataIdentifiedByStringImpl extends AbstractDataImpl implements DataIdentifiedByString,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getIdentifier() {
		return (String) super.getIdentifier();
	}

	@Override
	public String toString() {
		return getIdentifier();
	}
}
