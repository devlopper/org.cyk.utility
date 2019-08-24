package org.cyk.utility.server.persistence.jpa.hierarchy;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;

public interface PersistenceIdentifiedByStringAndCoded<ENTITY extends AbstractIdentifiedByStringAndCoded<?,?>> extends PersistenceIdentifiedByString<ENTITY> {

	Collection<ENTITY> readByParentsCodes(Collection<String> parentsCodes,Properties properties);
	Collection<ENTITY> readByParentsCodes(Collection<String> parentsCodes);
	Collection<ENTITY> readByParentsCodes(Properties properties,String...parentsCodes);
	Collection<ENTITY> readByParentsCodes(String...parentsCodes);
	
	Long countByParentsCodes(Collection<String> parentsCodes,Properties properties);
	Long countByParentsCodes(Collection<String> parentsCodes);
	Long countByParentsCodes(Properties properties,String...parentsCodes);
	Long countByParentsCodes(String...parentsCodes);
	
	Collection<ENTITY> readByChildrenCodes(Collection<String> childrenCodes,Properties properties);
	Collection<ENTITY> readByChildrenCodes(Collection<String> childrenCodes);
	Collection<ENTITY> readByChildrenCodes(Properties properties,String...childrenCodes);
	Collection<ENTITY> readByChildrenCodes(String...childrenCodes);
	
}
