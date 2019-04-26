package org.cyk.utility.client.controller.entities.verysimpleentity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputString;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.client.controller.data.AbstractDataIdentifiedByStringAndCodedImpl;

public class VerySimpleEntityDetailsImpl extends AbstractDataIdentifiedByStringAndCodedImpl implements VerySimpleEntityDetails,Serializable {
	private static final long serialVersionUID = 1L;

	@Input @InputString @InputStringLineOne @NotNull
	private String address;
	
	@Override
	public VerySimpleEntityDetails setIdentifier(Object identifier) {
		return (VerySimpleEntityDetails) super.setIdentifier(identifier);
	}
	
	@Override
	public VerySimpleEntityDetails setCode(String code) {
		return (VerySimpleEntityDetails) super.setCode(code);
	}
	
	@Override
	public String getAddress() {
		return address;
	}
	
	@Override
	public VerySimpleEntityDetails setAddress(String address) {
		this.address = address;
		return this;
	}
	
	/**/
}
