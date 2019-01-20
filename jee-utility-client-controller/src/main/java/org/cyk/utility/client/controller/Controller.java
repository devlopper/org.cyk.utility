package org.cyk.utility.client.controller;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.value.ValueUsageType;

public interface Controller extends ControllerServiceProvider<Object> {

	/* Create */
	
	/* Read */ 
	<ENTITY> ENTITY readOne(Class<ENTITY> aClass,Object identifier,Properties properties);
	<ENTITY> ENTITY readOne(Class<ENTITY> aClass,Object identifier);
	
	<ENTITY> Collection<ENTITY> readMany(Class<ENTITY> aClass,Properties properties);
	<ENTITY> Collection<ENTITY> readMany(Class<ENTITY> aClass);
	
	/* Update */
	
	/* Delete */
	Controller deleteAll(Collection<Class<?>> classes);
	
	Controller deleteAll(Class<?>...classes);
	
	<ENTITY> Controller deleteByClassByIdentififerByValueUsageType(Class<ENTITY> clazz,Object identifier,ValueUsageType valueUsageType);
	
	/* Count */
	<ENTITY> Long count(Class<ENTITY> aClass,Properties properties);
	<ENTITY> Long count(Class<ENTITY> aClass);
	
	/* Redirect */
	<ENTITY> Controller redirect(Class<ENTITY> aClass,Object identifier,Properties properties);
	<ENTITY> Controller redirect(Class<ENTITY> aClass,Object identifier);
	
}
