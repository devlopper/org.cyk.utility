package org.cyk.utility.playground.client.controller.impl;

import java.io.Serializable;
import java.security.Principal;

import org.cyk.utility.__kernel__.icon.Icon;
import org.cyk.utility.client.controller.component.menu.AbstractMenuBuilderMapInstantiatorImpl;
import org.cyk.utility.client.controller.component.menu.MenuBuilder;
import org.cyk.utility.client.controller.component.menu.MenuItemBuilder;
import org.cyk.utility.playground.client.controller.entities.MyEntity;
import org.cyk.utility.playground.client.controller.entities.Node;
import org.cyk.utility.playground.client.controller.entities.Person;
import org.cyk.utility.playground.client.controller.entities.SelectedNode;

@org.cyk.utility.playground.server.System
public class MenuBuilderMapInstantiatorImpl extends AbstractMenuBuilderMapInstantiatorImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __instantiateSessionMenuBuilderItems__(Object key,MenuBuilder sessionMenuBuilder, Object request, Principal principal) {
		sessionMenuBuilder.addItems(
				__inject__(MenuItemBuilder.class).setCommandableName("Non hierarchique").setCommandableIcon(Icon.QUESTION)
					.list(Person.class,MyEntity.class,SelectedNode.class)
				,__inject__(MenuItemBuilder.class).setCommandableName("Hierarchique").setCommandableIcon(Icon.FILE)
					.tree(Node.class)
				,__instantiateMenuItemTools__()
				);	
	}

}
