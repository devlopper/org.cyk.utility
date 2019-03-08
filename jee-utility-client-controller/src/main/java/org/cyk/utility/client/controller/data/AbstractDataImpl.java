package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.cyk.utility.client.controller.AbstractObject;
import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputString;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;

public abstract class AbstractDataImpl extends AbstractObject implements Data,Serializable {
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
	
	//can be upper , Dynamic Object
	@Override
	public boolean equals(Object object) {
		if(object instanceof Data && object.getClass().equals(getClass()) ) {
			Object identifier01 = getIdentifier();
			Object identifier02 = ((Data)object).getIdentifier();
			return identifier01!=null && identifier02!=null && identifier01.equals(identifier02);
		}
		return super.equals(object);
	}
	
	@Override
	public String toString() {
		return getCode();
	}
}
