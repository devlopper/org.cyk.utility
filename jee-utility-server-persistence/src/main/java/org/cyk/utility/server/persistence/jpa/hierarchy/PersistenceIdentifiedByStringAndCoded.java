package org.cyk.utility.server.persistence.jpa.hierarchy;

import java.util.Collection;

public interface PersistenceIdentifiedByStringAndCoded<ENTITY extends AbstractIdentifiedByStringAndCoded<?,?>> extends PersistenceIdentifiedByString<ENTITY> {

	Collection<ENTITY> readByParentsCodes(Collection<String> parentsCodes);
	Collection<ENTITY> readByParentsCodes(String...parentsCodes);
	Collection<ENTITY> readByParents(Collection<ENTITY> parents);
	Collection<ENTITY> readByParents(@SuppressWarnings("unchecked") ENTITY...parents);
	
	Collection<ENTITY> readByChildrenCodes(Collection<String> childrenCodes);
	Collection<ENTITY> readByChildrenCodes(String...childrenCodes);
	Collection<ENTITY> readByChildren(Collection<ENTITY> children);
	Collection<ENTITY> readByChildren(@SuppressWarnings("unchecked") ENTITY...children);
}
