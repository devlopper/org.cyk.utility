package org.cyk.utility.client.controller.data.hierarchy;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputString;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.string.StringHelper;

public abstract class AbstractDataIdentifiedByStringAndCodedImpl<NODE> extends AbstractDataIdentifiedByStringImpl<NODE> implements DataIdentifiedByStringAndCoded<NODE>,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Input @InputString @InputStringLineOne
	@NotNull
	private String code;
	
	@Override
	public DataIdentifiedByStringAndCoded<NODE> setCode(String code) {
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
		if(__inject__(StringHelper.class).isBlank(string))
			string = super.toString();
		return string;
	}
	
}
