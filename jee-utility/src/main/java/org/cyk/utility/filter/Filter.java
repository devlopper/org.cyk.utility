package org.cyk.utility.filter;

import java.util.Collection;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface Filter extends Objectable {

	Filter addChildren(Collection<Object> children);
	Filter addChild(Object... child);
}
