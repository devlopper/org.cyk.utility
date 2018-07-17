package org.cyk.utility.filter;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public class FilterImpl extends AbstractObject implements Filter, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public Filter addChildren(Collection<Object> children) {
		return (Filter) super.addChildren(children);
	}
	
	@Override
	public Filter addChild(Object... child) {
		return (Filter) super.addChild(child);
	}
}
