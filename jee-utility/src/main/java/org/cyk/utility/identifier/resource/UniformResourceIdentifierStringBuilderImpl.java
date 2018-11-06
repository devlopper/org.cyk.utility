package org.cyk.utility.identifier.resource;

import java.io.Serializable;

import org.cyk.utility.string.AbstractStringFunctionImpl;

public class UniformResourceIdentifierStringBuilderImpl extends AbstractStringFunctionImpl implements UniformResourceIdentifierStringBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		getFormat(Boolean.TRUE).setValue(FORMAT);
	}

	@Override
	public UniformResourceIdentifierStringBuilder setFormatArguments(Object... arguments) {
		getFormat(Boolean.TRUE).setArguments(arguments);
		return this;
	}
	
	
	
}
