package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputString;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.__kernel__.string.StringHelper;

public abstract class AbstractDataIdentifiedByStringAndCodedImpl extends AbstractDataIdentifiedByStringImpl implements DataIdentifiedByStringAndCoded,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Input @InputString @InputStringLineOne
	@NotNull
	private String code;
	
	@Override
	public DataIdentifiedByStringAndCoded setCode(String code) {
		this.code = code;
		return this;
	}
	
	@Override
	public String getCode() {
		return code;
	}
	
	@Override
	public String toString() {
		String string = getCode();
		if(StringHelper.isBlank(string))
			string = super.toString();
		return string;
	}
	
}
