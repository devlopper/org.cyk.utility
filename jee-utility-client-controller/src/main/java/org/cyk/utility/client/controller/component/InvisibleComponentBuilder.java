package org.cyk.utility.client.controller.component;

public interface InvisibleComponentBuilder<COMPONENT extends InvisibleComponent> extends ComponentBuilder<COMPONENT> {
	
	@Override InvisibleComponentBuilder<COMPONENT> setOutputProperty(Object key, Object value);
}
