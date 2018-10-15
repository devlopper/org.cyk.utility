package org.cyk.utility.client.controller.component.layout;

import java.util.Collection;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface LayoutBuiler extends FunctionWithPropertiesAsInput<Layout> {

	LayoutBuilerItems getItems();
	LayoutBuilerItems getItems(Boolean injectIfNull);
	LayoutBuiler setItems(LayoutBuilerItems items);
	
	LayoutBuiler addItems(Collection<LayoutBuilerItem> items);
	LayoutBuiler addItems(LayoutBuilerItem...items);
	
	Integer getMaximumWidth();
	LayoutBuiler setMaximumWidth(Integer maximumWidth);
	
	Type getType();
	LayoutBuiler setType(Type type);
	
	/**/
	
	public static enum Type {FORM}
}
