package org.cyk.utility.__kernel__.object.__static__.representation;

import java.io.Serializable;

public abstract class AbstractIdentifiedByStringAndCodedAndNamedImpl extends AbstractIdentifiedByStringAndCodedImpl implements IdentifiedByStringAndCodedAndNamed,Serializable {
	private static final long serialVersionUID = 1L;

	private String name;

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public IdentifiedByStringAndCodedAndNamed setName(String name) {
		this.name = name;
		return this;
	}
	
	@Override
	public IdentifiedByStringAndCodedAndNamed setIdentifier(String identifier) {
		return (IdentifiedByStringAndCodedAndNamed) super.setIdentifier(identifier);
	}
	
	@Override
	public IdentifiedByStringAndCodedAndNamed setCode(String code) {
		return (IdentifiedByStringAndCodedAndNamed) super.setCode(code);
	}
}
