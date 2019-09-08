package org.cyk.utility.client.controller.component;

import java.io.Serializable;

import org.cyk.utility.method.AbstractMethodCallListenerImpl;

@Deprecated
public abstract class AbstractComponentBuilderExecuteListenerImpl extends AbstractMethodCallListenerImpl implements ComponentBuilderExecuteListener,Serializable {
	private static final long serialVersionUID = 1L;

	private Component component;
	
	@Override
	public Component getComponent() {
		return component;
	}

	@Override
	public ComponentBuilderExecuteListener setComponent(Component component) {
		this.component = component;
		return this;
	}
	
	@Override
	public ComponentBuilderExecuteListener setObject(Object object) {
		return (ComponentBuilderExecuteListener) super.setObject(object);
	}
	
}
