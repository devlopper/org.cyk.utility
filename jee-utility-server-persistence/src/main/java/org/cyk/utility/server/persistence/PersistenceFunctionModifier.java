package org.cyk.utility.server.persistence;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.value.ValueUsageType;

public interface PersistenceFunctionModifier extends PersistenceFunctionTransaction {

	PersistenceFunctionModifier setAction(SystemAction action);
	
	PersistenceFunctionModifier setEntityClass(Class<?> aClass);
	
	PersistenceFunctionModifier setEntityIdentifier(Object identifier);
	
	PersistenceFunctionModifier setEntityIdentifierValueUsageType(ValueUsageType valueUsageType);
	
	PersistenceFunctionModifier setQueryIdentifier(Object identifier);
	
	PersistenceFunctionModifier setQueryParameters(Properties parameters);
	
	PersistenceFunctionModifier setQueryParameter(String name, Object value);
	
}
