package org.cyk.utility.__kernel__.object.__static__.representation;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;

import org.cyk.utility.__kernel__.object.marker.IdentifiableBusiness;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
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
	
	@Override @JsonbTransient @JsonIgnore
	public String getBusinessIdentifier() {
		return getCode();
	}
	
	@Override @JsonbTransient @JsonIgnore
	public IdentifiableBusiness<String> setBusinessIdentifier(String identifier) {
		setCode(identifier);
		return this;
	}
}
