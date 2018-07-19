package org.cyk.utility.server.business;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;

/**
 * 
 * @author Christian
 *
 */
public interface Business extends BusinessServiceProvider<Object> {

	/* Create */
	
	/* Find */ 
	<ENTITY> ENTITY findOne(Class<ENTITY> aClass,Object identifier,Properties properties);
	<ENTITY> ENTITY findOne(Class<ENTITY> aClass,Object identifier);
	
	<ENTITY> Collection<ENTITY> findMany(Class<ENTITY> aClass,Properties properties);
	<ENTITY> Collection<ENTITY> findMany(Class<ENTITY> aClass);
	
	/* Update */
	
	/* Delete */
	
	/* Count */
	<ENTITY> Long count(Class<ENTITY> aClass,Properties properties);
	<ENTITY> Long count(Class<ENTITY> aClass);
	
}
