package org.cyk.utility.server.business.hierarchy;

import java.util.Collection;

import org.cyk.utility.server.business.BusinessEntity;
import org.cyk.utility.server.persistence.jpa.hierarchy.AbstractIdentifiedByString;

public interface BusinessIdentifiedByString<ENTITY extends AbstractIdentifiedByString<ENTITY,?>> extends BusinessEntity<ENTITY> {

	Collection<ENTITY> findByParentsIdentifiers(Collection<String> parentsIdentifiers);
	Collection<ENTITY> findByParentsIdentifiers(String...parentsIdentifiers);
	Collection<ENTITY> findByParents(Collection<ENTITY> parents);
	Collection<ENTITY> findByParents(@SuppressWarnings("unchecked") ENTITY...parents);
	
	Collection<ENTITY> findByChildrenIdentifiers(Collection<String> childrenIdentifiers);
	Collection<ENTITY> findByChildrenIdentifiers(String...childrenIdentifiers);
	Collection<ENTITY> findByChildren(Collection<ENTITY> children);
	Collection<ENTITY> findByChildren(@SuppressWarnings("unchecked") ENTITY...children);
}