package org.cyk.utility.server.persistence;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.system.SystemServiceProvider;

public interface Persistence extends SystemServiceProvider {

	/* Create */
	Persistence create(Object entity,Properties properties);
	Persistence create(Object entity);
	
	/* Read */ 
	<ENTITY> ENTITY read(Class<ENTITY> aClass,Object identifier,Properties properties);
	<ENTITY> ENTITY read(Class<ENTITY> aClass,Object identifier);
	
	<ENTITY> Collection<ENTITY> readAll(Class<ENTITY> aClass,Properties properties);
	<ENTITY> Collection<ENTITY> readAll(Class<ENTITY> aClass);
	
	/* Update */
	Persistence update(Object entity,Properties properties);
	Persistence update(Object entity);
	
	/* Delete */
	Persistence delete(Object entity,Properties properties);
	Persistence delete(Object entity);
	
	/* Count */
	<ENTITY> Long countAll(Class<ENTITY> aClass,Properties properties);
	<ENTITY> Long countAll(Class<ENTITY> aClass);
	
}
