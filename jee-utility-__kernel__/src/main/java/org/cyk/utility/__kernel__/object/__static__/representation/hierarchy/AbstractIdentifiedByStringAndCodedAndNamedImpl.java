package org.cyk.utility.__kernel__.object.__static__.representation.hierarchy;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public abstract class AbstractIdentifiedByStringAndCodedAndNamedImpl<IDENTIFIED> extends AbstractIdentifiedByStringAndCodedImpl<IDENTIFIED> implements IdentifiedByStringAndCodedAndNamed<IDENTIFIED>,Serializable {
	private static final long serialVersionUID = 1L;

	private String name;

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public IdentifiedByStringAndCodedAndNamed<IDENTIFIED> setName(String name) {
		this.name = name;
		return this;
	}
	
	@Override
	public IdentifiedByStringAndCodedAndNamed<IDENTIFIED> setIdentifier(String identifier) {
		return (IdentifiedByStringAndCodedAndNamed<IDENTIFIED>) super.setIdentifier(identifier);
	}
	
	@Override
	public IdentifiedByStringAndCodedAndNamed<IDENTIFIED> setCode(String code) {
		return (IdentifiedByStringAndCodedAndNamed<IDENTIFIED>) super.setCode(code);
	}
}
