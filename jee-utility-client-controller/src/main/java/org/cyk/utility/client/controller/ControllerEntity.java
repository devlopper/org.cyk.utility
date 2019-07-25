package org.cyk.utility.client.controller;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.value.ValueUsageType;

public interface ControllerEntity<ENTITY> extends ControllerServiceProvider<ENTITY> {

	/* Read */
	
	Collection<ENTITY> read(Properties properties);
	Collection<ENTITY> read();
	
	Collection<ENTITY> readByIdentifiers(Collection<Object> identifiers,ValueUsageType valueUsageType,Properties properties);
	Collection<ENTITY> readByIdentifiers(Collection<Object> identifiers,ValueUsageType valueUsageType);
	
	Collection<ENTITY> readBySystemIdentifiers(Collection<Object> identifiers,Properties properties);
	Collection<ENTITY> readBySystemIdentifiers(Collection<Object> identifiers);
	
	Collection<ENTITY> readByBusinessIdentifiers(Collection<Object> identifiers,Properties properties);
	Collection<ENTITY> readByBusinessIdentifiers(Collection<Object> identifiers);
	
	ENTITY readByIdentifier(Object identifier,ValueUsageType valueUsageType,Properties properties);
	ENTITY readByIdentifier(Object identifier,ValueUsageType valueUsageType);
	
	ENTITY readBySystemIdentifier(Object identifier,Properties properties);
	ENTITY readBySystemIdentifier(Object identifier);
	
	ENTITY readByBusinessIdentifier(Object identifier,Properties properties);
	ENTITY readByBusinessIdentifier(Object identifier);
	
	Long count(Properties properties);
	Long count();
	
	/* Find */
	ControllerEntity<ENTITY> redirect(Object identifier,Properties properties);
	ControllerEntity<ENTITY> redirect(Object identifier);
	
	/* Select */
	ControllerEntity<ENTITY> select(Collection<Object> identifiers,Properties properties);
	ControllerEntity<ENTITY> select(Collection<Object> identifiers);
	
	Class<ENTITY> getEntityClass();
	ControllerEntity<ENTITY> setEntityClass(Class<ENTITY> entityClass);
	
}
