package org.cyk.utility.client.controller.navigation;

import org.cyk.utility.__kernel__.object.Objects;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.object.ObjectByObjectMap;

@Deprecated
public interface NavigationBuilder extends FunctionWithPropertiesAsInput<Navigation> {

	@Override NavigationBuilder setIdentifier(Object identifier);
	/*
	UrlBuilder getUrl();
	UrlBuilder getUrl(Boolean injectIfNull);
	NavigationBuilder setUrl(UrlBuilder url);
	*/
	NavigationBuilder setParameterMap(ObjectByObjectMap parameterMap);
	ObjectByObjectMap getParameterMap(Boolean injectIfNull);
	ObjectByObjectMap getParameterMap();
	NavigationBuilder setParameters(Object...keyValues);
	
	Objects getDynamicParameterNames();
	Objects getDynamicParameterNames(Boolean injectIfNull);
	NavigationBuilder setDynamicParameterNames(Objects dynamicParameterNames);
	
	SystemAction getSystemAction();
	NavigationBuilder setSystemAction(SystemAction systemAction);
	
	Boolean getIsDeriveParametersFromSystemAction();
	NavigationBuilder setIsDeriveParametersFromSystemAction(Boolean isDeriveParametersFromSystemAction);
	
	NavigationBuilder setIdentifierBuilder(NavigationIdentifierStringBuilder identifierBuilder);
	NavigationIdentifierStringBuilder getIdentifierBuilder();
	NavigationIdentifierStringBuilder getIdentifierBuilder(Boolean injectIfNull);
	NavigationBuilder setIdentifierBuilderSystemAction(SystemAction systemAction);

}
