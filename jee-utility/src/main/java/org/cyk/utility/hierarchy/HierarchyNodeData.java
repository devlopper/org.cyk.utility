package org.cyk.utility.hierarchy;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface HierarchyNodeData extends Objectable {

	Object getValue();
	HierarchyNodeData setValue(Object value);
}
