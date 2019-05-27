package org.cyk.utility.client.controller;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.value.ValueUsageType;

public interface ControllerEntity<ENTITY> extends ControllerServiceProvider<ENTITY> {

	Collection<ENTITY> read(Properties properties);
	Collection<ENTITY> read();
	
	ENTITY readOne(Object identifier,Properties properties);
	ENTITY readOne(Object identifier,ValueUsageType valueUsageType);
	ENTITY readOne(Object identifier);
	ENTITY readOneBySystemIdentifier(Object identifier);
	ENTITY readOneByBusinessIdentifier(Object identifier);
	
	Collection<ENTITY> readMany(Properties properties);
	Collection<ENTITY> readMany();
	
	Long count(Properties properties);
	Long count();
	
	/* Find */
	ControllerEntity<ENTITY> redirect(Object identifier,Properties properties);
	ControllerEntity<ENTITY> redirect(Object identifier);
	
	Class<ENTITY> getEntityClass();
	ControllerEntity<ENTITY> setEntityClass(Class<ENTITY> entityClass);
	
}
