package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputString;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.__kernel__.string.StringHelper;

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
		return this;
	}

	@Override
	public String toString() {
		String string = getIdentifier();
		if(StringHelper.isBlank(string))
			string = super.toString();
		return string;
	}
}
