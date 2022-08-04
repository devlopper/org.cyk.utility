package org.cyk.utility.client.controller.web.jsf.primefaces.component;

import org.cyk.utility.client.controller.component.menu.MenuItem;

public interface MenuItemBuilder extends ComponentBuilder<org.primefaces.model.menu.MenuItem,MenuItem> {

	@Override MenuItemBuilder setModel(MenuItem model);
	@Override MenuItemBuilder setParent(Object parent);
	
}
