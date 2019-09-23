package org.cyk.utility.server.persistence;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.__kernel__.value.ValueUsageType;

public interface PersistenceFunctionTransaction extends PersistenceFunction {

	PersistenceFunctionTransaction setEntity(Object entity);
	
	PersistenceFunctionTransaction setEntities(Collection<?> entities);
	
	PersistenceFunctionTransaction setAction(SystemAction action);
	
	PersistenceFunctionTransaction setEntityClass(Class<?> aClass);
	
	PersistenceFunctionTransaction setEntityIdentifier(Object identifier);
	
	PersistenceFunctionTransaction setEntityIdentifierValueUsageType(ValueUsageType valueUsageType);
	
	PersistenceFunctionTransaction setQueryIdentifier(Object identifier);
	
	PersistenceFunctionTransaction setQueryParameters(Properties parameters);
	
	PersistenceFunctionTransaction setQueryParameter(String name, Object value);
}
