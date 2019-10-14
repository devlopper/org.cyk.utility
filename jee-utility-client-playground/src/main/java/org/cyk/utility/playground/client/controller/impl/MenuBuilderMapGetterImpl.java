package org.cyk.utility.playground.client.controller.impl;

import java.io.Serializable;
import java.security.Principal;

import org.cyk.utility.__kernel__.icon.Icon;
import org.cyk.utility.client.controller.component.menu.AbstractMenuBuilderMapGetterImpl;
import org.cyk.utility.client.controller.component.menu.MenuBuilder;
import org.cyk.utility.client.controller.component.menu.MenuItemBuilder;
import org.cyk.utility.playground.client.controller.entities.MyEntity;
import org.cyk.utility.playground.client.controller.entities.Node;
import org.cyk.utility.playground.client.controller.entities.Person;
import org.cyk.utility.playground.client.controller.entities.SelectedNode;

@org.cyk.utility.playground.server.System
public class MenuBuilderMapGetterImpl extends AbstractMenuBuilderMapGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void ____execute____(MenuBuilder sessionMenuBuilder, Object request, Principal principal) throws Exception {
		sessionMenuBuilder.addItems(
				__inject__(MenuItemBuilder.class).setCommandableName("Non hierarchique").setCommandableIcon(Icon.QUESTION)
					.list(Person.class,MyEntity.class,SelectedNode.class)
				,__inject__(MenuItemBuilder.class).setCommandableName("Hierarchique").setCommandableIcon(Icon.FILE)
					.tree(Node.class)
				);	
	}
	
	@Override
	protected void ____executePrincipalIsNotNull____(MenuBuilder sessionMenuBuilder, Object request, Principal principal) throws Exception {
		
	}

	@Override
	protected void ____executePrincipalIsNull____(MenuBuilder sessionMenuBuilder, Object request) throws Exception {
		
	}

	

}
