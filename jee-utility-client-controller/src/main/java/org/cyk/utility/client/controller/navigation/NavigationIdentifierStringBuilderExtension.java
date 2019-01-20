package org.cyk.utility.client.controller.navigation;

import org.cyk.utility.string.StringFunction;

public interface NavigationIdentifierStringBuilderExtension extends StringFunction {

	NavigationIdentifierStringBuilder getNavigationIdentifier();
	NavigationIdentifierStringBuilderExtension setNavigationIdentifier(NavigationIdentifierStringBuilder navigationIdentifier);
	
	String getResult();
	NavigationIdentifierStringBuilderExtension setResult(String result);
}
