package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.value.ValueUsageType;

public class RepresentationFunctionReaderImpl extends AbstractRepresentationFunctionReaderImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Object entity;
	private List<?> entities;
	
	@Override
	protected void __executeBusiness__() {
		if(getEntityIdentifier()!=null) {//specific identifiers
			ValueUsageType valueUsageType = getEntityIdentifierValueUsageType();
			Object identifier = getEntityIdentifier();
			if(ValueUsageType.SYSTEM.equals(valueUsageType) && !(identifier instanceof Long))
				identifier = __injectNumberHelper__().getLong(identifier);
			
			entity = __injectInstanceHelper__().buildOne(getEntityClass(),__injectBusiness__().findOne(getPersistenceEntityClass(),identifier,new Properties()
					.setValueUsageType(valueUsageType)));			
		}else {// no specific identifiers
			//TODO handle pagination
			entities = (List<?>) __injectInstanceHelper__().buildMany(getEntityClass(),__injectBusiness__().findMany(getPersistenceEntityClass()/* properties */));
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
				else
					return new GenericEntity<List<?>>(entities,(Type) __injectTypeHelper__().instanciateGenericCollectionParameterizedTypeForJaxrs(List.class, getEntityClass()));	
			}	
		}else
			return super.__computeResponseEntity__();
	}
	
}
