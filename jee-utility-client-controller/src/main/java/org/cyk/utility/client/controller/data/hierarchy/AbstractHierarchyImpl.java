package org.cyk.utility.client.controller.data.hierarchy;

import java.io.Serializable;

public abstract class AbstractHierarchyImpl<NODE extends DataIdentifiedByString<?>> extends org.cyk.utility.client.controller.data.AbstractDataIdentifiedByStringImpl implements Hierarchy<NODE>,Serializable {
	private static final long serialVersionUID = 1L;

	private NODE parent,child;
	
	@Override
	public NODE getParent() {
		return parent;
	}

	@Override
	public Hierarchy<NODE> setParent(NODE parent) {
		this.parent = parent;
		return this;
	}

	@Override
	public NODE getChild() {
		return child;
	}

	@Override
	public Hierarchy<NODE> setChild(NODE child) {
		this.child = child;
		return this;
	}
	
	
}
