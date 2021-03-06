package org.cyk.utility.server.business;

import java.util.Collection;

import javax.transaction.Transactional;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.value.ValueUsageType;

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
	@Transactional
	Business deleteAll(Collection<Class<?>> classes);
	
	@Transactional
	Business deleteAll(Class<?>...classes);
	
	@Transactional
	<ENTITY> Business deleteByClassByIdentififerByValueUsageType(Class<ENTITY> clazz,Object identifier,ValueUsageType valueUsageType);
	
	/* Count */
	<ENTITY> Long count(Class<ENTITY> aClass,Properties properties);
	<ENTITY> Long count(Class<ENTITY> aClass);
	
}
