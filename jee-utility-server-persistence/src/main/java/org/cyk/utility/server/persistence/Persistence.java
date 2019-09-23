package org.cyk.utility.server.persistence;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.value.ValueUsageType;

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
	<ENTITY> Collection<ENTITY> readByIdentifiers(Class<ENTITY> aClass,Collection<Object> identifiers,ValueUsageType valueUsageType,Properties properties);
	<ENTITY> Collection<ENTITY> readByIdentifiers(Class<ENTITY> aClass,Collection<Object> identifiers,ValueUsageType valueUsageType);
	
	<ENTITY> ENTITY readByIdentifier(Class<ENTITY> aClass,Object identifier,Properties properties);
	<ENTITY> ENTITY readByIdentifier(Class<ENTITY> aClass,Object identifier);
	<ENTITY> ENTITY readByIdentifier(Class<ENTITY> aClass,Object identifier,ValueUsageType valueUsageType);
	
	<ENTITY> Collection<ENTITY> read(Class<ENTITY> aClass,Properties properties);
	<ENTITY> Collection<ENTITY> read(Class<ENTITY> aClass);
	
	<ENTITY> Collection<Object> readIdentifiers(Class<ENTITY> aClass,ValueUsageType valueUsageType,Properties properties);
	<ENTITY> Collection<Object> readIdentifiers(Class<ENTITY> aClass,ValueUsageType valueUsageType);
	
	/* Update */
	
	/* Delete */
	<ENTITY> Persistence deleteByIdentifiers(Class<ENTITY> aClass,Collection<Object> identifiers,ValueUsageType valueUsageType,Properties properties);
	<ENTITY> Persistence deleteByIdentifiers(Class<ENTITY> aClass,Collection<Object> identifiers,ValueUsageType valueUsageType);
	
	<ENTITY> Persistence deleteBySystemIdentifiers(Class<ENTITY> aClass,Collection<Object> identifiers,Properties properties);
	<ENTITY> Persistence deleteBySystemIdentifiers(Class<ENTITY> aClass,Collection<Object> identifiers);
	
	<ENTITY> Persistence deleteByBusinessIdentifiers(Class<ENTITY> aClass,Collection<Object> identifiers,Properties properties);
	<ENTITY> Persistence deleteByBusinessIdentifiers(Class<ENTITY> aClass,Collection<Object> identifiers);
	
	<ENTITY> Persistence deleteByEntityClass(Class<ENTITY> aClass,Properties properties);
	<ENTITY> Persistence deleteByEntityClass(Class<ENTITY> aClass);
	
	/* Count */
	<ENTITY> Long count(Class<ENTITY> aClass,Properties properties);
	<ENTITY> Long count(Class<ENTITY> aClass);
	
}
