package org.cyk.utility.server.persistence.jpa.hierarchy;

import java.util.Collection;

import org.cyk.utility.server.persistence.PersistenceEntity;

public interface HierarchyPersistence<HIERARCHY extends AbstractHierarchy<ENTITY>,ENTITY extends AbstractIdentifiedByString<?,?>,HIERARCHIES extends Hierarchies<HIERARCHY,ENTITY>> extends PersistenceEntity<HIERARCHY> {

	HIERARCHIES readByParentsIdentifiers(Collection<String> parentsIdentifiers);
	HIERARCHIES readByParentsIdentifiers(String...parentsIdentifiers);
	
	HIERARCHIES readByParentsBusinessIdentifiers(Collection<Object> parentsBusinessIdentifiers);
	HIERARCHIES readByParentsBusinessIdentifiers(Object...parentsBusinessIdentifiers);
	
	HIERARCHIES readByParents(Collection<ENTITY> parents);
	HIERARCHIES readByParents(@SuppressWarnings("unchecked") ENTITY...parents);
	
	HIERARCHIES readByChildrenIdentifiers(Collection<String> childrenIdentifiers);
	HIERARCHIES readByChildrenIdentifiers(String...childrenIdentifiers);
	
	HIERARCHIES readByChildrenBusinessIdentifiers(Collection<Object> childrenBusinessIdentifiers);
	HIERARCHIES readByChildrenBusinessIdentifiers(Object...childrenBusinessIdentifiers);
	
	HIERARCHIES readByChildren(Collection<ENTITY> children);
	HIERARCHIES readByChildren(@SuppressWarnings("unchecked") ENTITY...children);
}
