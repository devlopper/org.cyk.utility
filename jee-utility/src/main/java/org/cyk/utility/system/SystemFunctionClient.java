package org.cyk.utility.system;

import java.util.Collection;

public interface SystemFunctionClient extends SystemFunction {

	SystemFunctionClient setActionEntityClass(Class<?> entityClass);
	SystemFunctionClient addActionEntities(Object...entities);
	
	SystemFunctionClient setActionEntityIdentifierClass(Class<?> entityIdentifierClass);
	SystemFunctionClient addActionEntitiesIdentifiers(Collection<Object> entitiesIdentifiers);
	SystemFunctionClient addActionEntitiesIdentifiers(Object...entitiesIdentifiers);

	Class<?> getDataTransferClass();
	SystemFunctionClient setDataTransferClass(Class<?> dataTransferClass);

}
