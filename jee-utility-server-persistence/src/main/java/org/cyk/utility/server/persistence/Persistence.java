package org.cyk.utility.server.persistence;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;

/**
 * Generic persistence functions provider. It tries , first , to find the specific persistence defined for a specific object class.<br/>
 * If no specific persistence defined for a specific class then generic processing takes place.
 * 
 * @author Christian
 *
 */
public interface Persistence extends PersistenceServiceProvider<Object> {

	/* Create */
	
	/* Read */ 
	<ENTITY> ENTITY readOne(Class<ENTITY> aClass,Object identifier,Properties properties);
	<ENTITY> ENTITY readOne(Class<ENTITY> aClass,Object identifier);
	
	<ENTITY> Collection<ENTITY> readMany(Class<ENTITY> aClass,Properties properties);
	<ENTITY> Collection<ENTITY> readMany(Class<ENTITY> aClass);
	
	/* Update */
	
	/* Delete */
	<ENTITY> Persistence deleteAll(Class<ENTITY> aClass,Properties properties);
	<ENTITY> Persistence deleteAll(Class<ENTITY> aClass);
	
	/* Count */
	<ENTITY> Long count(Class<ENTITY> aClass,Properties properties);
	<ENTITY> Long count(Class<ENTITY> aClass);
	
}
