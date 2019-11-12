package org.cyk.utility.__kernel__.object.__static__.identifiable;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.marker.IdentifiableSystem;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public abstract class AbstractIdentifiedPersistableByString extends AbstractIdentifiedPersistable<String> implements IdentifiedPersistableByString,IdentifiableSystem<String>,Serializable {
	private static final long serialVersionUID = 1L;
	
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