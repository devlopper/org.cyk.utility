package org.cyk.utility.server.persistence;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.system.SystemFunctionServer;
import org.cyk.utility.system.action.SystemAction;

public interface PersistenceFunction extends SystemFunctionServer {

	PersistenceFunction setAction(SystemAction action);
	
	PersistenceFunction setEntityClass(Class<?> aClass);
	
	PersistenceFunction setQueryIdentifier(Object identifier);
	Object getQueryIdentifier();
	
	PersistenceFunction setQueryValue(String value);
	String getQueryValue();
	
	PersistenceFunction setQueryParameters(Properties parameters);
	Properties getQueryParameters();
	PersistenceFunction setQueryParameter(String name,Object value);
	
	PersistenceFunction setQueryResultClass(Class<?> aClass);
	Class<?> getQueryResultClass();
	
	Boolean getIsQueryResultPaginated();
	PersistenceFunction setIsQueryResultPaginated(Boolean isQueryResultPaginated);
	
	Long getQueryFirstTupleIndex();
	PersistenceFunction setQueryFirstTupleIndex(Long queryFirstTupleIndex);
	
	Long getQueryNumberOfTuple();
	PersistenceFunction setQueryNumberOfTuple(Long queryNumberOfTuple);
}
