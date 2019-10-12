package org.cyk.utility.client.controller.data.hierarchy;

public interface Hierarchy<NODE extends DataIdentifiedByString<?>> extends org.cyk.utility.client.controller.data.DataIdentifiedByString {

	NODE getParent();
	Hierarchy<NODE> setParent(NODE privilege);
	
	NODE getChild();
	Hierarchy<NODE> setChild(NODE child);
}