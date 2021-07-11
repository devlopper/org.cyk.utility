package org.cyk.utility.representation.entity;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;

import org.cyk.utility.__kernel__.object.__static__.representation.IdentifiedByString;
import org.cyk.utility.__kernel__.object.marker.IdentifiableSystem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
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
	
	@Override @JsonbTransient @JsonIgnore
	public String getSystemIdentifier() {
		return getIdentifier();
	}
	
	@Override @JsonbTransient @JsonIgnore
	public IdentifiableSystem<String> setSystemIdentifier(String identifier) {
		setIdentifier(identifier);
		return this;
	}
}
