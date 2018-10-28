package org.cyk.utility.client.controller.component;

import org.cyk.utility.css.StyleBuilder;

public interface VisibleComponentBuilder<COMPONENT extends VisibleComponent> extends ComponentBuilder<COMPONENT> {
	
	StyleBuilder getStyle();
	StyleBuilder getStyle(Boolean injectIfNull);
	VisibleComponentBuilder<COMPONENT> setStyle(StyleBuilder styleBuilder);
	
	VisibleComponentBuilder<COMPONENT> addStyleClasses(String...classes);
	
	@Override ComponentBuilder<COMPONENT> setOutputProperty(Object key, Object value);
}
