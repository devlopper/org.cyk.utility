package org.cyk.utility.client.controller.navigation;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndVoidAsOutput;

public interface NavigationRedirector extends FunctionWithPropertiesAsInputAndVoidAsOutput {

	Navigation getNavigation();
	NavigationRedirector setNavigation(Navigation navigation);
	
}
