package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputString;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.string.StringHelper;

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
		String string = getName();
		if(__inject__(StringHelper.class).isBlank(string))
			string = super.toString();
		return string;
	}
	
}
