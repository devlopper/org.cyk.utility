package org.cyk.utility.client.controller.navigation;

import java.io.Serializable;

import org.cyk.utility.string.AbstractStringFunctionImpl;

public class NavigationIdentifierToUrlStringMapperImpl extends AbstractStringFunctionImpl implements NavigationIdentifierToUrlStringMapper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public NavigationIdentifierToUrlStringMapper setIdentifier(Object identifier) {
		return (NavigationIdentifierToUrlStringMapper) super.setIdentifier(identifier);
	}
	
}
