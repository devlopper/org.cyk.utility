package org.cyk.utility.server.persistence.query;

import java.util.Map;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface PersistenceQueryContext extends Objectable {

	PersistenceQuery getQuery();
	PersistenceQueryContext setQuery(PersistenceQuery query);
	
	Map<String,Object> getFilters();
	PersistenceQueryContext setFilters(Map<String,Object> filters);
	
	Object[] getParameters();
	PersistenceQueryContext setParameters(Object[] parameters);
	
	Boolean isFilterByKeys(String...keys);
	Object getFilterByKeysValue(String...keys);
}
