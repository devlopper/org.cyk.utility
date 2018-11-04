package org.cyk.utility.client.controller.component.command;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;

public abstract class AbstractCommandableImpl extends AbstractVisibleComponentImpl implements Commandable,Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public Commandable setName(String name) {
		this.name = name;
		return this;
	}
	
}
