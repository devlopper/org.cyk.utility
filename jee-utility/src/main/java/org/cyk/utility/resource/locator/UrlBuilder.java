package org.cyk.utility.resource.locator;

import java.net.URL;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface UrlBuilder extends FunctionWithPropertiesAsInput<URL> {

	UniformResourceLocatorStringBuilder getString();
	UniformResourceLocatorStringBuilder getString(Boolean injectIfNull);
	UrlBuilder setString(UniformResourceLocatorStringBuilder string);
	
}
