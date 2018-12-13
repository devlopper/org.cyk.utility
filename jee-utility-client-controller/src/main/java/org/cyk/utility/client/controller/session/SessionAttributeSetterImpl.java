package org.cyk.utility.client.controller.session;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;

public class SessionAttributeSetterImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements SessionAttributeSetter,Serializable {
	private static final long serialVersionUID = 1L;

	private SessionAttributeEnumeration attribute;
	private Object value;

	@Override
	public SessionAttributeEnumeration getAttribute() {
		return attribute;
	}

	@Override
	public SessionAttributeSetter setAttribute(SessionAttributeEnumeration attribute) {
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
