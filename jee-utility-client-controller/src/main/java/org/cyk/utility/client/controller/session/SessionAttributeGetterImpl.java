package org.cyk.utility.client.controller.session;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class SessionAttributeGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Object> implements SessionAttributeGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private SessionAttributeEnumeration attribute;

	@Override
	public SessionAttributeEnumeration getAttribute() {
		return attribute;
	}

	@Override
	public SessionAttributeGetter setAttribute(SessionAttributeEnumeration attribute) {
		this.attribute = attribute;
		return this;
	}
	
}
