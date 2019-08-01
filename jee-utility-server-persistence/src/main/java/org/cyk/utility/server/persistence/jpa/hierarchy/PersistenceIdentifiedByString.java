package org.cyk.utility.server.persistence.jpa.hierarchy;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.PersistenceEntity;

public interface PersistenceIdentifiedByString<ENTITY extends AbstractIdentifiedByString<?,?>> extends PersistenceEntity<ENTITY> {

	Collection<ENTITY> readByParentsIdentifiers(Collection<String> parentsIdentifiers,Properties properties);
	Collection<ENTITY> readByParentsIdentifiers(Collection<String> parentsIdentifiers);
	Collection<ENTITY> readByParentsIdentifiers(Properties properties,String...parentsIdentifiers);
	Collection<ENTITY> readByParentsIdentifiers(String...parentsIdentifiers);
	
	Collection<ENTITY> readByParents(Collection<ENTITY> parents,Properties properties);
	Collection<ENTITY> readByParents(Collection<ENTITY> parents);
	Collection<ENTITY> readByParents(Properties properties,@SuppressWarnings("unchecked") ENTITY...parents);
	Collection<ENTITY> readByParents(@SuppressWarnings("unchecked") ENTITY...parents);
	
	Collection<ENTITY> readByChildrenIdentifiers(Collection<String> childrenIdentifiers,Properties properties);
	Collection<ENTITY> readByChildrenIdentifiers(Collection<String> childrenIdentifiers);
	Collection<ENTITY> readByChildrenIdentifiers(Properties properties,String...childrenIdentifiers);
	Collection<ENTITY> readByChildrenIdentifiers(String...childrenIdentifiers);
	
	Collection<ENTITY> readByChildren(Collection<ENTITY> children,Properties properties);
	Collection<ENTITY> readByChildren(Collection<ENTITY> children);
	Collection<ENTITY> readByChildren(Properties properties,@SuppressWarnings("unchecked") ENTITY...children);
	Collection<ENTITY> readByChildren(@SuppressWarnings("unchecked") ENTITY...children);
}
