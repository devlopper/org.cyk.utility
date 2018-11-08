package org.cyk.utility.client.controller.navigation;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.object.ObjectByObjectMap;
import org.cyk.utility.resource.locator.UrlBuilder;

public interface NavigationBuilder extends FunctionWithPropertiesAsInput<Navigation> {

	UrlBuilder getUrl();
	UrlBuilder getUrl(Boolean injectIfNull);
	NavigationBuilder setUrl(UrlBuilder url);
	
	NavigationBuilder setParameterMap(ObjectByObjectMap parameterMap);
	ObjectByObjectMap getParameterMap(Boolean injectIfNull);
	ObjectByObjectMap getParameterMap();
	NavigationBuilder setParameters(Object...keyValues);
}
