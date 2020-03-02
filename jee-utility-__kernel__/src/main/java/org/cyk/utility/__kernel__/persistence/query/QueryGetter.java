package org.cyk.utility.__kernel__.persistence.query;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

public interface QueryGetter {

	Query get(Class<?> resultClass,String queryIdentifier,String queryValue);
	
	default Query get(Class<?> resultClass,String queryIdentifier) {
		return get(resultClass,queryIdentifier,null);
	}
	
	/* Select */
	
	default Query getBySelect(Class<?> tupleClass,String queryName,String queryValue) {
		return get(tupleClass, QueryIdentifierBuilder.getInstance().build(tupleClass, queryName), queryValue);
	}
	
	default Query getBySelect(Class<?> tupleClass,String queryName) {
		return getBySelect(tupleClass, queryName, null);
	}
	
	default Query getBySelect(Class<?> tupleClass) {
		return getBySelect(tupleClass, QueryName.READ.getValue(), null);
	}
	
	default Query getBySelectBySystemIdentifiers(Class<?> tupleClass) {
		return getBySelect(tupleClass, QueryName.READ_BY_SYSTEM_IDENTIFIERS.getValue(), null);
	}
	
	default Query getBySelectByBusinessIdentifiers(Class<?> tupleClass) {
		return getBySelect(tupleClass, QueryName.READ_BY_BUSINESS_IDENTIFIERS.getValue(), null);
	}
	
	/* Count */
	
	default Query getByCount(Class<?> tupleClass,String queryName,String queryValue) {
		return get(tupleClass, QueryIdentifierBuilder.getInstance().build(tupleClass, queryName), queryValue);
	}
	
	default Query getByCount(Class<?> tupleClass,String queryName) {
		return getByCount(tupleClass, queryName, null);
	}
	
	default Query getByCount(Class<?> tupleClass) {
		return getByCount(tupleClass, QueryName.COUNT.getValue(), null);
	}
	
	/**/
	
	static QueryGetter getInstance() {
		return Helper.getInstance(QueryGetter.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
}
