package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputString;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;

public abstract class AbstractDataIdentifiedByStringImpl extends AbstractDataImpl implements DataIdentifiedByString,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Input @InputString @InputStringLineOne
	@NotNull
	private String identifier;
	
	@Override
	public String getIdentifier() {
		return identifier;
	}
	
	@Override
	public DataIdentifiedByString setIdentifier(String identifier) {
		this.identifier = identifier;
		super.setIdentifier(this.identifier);
		return this;
	}

	@Override
	public String toString() {
		return getIdentifier();
	}
}
