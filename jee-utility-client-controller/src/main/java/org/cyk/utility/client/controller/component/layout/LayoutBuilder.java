package org.cyk.utility.client.controller.component.layout;

import java.util.Collection;

import org.cyk.utility.client.controller.component.InvisibleComponentBuilder;

public interface LayoutBuilder extends InvisibleComponentBuilder<Layout> {

	LayoutBuilerItems getItems();
	LayoutBuilerItems getItems(Boolean injectIfNull);
	LayoutBuilder setItems(LayoutBuilerItems items);
	
	LayoutBuilder addItems(Collection<LayoutBuilerItem> items);
	LayoutBuilder addItems(LayoutBuilerItem...items);
	
	Type getType();
	LayoutBuilder setType(Type type);
	
	/**/
	
	public static enum Type {FORM}
}
