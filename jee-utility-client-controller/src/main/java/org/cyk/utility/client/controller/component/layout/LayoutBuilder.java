package org.cyk.utility.client.controller.component.layout;

import java.util.Collection;

import org.cyk.utility.client.controller.component.VisibleComponentBuilder;

public interface LayoutBuilder extends VisibleComponentBuilder<Layout> {

	LayoutItemBuilders getItems();
	LayoutItemBuilders getItems(Boolean injectIfNull);
	LayoutBuilder setItems(LayoutItemBuilders items);
	
	LayoutBuilder addItems(Collection<LayoutItemBuilder> items);
	LayoutBuilder addItems(LayoutItemBuilder...items);
	
	LayoutType getType();
	LayoutBuilder setType(LayoutType type);
	

}
