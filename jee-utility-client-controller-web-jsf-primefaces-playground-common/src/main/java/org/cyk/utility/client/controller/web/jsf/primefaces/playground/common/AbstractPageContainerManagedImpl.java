package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import org.cyk.utility.client.controller.component.menu.MenuBuilderMap;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMapGetter;

public abstract class AbstractPageContainerManagedImpl extends org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl {
	private static final long serialVersionUID = -5590021276559897250L;

	@Override
	protected MenuBuilderMap __getMenuBuilderMap__() {
		return __inject__(MenuBuilderMapGetter.class).execute().getOutput();
	}
	
}
