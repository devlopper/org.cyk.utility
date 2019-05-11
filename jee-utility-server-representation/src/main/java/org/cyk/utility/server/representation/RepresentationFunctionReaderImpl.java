package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.string.Strings;
import org.cyk.utility.value.ValueUsageType;

public class RepresentationFunctionReaderImpl extends AbstractRepresentationFunctionReaderImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Object entity;
	private List<?> entities;
	
	@Override
	protected void __executeBusiness__() {
		Strings entityFieldNames = getEntityFieldNames();
		Properties properties = new Properties().setFields(entityFieldNames);
		if(getEntityIdentifier()!=null) {//specific identifiers
			ValueUsageType valueUsageType = getEntityIdentifierValueUsageType();
			Object identifier = getEntityIdentifier();
			if(ValueUsageType.SYSTEM.equals(valueUsageType) /*&& !(identifier instanceof Long)*/) {
				//TODO should depend on identifier field type
				//identifier = __injectNumberHelper__().getLong(identifier);
			}
			
			properties.setValueUsageType(valueUsageType);
			entity = __injectInstanceHelper__().buildOne(getEntityClass(),__injectBusiness__().findOne(getPersistenceEntityClass(),identifier,properties),new Properties().setFields(entityFieldNames == null ? null : entityFieldNames.get()));			
		}else {// no specific identifiers
			//In order to take less execution time and data size , we will set default values if not set by caller.
			properties.copyFrom(getProperties(), Properties.IS_QUERY_RESULT_PAGINATED, Properties.QUERY_FIRST_TUPLE_INDEX,Properties.QUERY_NUMBER_OF_TUPLE
					,Properties.QUERY_FILTERS);
			if(properties.getIsQueryResultPaginated() == null)
				properties.setIsQueryResultPaginated(Boolean.TRUE); //yes we paginate
			if(properties.getQueryFirstTupleIndex() == null)
				properties.setQueryFirstTupleIndex(0); // first page
			if(properties.getQueryNumberOfTuple() == null)
				properties.setQueryNumberOfTuple(5); // 5 results
			Collection<?> collection = __injectBusiness__().findMany(getPersistenceEntityClass(), properties);
			entities = __injectCollectionHelper__().isEmpty(collection) ? null : (List<?>) __injectInstanceHelper__().buildMany(getEntityClass(),collection,
					new Properties().setFields(entityFieldNames == null ? null : entityFieldNames.get()));
			
			__responseBuilder__.header("X-Total-Count", __injectBusiness__().count(getPersistenceEntityClass(), properties));
			/*
			HttpServletRequest request = __inject__(HttpServletRequest.class);
			String uri = __inject__(UniformResourceIdentifierStringBuilder.class).setRequest(request).execute().getOutput();
			__responseBuilder__.link(uri, "next");
			*/
		}
	}
	
	@Override
	protected Status __computeResponseStatus__() {
		if(getEntityIdentifier()!=null) {
			if(entity == null)
				return Response.Status.NOT_FOUND;	
		}
		return super.__computeResponseStatus__();
	}
	
	@Override
	protected Object __computeResponseEntity__() {
		if(__throwable__ == null) {
			if(getEntityIdentifier()!=null) {
				if(entity == null)
					return null;
				else
					return new GenericEntity<Object>(entity, getEntityClass());	
			}else {
				if(entities == null)
					return null;
				else {
					return new GenericEntity<List<?>>(entities,(Type) __injectTypeHelper__().instanciateCollectionParameterizedType(List.class, getEntityClass()));
				}
			}	
		}else
			return super.__computeResponseEntity__();
	}
	
}
