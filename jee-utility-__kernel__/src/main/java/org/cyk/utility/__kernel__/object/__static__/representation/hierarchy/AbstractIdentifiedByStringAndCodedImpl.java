package org.cyk.utility.__kernel__.object.__static__.representation.hierarchy;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public abstract class AbstractIdentifiedByStringAndCodedImpl<IDENTIFIED> extends AbstractIdentifiedByStringImpl<IDENTIFIED> implements IdentifiedByStringAndCoded<IDENTIFIED>,Serializable {
	private static final long serialVersionUID = 1L;

	private String code;

	@Override
	public String getCode() {
		return code;
	}
	
	@Override
	public IdentifiedByStringAndCoded<IDENTIFIED> setCode(String code) {
		this.code = code;
		return this;
	}
	
	@Override
	public IdentifiedByStringAndCoded<IDENTIFIED> setIdentifier(String identifier) {
		return (IdentifiedByStringAndCoded<IDENTIFIED>) super.setIdentifier(identifier);
	}
}
