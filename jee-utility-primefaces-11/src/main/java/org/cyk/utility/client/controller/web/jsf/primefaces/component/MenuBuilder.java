package org.cyk.utility.client.controller.web.jsf.primefaces.component;

import org.cyk.utility.client.controller.component.menu.Menu;

public interface MenuBuilder extends ComponentBuilder<org.primefaces.model.menu.MenuModel,Menu> {

	@Override MenuBuilder setModel(Menu model);
	
}
