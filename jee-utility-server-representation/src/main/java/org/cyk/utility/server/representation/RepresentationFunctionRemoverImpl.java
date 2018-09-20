package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.instance.InstanceHelper;
import org.cyk.utility.server.business.Business;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.value.ValueUsageType;

public class RepresentationFunctionRemoverImpl extends AbstractRepresentationFunctionRemoverImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __execute__(SystemAction action) {
		ResponseBuilder responseBuilder;
		if(getEntities()!=null)
			__inject__(Business.class).deleteMany((Collection<Object>) __inject__(InstanceHelper.class).buildMany(getPersistenceEntityClass(),getEntities()));
		else if(getEntity()!=null)
			__inject__(Business.class).delete(__inject__(InstanceHelper.class).buildOne(getPersistenceEntityClass(),getEntity()));
		else {
			Object identifier = getEntityIdentifier();
			ValueUsageType valueUsageType = getEntityIdentifierValueUsageType();
			if(ValueUsageType.SYSTEM.equals(valueUsageType))
				identifier = __injectNumberHelper__().getLong(identifier);
			Object entity = __inject__(Business.class).findOne(getPersistenceEntityClass(), identifier, new Properties().setValueUsageType(valueUsageType));
			__inject__(Business.class).delete(__inject__(InstanceHelper.class).buildOne(getPersistenceEntityClass(),entity));
		}
		responseBuilder = Response.status(Response.Status.OK);
		setResponse(responseBuilder.build());
	}
	
}
