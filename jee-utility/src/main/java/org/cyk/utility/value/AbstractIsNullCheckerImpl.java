package org.cyk.utility.value;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class AbstractIsNullCheckerImpl extends AbstractFunctionWithPropertiesAsInputImpl<Boolean> implements IsNullChecker,Serializable {
	private static final long serialVersionUID = 1L;

	private Object value;
	
	@Override
	protected Boolean __execute__() throws Exception {
		Object value = getValue();
		return Boolean.TRUE.equals(__execute__(value));
	}
	
	protected Boolean __execute__(Object value) throws Exception {
		return value == null;
	}
	
	@Override
	public Object getValue() {
		return value;
	}
	
	@Override
	public IsNullChecker setValue(Object value) {
		this.value = value;
		return this;
	}
}
