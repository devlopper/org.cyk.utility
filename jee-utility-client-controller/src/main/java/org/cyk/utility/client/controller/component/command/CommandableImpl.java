package org.cyk.utility.client.controller.component.command;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;
import org.cyk.utility.client.controller.navigation.Navigation;

public class CommandableImpl extends AbstractVisibleComponentImpl implements Commandable,Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private CommandableRenderType renderType;
	private Navigation navigation;
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public Commandable setName(String name) {
		this.name = name;
		return this;
	}
	
	@Override
	public CommandableRenderType getRenderType() {
		return renderType;
	}
	
	@Override
	public Commandable setRenderType(CommandableRenderType renderType) {
		this.renderType = renderType;
		return this;
	}

	@Override
	public Navigation getNavigation() {
		return navigation;
	}

	@Override
	public Commandable setNavigation(Navigation navigation) {
		this.navigation = navigation;
		return this;
	}
	
}
