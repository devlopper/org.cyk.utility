package org.cyk.utility.playground.client.controller.entities;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputString;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.client.controller.data.AbstractDataIdentifiedByStringAndCodedImpl;

public class PersonImpl extends AbstractDataIdentifiedByStringAndCodedImpl implements Person,Serializable {
	private static final long serialVersionUID = 1L;

	@Input @InputString @InputStringLineOne private String firstName;
	@Input @InputString @InputStringLineOne private String lastNames;
	
	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public Person setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	@Override
	public String getLastNames() {
		return lastNames;
	}

	@Override
	public Person setLastNames(String lastNames) {
		this.lastNames = lastNames;
		return this;
	}

}
