package org.cyk.utility.server.persistence.hierarchy;

import java.util.Collection;

import org.cyk.utility.collection.CollectionInstance;

public interface HierarchyCollectionInstance<ENTITY extends AbstractIdentifiedByStringAndCodedAndNamedAndHierarchy<?,?>,HIERARCHY extends AbstractHierarchy<ENTITY>> extends CollectionInstance<HIERARCHY> {

	Collection<ENTITY> getPrivilegeTypeParents();
	Collection<ENTITY> getPrivilegeTypeChildren();

}