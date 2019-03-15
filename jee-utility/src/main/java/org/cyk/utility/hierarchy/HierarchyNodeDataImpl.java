package org.cyk.utility.hierarchy;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public class HierarchyNodeDataImpl extends AbstractObject implements HierarchyNodeData,Serializable {
	private static final long serialVersionUID = 1L;

	private Object value;
	
	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public HierarchyNodeData setValue(Object value) {
		this.value = value;
		return this;
	}

	@Override
	public String toString() {
		return value == null ? null : value.toString();
	}
}
