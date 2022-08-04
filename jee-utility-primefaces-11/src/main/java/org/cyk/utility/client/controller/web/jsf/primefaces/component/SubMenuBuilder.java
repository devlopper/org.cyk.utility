package org.cyk.utility.client.controller.web.jsf.primefaces.component;

import org.cyk.utility.client.controller.component.menu.MenuItem;

public interface SubMenuBuilder extends ComponentBuilder<org.primefaces.model.menu.Submenu,MenuItem> {

	@Override SubMenuBuilder setModel(MenuItem model);
	@Override SubMenuBuilder setParent(Object parent);
	
}
