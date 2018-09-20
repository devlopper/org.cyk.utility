package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.cyk.utility.instance.InstanceHelper;
import org.cyk.utility.server.business.Business;
import org.cyk.utility.system.action.SystemAction;

public class RepresentationFunctionCreatorImpl extends AbstractRepresentationFunctionCreatorImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __execute__(SystemAction action) {
		ResponseBuilder responseBuilder;
		if(getEntities()!=null)
			__inject__(Business.class).createMany((Collection<Object>) __inject__(InstanceHelper.class).buildMany(getPersistenceEntityClass(),getEntities()));
		else if(getEntity()!=null)
			__inject__(Business.class).create(__inject__(InstanceHelper.class).buildOne(getPersistenceEntityClass(),getEntity()));
		else {
			System.err.println("No entity to "+action);
		}
		responseBuilder = Response.status(Response.Status.CREATED);
		setResponse(responseBuilder.build());
	}

}
