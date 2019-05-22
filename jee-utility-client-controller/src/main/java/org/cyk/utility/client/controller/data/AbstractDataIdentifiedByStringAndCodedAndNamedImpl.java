package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputString;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;

public abstract class AbstractDataIdentifiedByStringAndCodedAndNamedImpl extends AbstractDataIdentifiedByStringAndCodedImpl implements DataIdentifiedByStringAndCodedAndNamed,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Input @InputString @InputStringLineOne
	@NotNull
	private String name;
	
	@Override
	public DataIdentifiedByStringAndCodedAndNamed setName(String name) {
		this.name = name;
		return this;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
}
