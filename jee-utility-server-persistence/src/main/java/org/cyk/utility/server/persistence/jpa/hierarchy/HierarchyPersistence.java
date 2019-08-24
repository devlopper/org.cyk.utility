package org.cyk.utility.server.persistence.jpa.hierarchy;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.PersistenceEntity;

public interface HierarchyPersistence<HIERARCHY extends AbstractHierarchy<ENTITY>,ENTITY extends AbstractIdentifiedByString<?,?>,HIERARCHIES extends Hierarchies<HIERARCHY,ENTITY>> extends PersistenceEntity<HIERARCHY> {

	HIERARCHIES readByParentsIdentifiers(Collection<String> parentsIdentifiers);
	Long countByParentsIdentifiers(Collection<String> parentsIdentifiers,Properties properties);
	Long countByParentsIdentifiers(Collection<String> parentsIdentifiers);
	HIERARCHIES readByParentsIdentifiers(String...parentsIdentifiers);
	Long countByParentsIdentifiers(String...parentsIdentifiers);
	
	//HIERARCHIES readByParentsIdentifiersByChildrenIdentifiers(Collection<String> parentsIdentifiers,Collection<String> childrenIdentifiers);
	//HIERARCHIES readByParentsByChildren(Collection<ENTITY> parents,Collection<ENTITY> children);
	
	HIERARCHIES readByParentsBusinessIdentifiers(Collection<Object> parentsBusinessIdentifiers);
	Long countByParentsBusinessIdentifiers(Collection<Object> parentsBusinessIdentifiers);
	HIERARCHIES readByParentsBusinessIdentifiers(Object...parentsBusinessIdentifiers);
	Long countByParentsBusinessIdentifiers(Object...parentsBusinessIdentifiers);
	
	HIERARCHIES readByParents(Collection<ENTITY> parents);
	HIERARCHIES readByParents(@SuppressWarnings("unchecked") ENTITY...parents);
	
	HIERARCHIES readWhereParentDoesNotHaveParent(Properties properties);
	Long countWhereParentDoesNotHaveParent(Properties properties);
	HIERARCHIES readWhereParentDoesNotHaveParent();
	Long countWhereParentDoesNotHaveParent();
	
	HIERARCHIES readByChildrenIdentifiers(Collection<String> childrenIdentifiers);
	HIERARCHIES readByChildrenIdentifiers(String...childrenIdentifiers);
	
	HIERARCHIES readByChildrenBusinessIdentifiers(Collection<Object> childrenBusinessIdentifiers);
	HIERARCHIES readByChildrenBusinessIdentifiers(Object...childrenBusinessIdentifiers);
	
	HIERARCHIES readByChildren(Collection<ENTITY> children);
	HIERARCHIES readByChildren(@SuppressWarnings("unchecked") ENTITY...children);
	
	HIERARCHIES readWhereIsParentOrChildIdentifiers(Collection<String> identifiers);
	HIERARCHIES readWhereIsParentOrChildIdentifiers(String...identifiers);
	HIERARCHIES readWhereIsParentOrChild(Collection<ENTITY> entities);
	HIERARCHIES readWhereIsParentOrChild(@SuppressWarnings("unchecked") ENTITY...entities);
	
}
