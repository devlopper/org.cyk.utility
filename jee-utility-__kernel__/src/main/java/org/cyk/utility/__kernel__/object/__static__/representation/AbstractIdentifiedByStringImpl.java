package org.cyk.utility.__kernel__.object.__static__.representation;

import java.io.Serializable;

public abstract class AbstractIdentifiedByStringImpl extends AbstractIdentifiedImpl<String> implements IdentifiedByString,Serializable {
	private static final long serialVersionUID = 1L;

	private String identifier;

	@Override
	public String getIdentifier() {
		return identifier;
	}
	
	@Override
	public IdentifiedByString setIdentifier(String identifier) {
		this.identifier = identifier;
		return this;
	}
}
