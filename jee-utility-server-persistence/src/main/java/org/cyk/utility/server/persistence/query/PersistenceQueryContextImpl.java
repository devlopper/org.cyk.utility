package org.cyk.utility.server.persistence.query;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.array.ArrayHelper;

public class PersistenceQueryContextImpl extends AbstractObject implements PersistenceQueryContext,Serializable {
	private static final long serialVersionUID = 1L;

	private PersistenceQuery query;
	private Map<String, Object> filters;
	private Object[] parameters;
	
	@Override
	public PersistenceQuery getQuery() {
		return query;
	}

	@Override
	public PersistenceQueryContext setQuery(PersistenceQuery query) {
		this.query = query;
		return this;
	}

	@Override
	public Map<String, Object> getFilters() {
		return filters;
	}

	@Override
	public PersistenceQueryContext setFilters(Map<String, Object> filters) {
		this.filters = filters;
		return this;
	}

	@Override
	public Object[] getParameters() {
		return parameters;
	}

	@Override
	public PersistenceQueryContext setParameters(Object[] parameters) {
		this.parameters = parameters;
		return this;
	}

	@Override
	public Boolean isFilterByKeys(String... keys) {
		return isFilterByKeys(getFilters(),keys);
	}
	
	@Override
	public Object getFilterByKeysValue(String... keys) {
		if(Boolean.TRUE.equals(__inject__(ArrayHelper.class).isNotEmpty(keys))) {
			Map<String, Object> filters = getFilters();
			if(filters != null) {
				for(String index : keys) {
					return filters.get(index);
				}
			}
		}
		return null;
	}
	
	/**/
	
	public static Boolean isFilterByKeys(Map<String,Object> filters,String... keys) {
		Boolean isFilterByKeys = null;
		if(filters != null && Boolean.TRUE.equals(__inject__(ArrayHelper.class).isNotEmpty(keys))) {
			for(String index : keys) {
				if(filters.containsKey(index)) {
					isFilterByKeys = Boolean.TRUE;
					break;
				}
			}
		}
		return Boolean.TRUE.equals(isFilterByKeys);
	}
}
