package org.cyk.utility.client.controller.navigation;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class NavigationBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Navigation> implements NavigationBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Navigation __execute__() throws Exception {
		Navigation navigation = __inject__(Navigation.class);
		navigation.setIdentifier(getIdentifier());
		return navigation;
	}
	
}
