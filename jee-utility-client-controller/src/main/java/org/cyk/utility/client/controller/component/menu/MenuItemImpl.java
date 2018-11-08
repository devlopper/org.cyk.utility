package org.cyk.utility.client.controller.component.menu;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;
import org.cyk.utility.client.controller.component.command.Commandable;

public class MenuItemImpl extends AbstractVisibleComponentImpl implements MenuItem,Serializable {
	private static final long serialVersionUID = 1L;

	private Commandable commandable;
	
	@Override
	public Commandable getCommandable() {
		return commandable;
	}

	@Override
	public MenuItem setCommandable(Commandable commandable) {
		this.commandable = commandable;
		return this;
	}
	
	@Override
	public MenuItem getParent() {
		return (MenuItem) super.getParent();
	}

}
