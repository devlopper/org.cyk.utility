package org.cyk.utility.client.controller.component.link;

import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.component.text.TextBuilder;
import org.cyk.utility.resource.locator.UniformResourceLocatorStringBuilder;

public interface LinkBuilder extends VisibleComponentBuilder<Link> {

	TextBuilder getText();
	TextBuilder getText(Boolean injectIfNull);
	LinkBuilder setText(TextBuilder text);
	
	UniformResourceLocatorStringBuilder getUniformResourceLocator();
	UniformResourceLocatorStringBuilder getUniformResourceLocator(Boolean injectIfNull);
	LinkBuilder setUniformResourceLocator(UniformResourceLocatorStringBuilder uniformResourceLocator);
}
