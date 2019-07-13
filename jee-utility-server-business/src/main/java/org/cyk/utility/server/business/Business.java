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
	<ENTITY> ENTITY findByIdentifier(Class<ENTITY> aClass,Object identifier,ValueUsageType valueUsageType,Properties properties);
	<ENTITY> ENTITY findByIdentifier(Class<ENTITY> aClass,Object identifier,ValueUsageType valueUsageType);
	
	<ENTITY> Collection<ENTITY> find(Class<ENTITY> aClass,Properties properties);
	<ENTITY> Collection<ENTITY> find(Class<ENTITY> aClass);
	
	/* Update */
	
	/* Delete */
	@Transactional
	Business deleteAll(Collection<Class<?>> classes);
	
	@Transactional
	Business deleteAll(Class<?>...classes);
	
	@Transactional
	Business deleteByIdentifiers(Class<?> klass,Collection<Object> identifiers,ValueUsageType valueUsageType,Properties properties);
	@Transactional
	Business deleteByIdentifiers(Class<?> klass,Collection<Object> identifiers,ValueUsageType valueUsageType);
	
	@Transactional
	Business deleteBySystemIdentifiers(Class<?> klass,Collection<Object> identifiers,Properties properties);
	@Transactional
	Business deleteBySystemIdentifiers(Class<?> klass,Collection<Object> identifiers);
	
	@Transactional
	Business deleteByBusinessIdentifiers(Class<?> klass,Collection<Object> identifiers,Properties properties);
	@Transactional
	Business deleteByBusinessIdentifiers(Class<?> klass,Collection<Object> identifiers);
	
	@Transactional
	<ENTITY> Business deleteByClassByIdentififerByValueUsageType(Class<ENTITY> clazz,Object identifier,ValueUsageType valueUsageType);
	
	/* Count */
	<ENTITY> Long count(Class<ENTITY> aClass,Properties properties);
	<ENTITY> Long count(Class<ENTITY> aClass);
	
}
