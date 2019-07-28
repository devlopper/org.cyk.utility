package org.cyk.utility.server.persistence.hierarchy;

import java.util.Collection;

import org.cyk.utility.server.persistence.PersistenceEntity;

public interface HierarchyPersistence<HIERARCHY extends AbstractHierarchy<ENTITY>,ENTITY extends AbstractIdentifiedByStringAndCodedAndNamedAndHierarchical<?,?>,HIERARCHIES extends HierarchyCollectionInstance<ENTITY, HIERARCHY>> extends PersistenceEntity<HIERARCHY> {

	HIERARCHIES readByParentsCodes(Collection<String> parentsCodes);
	HIERARCHIES readByParentsCodes(String...parentsCodes);
	HIERARCHIES readByParents(Collection<ENTITY> parents);
	HIERARCHIES readByParents(@SuppressWarnings("unchecked") ENTITY...parents);
	
	HIERARCHIES readByChildrenCodes(Collection<String> childrenCodes);
	HIERARCHIES readByChildrenCodes(String...childrenCodes);
	HIERARCHIES readByChildren(Collection<ENTITY> children);
	HIERARCHIES readByChildren(@SuppressWarnings("unchecked") ENTITY...children);
}
