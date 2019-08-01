package org.cyk.utility.server.persistence.query;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.server.persistence.query.filter.Filter;

public interface PersistenceQueryContext extends Objectable {

	PersistenceQuery getQuery();
	PersistenceQueryContext setQuery(PersistenceQuery query);
	
	Filter getFilter();
	PersistenceQueryContext setFilter(Filter filter);

	Object[] getParameters();
	PersistenceQueryContext setParameters(Object[] parameters);
	
	
	Boolean isFilterByKeys(String...keys);
	Object getFilterByKeysValue(String...keys);
	
}
