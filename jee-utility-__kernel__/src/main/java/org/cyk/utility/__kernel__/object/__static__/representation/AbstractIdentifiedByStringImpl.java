package org.cyk.utility.__kernel__.object.__static__.representation;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.marker.IdentifiableSystem;

public abstract class AbstractIdentifiedByStringImpl extends AbstractIdentifiedImpl<String> implements IdentifiedByString,IdentifiableSystem<String>,Serializable {
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
	
	@Override
	public String getSystemIdentifier() {
		return getIdentifier();
	}
	
	@Override
	public IdentifiableSystem<String> setSystemIdentifier(String identifier) {
		setIdentifier(identifier);
		return this;
	}
}
