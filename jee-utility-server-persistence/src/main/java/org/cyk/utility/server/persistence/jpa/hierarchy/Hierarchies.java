package org.cyk.utility.server.persistence.jpa.hierarchy;

import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionInstance;

public interface Hierarchies<HIERARCHY extends AbstractHierarchy<ENTITY>,ENTITY extends AbstractIdentifiedByString<?>> extends CollectionInstance<HIERARCHY> {

	Collection<ENTITY> getHierarchyParents();
	Collection<ENTITY> getHierarchyChildren();

}