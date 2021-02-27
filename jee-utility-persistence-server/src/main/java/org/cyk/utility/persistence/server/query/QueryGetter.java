package org.cyk.utility.persistence.server.query;

import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface QueryGetter {

	Query get(Class<?> resultClass,String queryIdentifier,String queryValue);
	
	default Query get(Class<?> resultClass,String queryIdentifier) {
		return get(resultClass,queryIdentifier,null);
	}
	
	default Query get(String queryIdentifier) {
		return QueryHelper.getQueries().getBySystemIdentifier(queryIdentifier);
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
		return get(Long.class, QueryIdentifierBuilder.getInstance().build(tupleClass, queryName), queryValue);
	}
	
	default Query getByCount(Class<?> tupleClass,String queryName) {
		return getByCount(tupleClass, queryName, null);
	}
	
	default Query getByCount(Class<?> tupleClass) {
		return getByCount(tupleClass, QueryName.COUNT.getValue(), null);
	}
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements QueryGetter,Serializable {

		@Override
		public Query get(Class<?> resultClass, String queryIdentifier, String queryValue) {
			if(resultClass == null)
				throw new IllegalArgumentException("result class is required");
			if(StringHelper.isBlank(queryIdentifier) && StringHelper.isBlank(queryValue))
				throw new IllegalArgumentException("query identifier or query value is required");
			Query query = null;
			if(StringHelper.isBlank(queryValue)) {
				//no query value specified then get query from registered
				query = get(queryIdentifier);
				if(query == null)
					throw new IllegalArgumentException(String.format("query with identifier %s has not been registered yet", queryIdentifier));
				if(StringHelper.isBlank(query.getValue()))
					throw new IllegalArgumentException(String.format("%s has invalid value : %s", queryIdentifier,query,query.getValue()));
			}else {
				//query value has been specified then register it if not yet done
				if(StringHelper.isBlank(queryIdentifier)) {
					query = Query.build(Query.FIELD_VALUE,queryValue);
					LogHelper.logWarning(String.format("You should assign an identifier to your read query %s(%s)", resultClass.getSimpleName(),queryValue), getClass());
				}else {
					query = get(queryIdentifier);
					if(query == null) {
						query = Query.build(Query.FIELD_IDENTIFIER,queryIdentifier,Query.FIELD_VALUE,queryValue,Query.FIELD_RESULT_CLASS,resultClass);
						QueryHelper.addQueries(query);
					}else {
						if(!queryValue.equalsIgnoreCase(query.getValue()))
							throw new IllegalArgumentException(String.format("%s has already been registered. it cannot be overriden with %s", query,queryValue));
					}
				}
			}
			return query;
		}
	}
	
	/**/
	
	static QueryGetter getInstance() {
		return Helper.getInstance(QueryGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}