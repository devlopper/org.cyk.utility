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
		if(getEntityIdentifier()!=null) {//specific identifiers
			ValueUsageType valueUsageType = getEntityIdentifierValueUsageType();
			Object identifier = getEntityIdentifier();
			if(ValueUsageType.SYSTEM.equals(valueUsageType) /*&& !(identifier instanceof Long)*/) {
				//TODO should depend on identifier field type
				//identifier = __injectNumberHelper__().getLong(identifier);
			}
				
			entity = __injectInstanceHelper__().buildOne(getEntityClass(),__injectBusiness__().findOne(getPersistenceEntityClass(),identifier,new Properties()
					.setValueUsageType(valueUsageType)),new Properties().setFields(entityFieldNames == null ? null : entityFieldNames.get()));			
		}else {// no specific identifiers
			//TODO handle pagination
			Collection<?> collection = __injectBusiness__().findMany(getPersistenceEntityClass()/* properties */);
			entities = __injectCollectionHelper__().isEmpty(collection) ? null : (List<?>) __injectInstanceHelper__().buildMany(getEntityClass(),collection,
					new Properties().setFields(entityFieldNames == null ? null : entityFieldNames.get()));
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
