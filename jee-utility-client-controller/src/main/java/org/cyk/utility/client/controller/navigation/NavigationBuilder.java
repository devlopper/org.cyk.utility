package org.cyk.utility.client.controller.navigation;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.resource.locator.UrlBuilder;

public interface NavigationBuilder extends FunctionWithPropertiesAsInput<Navigation> {

	UrlBuilder getUrl();
	NavigationBuilder setUrl(UrlBuilder url);
}
