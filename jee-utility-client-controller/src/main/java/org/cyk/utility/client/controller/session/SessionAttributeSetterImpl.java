package org.cyk.utility.client.controller.session;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;

public class SessionAttributeSetterImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements SessionAttributeSetter,Serializable {
	private static final long serialVersionUID = 1L;

	private Object attribute;
	private Object value;

	@Override
	public Object getAttribute() {
		return attribute;
	}

	@Override
	public SessionAttributeSetter setAttribute(Object attribute) {
		this.attribute = attribute;
		return this;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public SessionAttributeSetter setValue(Object value) {
		this.value = value;
		return this;
	}
	
}
