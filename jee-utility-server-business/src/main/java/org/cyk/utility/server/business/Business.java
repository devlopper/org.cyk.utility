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
	<ENTITY> Collection<ENTITY> findByIdentifiers(Class<ENTITY> aClass,Collection<Object> identifiers,ValueUsageType valueUsageType,Properties properties);
	<ENTITY> Collection<ENTITY> findByIdentifiers(Class<ENTITY> aClass,Collection<Object> identifiers,ValueUsageType valueUsageType);
	
	<ENTITY> ENTITY findByIdentifier(Class<ENTITY> aClass,Object identifier,ValueUsageType valueUsageType,Properties properties);
	<ENTITY> ENTITY findByIdentifier(Class<ENTITY> aClass,Object identifier,ValueUsageType valueUsageType);
	
	<ENTITY> Collection<ENTITY> find(Class<ENTITY> aClass,Properties properties);
	<ENTITY> Collection<ENTITY> find(Class<ENTITY> aClass);
	
	<ENTITY> Collection<Object> findIdentifiers(Class<ENTITY> aClass,ValueUsageType valueUsageType,Properties properties);
	<ENTITY> Collection<Object> findIdentifiers(Class<ENTITY> aClass,ValueUsageType valueUsageType);
	
	/* Update */
	
	/* Delete */
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
	Business deleteByClasses(Collection<Class<?>> classes);
	
	@Transactional
	Business deleteByClasses(Class<?>...classes);
	
	@Transactional
	Business deleteAll(Properties properties);
	
	@Transactional
	Business deleteAll();
	
	/* Count */
	<ENTITY> Long count(Class<ENTITY> aClass,Properties properties);
	<ENTITY> Long count(Class<ENTITY> aClass);
	
}
