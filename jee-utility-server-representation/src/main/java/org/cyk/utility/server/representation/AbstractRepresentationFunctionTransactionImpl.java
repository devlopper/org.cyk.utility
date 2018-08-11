package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.system.action.SystemAction;

public abstract class AbstractRepresentationFunctionTransactionImpl extends AbstractRepresentationFunctionImpl implements RepresentationFunctionTransaction, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public RepresentationFunctionTransaction setAction(SystemAction action) {
		return (RepresentationFunctionTransaction) super.setAction(action);
	}
	
	@Override
	public RepresentationFunctionTransaction setEntity(Object entity) {
		return (RepresentationFunctionTransaction) super.setEntity(entity);
	}
	
	@Override
	public RepresentationFunctionTransaction setEntities(Collection<?> entities) {
		return (RepresentationFunctionTransaction) super.setEntities(entities);
	}
	
	@Override
	public RepresentationFunctionTransaction setResponse(Response response) {
		return (RepresentationFunctionTransaction) super.setResponse(response);
	}
}
