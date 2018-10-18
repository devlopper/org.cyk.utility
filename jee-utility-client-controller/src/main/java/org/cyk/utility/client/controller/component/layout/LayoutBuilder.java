package org.cyk.utility.client.controller.component.layout;

import java.util.Collection;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface LayoutBuilder extends FunctionWithPropertiesAsInput<Layout> {

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
