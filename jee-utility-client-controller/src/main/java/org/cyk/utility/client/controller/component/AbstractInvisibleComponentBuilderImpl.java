package org.cyk.utility.client.controller.component;

import java.io.Serializable;

public abstract class AbstractInvisibleComponentBuilderImpl<COMPONENT extends InvisibleComponent> extends AbstractComponentBuilderImpl<COMPONENT> implements InvisibleComponentBuilder<COMPONENT>,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public InvisibleComponentBuilder<COMPONENT> setOutputProperty(Object key, Object value) {
		return (InvisibleComponentBuilder<COMPONENT>) super.setOutputProperty(key, value);
	}
	
}
