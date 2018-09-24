package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.value.ValueUsageType;

public class RepresentationFunctionReaderImpl extends AbstractRepresentationFunctionReaderImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __execute__(SystemAction action) {
		ResponseBuilder responseBuilder = null;
		if(getEntityIdentifier()!=null) {//specific identifiers
			ValueUsageType valueUsageType = getEntityIdentifierValueUsageType();
			Object identifier = getEntityIdentifier();
			if(ValueUsageType.SYSTEM.equals(valueUsageType))
				identifier = __injectNumberHelper__().getLong(identifier);
			
			Object entity = __injectInstanceHelper__().buildOne(getEntityClass(),__injectBusiness__().findOne(getPersistenceEntityClass(),identifier,new Properties()
					.setValueUsageType(valueUsageType)));
			
			if(entity == null)
				responseBuilder = Response.status(Response.Status.NOT_FOUND);
			else {
				responseBuilder = Response.status(Response.Status.OK).entity(new GenericEntity<Object>(entity, getEntityClass()));
			}
		}else {// no specific identifiers
			//TODO handle pagination
			List<?> entities = (List<?>) __injectInstanceHelper__().buildMany(getEntityClass(),__injectBusiness__().findMany(getPersistenceEntityClass()/* properties */));
			if(entities == null)
				entities = new ArrayList<>();
			GenericEntity<List<?>> genericEntity = new GenericEntity<List<?>>(entities,(Type) __injectTypeHelper__()
					.instanciateGenericCollectionParameterizedTypeForJaxrs(List.class, getEntityClass()));
			RepresentationFunctionReader function = __inject__(RepresentationFunctionReader.class);
			function.execute().getResponse();
			responseBuilder = Response.status(Response.Status.OK).entity(genericEntity);
			
		}
		setResponse(responseBuilder.build());
	}

}
