package org.cyk.utility.server.persistence.jpa.hierarchy;

import java.util.Collection;

import org.cyk.utility.server.persistence.PersistenceEntity;

public interface PersistenceIdentifiedByStringAndCodedAndHierarchical<ENTITY extends AbstractIdentifiedByStringAndCoded<?,?>> extends PersistenceEntity<ENTITY> {

	Collection<ENTITY> readByParentsIdentifiers(Collection<String> parentsIdentifiers);
	Collection<ENTITY> readByParentsIdentifiers(String...parentsIdentifiers);
	Collection<ENTITY> readByParents(Collection<ENTITY> parents);
	Collection<ENTITY> readByParents(@SuppressWarnings("unchecked") ENTITY...parents);
	
	Collection<ENTITY> readByChildrenIdentifiers(Collection<String> childrenIdentifiers);
	Collection<ENTITY> readByChildrenIdentifiers(String...childrenIdentifiers);
	Collection<ENTITY> readByChildren(Collection<ENTITY> children);
	Collection<ENTITY> readByChildren(@SuppressWarnings("unchecked") ENTITY...children);
}
