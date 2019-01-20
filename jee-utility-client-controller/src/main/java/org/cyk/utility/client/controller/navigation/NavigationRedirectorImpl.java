package org.cyk.utility.client.controller.navigation;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;

public class NavigationRedirectorImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements NavigationRedirector , Serializable {
	private static final long serialVersionUID = 1L;

	private Navigation navigation;
	
	@Override
	public Navigation getNavigation() {
		return navigation;
	}
	
	@Override
	public NavigationRedirector setNavigation(Navigation navigation) {
		this.navigation = navigation;
		return this;
	}
	
}
