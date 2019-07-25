package org.cyk.utility.client.controller;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.value.ValueUsageType;

public interface Controller extends ControllerServiceProvider<Object> {

	/* Create */
	
	/* Read */ 
	
	<ENTITY> Collection<ENTITY> read(Class<ENTITY> aClass, Properties properties);
	<ENTITY> Collection<ENTITY> read(Class<ENTITY> aClass);
	
	<ENTITY> Collection<ENTITY> readByIdentifiers(Class<ENTITY> aClass,Collection<Object> identifiers,ValueUsageType valueUsageType,Properties properties);
	<ENTITY> Collection<ENTITY> readByIdentifiers(Class<ENTITY> aClass,Collection<Object> identifiers,ValueUsageType valueUsageType);
	
	<ENTITY> Collection<ENTITY> readBySystemIdentifiers(Class<ENTITY> aClass,Collection<Object> identifiers,Properties properties);
	<ENTITY> Collection<ENTITY> readBySystemIdentifiers(Class<ENTITY> aClass,Collection<Object> identifiers);
	
	<ENTITY> Collection<ENTITY> readByBusinessIdentifiers(Class<ENTITY> aClass,Collection<Object> identifiers,Properties properties);
	<ENTITY> Collection<ENTITY> readByBusinessIdentifiers(Class<ENTITY> aClass,Collection<Object> identifiers);
	
	<ENTITY> ENTITY readByIdentifier(Class<ENTITY> aClass,Object identifier,ValueUsageType valueUsageType,Properties properties);
	<ENTITY> ENTITY readByIdentifier(Class<ENTITY> aClass,Object identifier,ValueUsageType valueUsageType);
	
	<ENTITY> ENTITY readBySystemIdentifier(Class<ENTITY> aClass,Object identifier,Properties properties);
	<ENTITY> ENTITY readBySystemIdentifier(Class<ENTITY> aClass,Object identifier);
	
	<ENTITY> ENTITY readByBusinessIdentifier(Class<ENTITY> aClass,Object identifier,Properties properties);
	<ENTITY> ENTITY readByBusinessIdentifier(Class<ENTITY> aClass,Object identifier);
	
	/* Update */
	
	/* Delete */
	
	<ENTITY> Controller deleteByClassByIdentififerByValueUsageType(Class<ENTITY> clazz,Object identifier,ValueUsageType valueUsageType);
	
	/* Count */
	<ENTITY> Long count(Class<ENTITY> aClass,Properties properties);
	<ENTITY> Long count(Class<ENTITY> aClass);
	
	/* Redirect */
	<ENTITY> Controller redirect(Class<ENTITY> aClass,Object identifier,Properties properties);
	<ENTITY> Controller redirect(Class<ENTITY> aClass,Object identifier);
	
	/* Select */
	<ENTITY> Controller select(Class<ENTITY> aClass,Collection<Object> identifiers,Properties properties);
	<ENTITY> Controller select(Class<ENTITY> aClass,Collection<Object> identifiers);
	
	/**/
	
	Controller act(SystemAction systemAction,Data data);
}
