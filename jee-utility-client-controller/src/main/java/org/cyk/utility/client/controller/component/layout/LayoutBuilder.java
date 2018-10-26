package org.cyk.utility.client.controller.component.layout;

import java.util.Collection;

import org.cyk.utility.client.controller.component.VisibleComponentBuilder;

public interface LayoutBuilder extends VisibleComponentBuilder<Layout> {

	LayoutItemBuilders getItems();
	LayoutItemBuilders getItems(Boolean injectIfNull);
	LayoutBuilder setItems(LayoutItemBuilders items);
	
	LayoutBuilder addItems(Collection<LayoutItemBuilder> items);
	LayoutBuilder addItems(LayoutItemBuilder...items);
	
	Type getType();
	LayoutBuilder setType(Type type);
	
	/**/
	
	public static enum Type {FORM}
}
