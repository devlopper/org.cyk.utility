package org.cyk.utility.client.controller.component.layout;

import org.cyk.utility.client.controller.component.InvisibleComponentBuilder;

public interface LayoutItemBuilder extends InvisibleComponentBuilder<LayoutItem> {

	LayoutBuilder getLayout();
	LayoutItemBuilder setLayout(LayoutBuilder layout);
	
}
