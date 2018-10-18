package org.cyk.utility.client.controller.component;

public interface VisibleComponentBuilder<COMPONENT extends VisibleComponent> extends ComponentBuilder<COMPONENT> {
	
	VisibleComponentArea getArea();
	VisibleComponentArea getArea(Boolean injectIfNull);
	VisibleComponentBuilder<COMPONENT> setArea(VisibleComponentArea area);
	
}
