package org.cyk.utility.__kernel__.object.__static__.representation;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;

import org.cyk.utility.__kernel__.object.marker.IdentifiableBusiness;
import org.cyk.utility.__kernel__.object.marker.IdentifiableSystem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
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
	
	@Override @JsonbTransient @JsonIgnore
	public String getSystemIdentifier() {
		return super.getSystemIdentifier();
	}
	
	@Override @JsonbTransient @JsonIgnore
	public IdentifiableSystem<String> setSystemIdentifier(String identifier) {
		return super.setSystemIdentifier(identifier);
	}
	
	@Override @JsonbTransient @JsonIgnore
	public String getBusinessIdentifier() {
		return super.getBusinessIdentifier();
	}
	
	@Override @JsonbTransient @JsonIgnore
	public IdentifiableBusiness<String> setBusinessIdentifier(String identifier) {
		return super.setBusinessIdentifier(identifier);
	}
}
