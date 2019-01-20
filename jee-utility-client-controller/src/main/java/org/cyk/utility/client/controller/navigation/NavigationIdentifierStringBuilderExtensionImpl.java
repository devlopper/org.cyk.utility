package org.cyk.utility.client.controller.navigation;

import java.io.Serializable;

import org.cyk.utility.string.AbstractStringFunctionImpl;

public class NavigationIdentifierStringBuilderExtensionImpl extends AbstractStringFunctionImpl implements NavigationIdentifierStringBuilderExtension,Serializable {
	private static final long serialVersionUID = 1L;

	private NavigationIdentifierStringBuilder navigationIdentifier;
	private String result;
	
	@Override
	public NavigationIdentifierStringBuilder getNavigationIdentifier() {
		return navigationIdentifier;
	}

	@Override
	public NavigationIdentifierStringBuilderExtension setNavigationIdentifier(NavigationIdentifierStringBuilder navigationIdentifier) {
		this.navigationIdentifier = navigationIdentifier;
		return this;
	}

	@Override
	public String getResult() {
		return result;
	}

	@Override
	public NavigationIdentifierStringBuilderExtension setResult(String result) {
		this.result = result;
		return this;
	}

	
	
}
