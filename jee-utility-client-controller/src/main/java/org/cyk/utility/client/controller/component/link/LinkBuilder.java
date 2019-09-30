package org.cyk.utility.client.controller.component.link;

import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.component.text.TextBuilder;

public interface LinkBuilder extends VisibleComponentBuilder<Link> {

	TextBuilder getText();
	TextBuilder getText(Boolean injectIfNull);
	LinkBuilder setText(TextBuilder text);
	LinkBuilder setTextCharacters(String characters);
	/*
	UniformResourceLocatorStringBuilder getUniformResourceLocator();
	UniformResourceLocatorStringBuilder getUniformResourceLocator(Boolean injectIfNull);
	LinkBuilder setUniformResourceLocator(UniformResourceLocatorStringBuilder uniformResourceLocator);
	*/
}
