package org.cyk.utility.server.business.hierarchy;

import java.util.Collection;

import org.cyk.utility.server.persistence.jpa.hierarchy.AbstractIdentifiedByStringAndCoded;

public interface BusinessIdentifiedByStringAndCoded<ENTITY extends AbstractIdentifiedByStringAndCoded<ENTITY,?>> extends BusinessIdentifiedByString<ENTITY> {

	Collection<ENTITY> findByParentsCodes(Collection<String> parentsCodes);
	Collection<ENTITY> findByParentsCodes(String...parentsCodes);
	
	Collection<ENTITY> findByChildrenCodes(Collection<String> childrenCodes);
	Collection<ENTITY> findByChildrenCodes(String...childrenCodes);
	
}