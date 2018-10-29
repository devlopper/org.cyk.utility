package org.cyk.utility.client.controller.component.layout;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractInvisibleComponentImpl;

public class InsertImpl extends AbstractInvisibleComponentImpl implements Insert,Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	
	@Override
	public Insert setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String getName() {
		return name;
	}

}
