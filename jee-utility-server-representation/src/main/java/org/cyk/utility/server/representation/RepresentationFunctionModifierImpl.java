package org.cyk.utility.server.representation;

import java.io.Serializable;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.cyk.utility.system.action.SystemAction;

public class RepresentationFunctionModifierImpl extends AbstractRepresentationFunctionModifierImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __execute__(SystemAction action) {
		ResponseBuilder responseBuilder;
		if(getEntities()!=null)
			;
		else if(getEntity()!=null) {
			Object entity = __injectBusiness__().findOne(getPersistenceEntityClass(), __injectNumberHelper__().getLong(
					__injectFieldHelper__().getFieldValueSystemIdentifier(getEntity())));
			__injectBusiness__().update(entity);
		}else {
			
		}
		responseBuilder = Response.status(Response.Status.OK);
		setResponse(responseBuilder.build());
	}
	
}
