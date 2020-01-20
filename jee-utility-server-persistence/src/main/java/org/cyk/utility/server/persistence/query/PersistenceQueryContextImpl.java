package org.cyk.utility.server.persistence.query;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.persistence.query.filter.Field;
import org.cyk.utility.__kernel__.persistence.query.filter.Filter;

@Deprecated
public class PersistenceQueryContextImpl extends AbstractObject implements PersistenceQueryContext,Serializable {
	private static final long serialVersionUID = 1L;

	private PersistenceQuery query;
	private Filter filter;
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
	public Filter getFilter() {
		return filter;
	}
	
	@Override
	public PersistenceQueryContext setFilter(Filter filter) {
		this.filter = filter;
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
		Filter filter = getFilter();
		return filter == null ? Boolean.FALSE : filter.hasFieldWithPath(keys); //isFilterByKeys(getFilters(),keys);
	}
	
	@Override
	public Object getFilterByKeysValue(String... keys) {
		Filter filter = getFilter();
		return filter == null ? null : filter.getFieldValueByPath(keys);
	}
	
	@Override
	public Field getFilterFieldByKeys(String... keys) {
		Filter filter = getFilter();
		return filter == null ? null : filter.getFieldByPath(keys);
	}
	
	/**/
	
	public static Boolean isFilterByKeys(Filter filter,String... keys) {
		return filter == null ? Boolean.FALSE : filter.hasFieldWithPath(keys);
	}
	
}
