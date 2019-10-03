package org.cyk.utility.__kernel__.object.__static__.representation;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.marker.IdentifiableBusiness;

public abstract class AbstractIdentifiedByStringAndCodedImpl extends AbstractIdentifiedByStringImpl implements IdentifiedByStringAndCoded,IdentifiableBusiness<String>,Serializable {
	private static final long serialVersionUID = 1L;

	private String code;

	@Override
	public String getCode() {
		return code;
	}
	
	@Override
	public IdentifiedByStringAndCoded setCode(String code) {
		this.code = code;
		return this;
	}
	
	@Override
	public IdentifiedByStringAndCoded setIdentifier(String identifier) {
		return (IdentifiedByStringAndCoded) super.setIdentifier(identifier);
	}
	
	@Override
	public String getBusinessIdentifier() {
		return getCode();
	}
	
	@Override
	public IdentifiableBusiness<String> setBusinessIdentifier(String identifier) {
		setCode(identifier);
		return this;
	}
}
