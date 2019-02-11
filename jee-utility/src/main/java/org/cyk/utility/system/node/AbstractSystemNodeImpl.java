package org.cyk.utility.system.node;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public abstract class AbstractSystemNodeImpl extends AbstractObject implements SystemNode,Serializable {
	private static final long serialVersionUID = 1L;

	private String name;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public SystemNode setName(String name) {
		this.name = name;
		return this;
	}
	
}
