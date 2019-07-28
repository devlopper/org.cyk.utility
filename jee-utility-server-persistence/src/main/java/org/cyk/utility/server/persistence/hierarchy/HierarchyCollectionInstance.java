package org.cyk.utility.server.persistence.hierarchy;

import java.util.Collection;

import org.cyk.utility.collection.CollectionInstance;

public interface HierarchyCollectionInstance<ENTITY extends AbstractIdentifiedByStringAndCodedAndNamedAndHierarchical<?,?>,HIERARCHY extends AbstractHierarchy<ENTITY>> extends CollectionInstance<HIERARCHY> {

	Collection<ENTITY> getHierarchyParents();
	Collection<ENTITY> getHierarchyChildren();

}