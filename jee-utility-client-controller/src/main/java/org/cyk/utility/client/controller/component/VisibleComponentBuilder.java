package org.cyk.utility.client.controller.component;

import org.cyk.utility.client.controller.component.theme.Theme;
import org.cyk.utility.css.StyleBuilder;

public interface VisibleComponentBuilder<COMPONENT extends VisibleComponent> extends ComponentBuilder<COMPONENT> {
	
	StyleBuilder getStyle();
	StyleBuilder getStyle(Boolean injectIfNull);
	VisibleComponentBuilder<COMPONENT> setStyle(StyleBuilder styleBuilder);
	
	VisibleComponentBuilder<COMPONENT> addStyleClasses(String...classes);
	VisibleComponentBuilder<COMPONENT> addStyleValues(String...values);
	
	Object getTooltip();
	VisibleComponentBuilder<COMPONENT> setTooltip(Object tooltip);
	
	Theme getTheme();
	VisibleComponentBuilder<COMPONENT> setTheme(Theme theme);
	
	@Override ComponentBuilder<COMPONENT> setOutputProperty(Object key, Object value);
	
	String PROPERTY_TOOLTIP = "tooltip";
}
