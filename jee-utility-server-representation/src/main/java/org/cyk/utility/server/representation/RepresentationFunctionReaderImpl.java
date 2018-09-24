package org.cyk.utility.server.representation;

import java.io.Serializable;

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
		if(getEntityIdentifier()!=null) {
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
		}else {
			
		}
		setResponse(responseBuilder.build());
	}

}
