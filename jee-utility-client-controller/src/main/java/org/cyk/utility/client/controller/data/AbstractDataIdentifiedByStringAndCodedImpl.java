package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputString;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;

public abstract class AbstractDataIdentifiedByStringAndCodedImpl extends AbstractDataIdentifiedByStringImpl implements DataIdentifiedByStringAndCoded,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Input @InputString @InputStringLineOne
	@NotNull
	private String code;
	
	@Override
	public Data setCode(String code) {
		this.code = code;
		return this;
	}
	
	@Override
	public String getCode() {
		return code;
	}
	
	@Override
	public String toString() {
		return getCode();
	}
	
}
