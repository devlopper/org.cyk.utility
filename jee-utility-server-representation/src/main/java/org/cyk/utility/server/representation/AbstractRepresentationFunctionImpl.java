package org.cyk.utility.server.representation;

import java.io.Serializable;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.business.Business;
import org.cyk.utility.system.AbstractSystemFunctionServerImpl;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.layer.SystemLayer;
import org.cyk.utility.system.layer.SystemLayerRepresentation;

public abstract class AbstractRepresentationFunctionImpl extends AbstractSystemFunctionServerImpl implements RepresentationFunction, Serializable {
	private static final long serialVersionUID = 1L;
	
	protected Throwable __throwable__;
	protected ResponseBuilder __responseBuilder__;
	
	@Override
	protected void __execute__(SystemAction action) {
		__responseBuilder__ = __instanciateResponseBuilder__();
		try {
			__executeBusiness__();
		} catch (Exception exception) {
			__throwable__ = exception;
		} finally {
			__processResponseBuilder__();
		}
		setResponse(__responseBuilder__.build());
	}
	
	protected abstract void __executeBusiness__();
	
	protected ResponseBuilder __instanciateResponseBuilder__() {
		return Response.noContent();
	}
	
	protected void __processResponseBuilder__() {
		__responseBuilder__.status(__computeResponseStatus__()).entity(__computeResponseEntity__());
	}
	
	protected Response.Status __computeResponseStatus__(){
		if(__throwable__==null)
			return Response.Status.OK;
		return Response.Status.INTERNAL_SERVER_ERROR;
	}
	
	protected Object __computeResponseEntity__(){
		ResponseEntityDto responseEntityDto = new ResponseEntityDto();
		MessageDto messageDto = new MessageDto();
		if(__throwable__==null) {
			responseEntityDto.setStatus("SUCCESS");
			messageDto.setValue(getPersistenceEntityClass().getSimpleName()+" a été créé avec succès.");
		}else {
			responseEntityDto.setStatus("FAILURE");
			Throwable cause = __injectThrowableHelper__().getFirstCause(__throwable__);	
			messageDto.setValue("Une erreur est survenue lors de "+getAction().getIdentifier()+" de "+getPersistenceEntityClass()+". "+cause.getMessage());
		}
		responseEntityDto.addMessage(messageDto);
		return responseEntityDto;
	}
	
	protected void __processResponseBuilder__(ResponseBuilder builder,Throwable throwable) {
		builder.status(__computeResponseStatus__()).entity(__computeResponseEntity__());
	}
	
	@Override
	public RepresentationFunction execute() {
		return (RepresentationFunction) super.execute();
	}
	
	@Override
	public Class<?> getPersistenceEntityClass() {
		return (Class<?>) getProperties().getFromPath(Properties.PERSISTENCE,Properties.ENTITY_CLASS);
	}
	
	@Override
	public RepresentationFunction setPersistenceEntityClass(Class<?> aClass) {
		getProperties().setFromPath(new Object[] {Properties.PERSISTENCE,Properties.ENTITY_CLASS},aClass);
		return this;
	}
	
	@Override
	public Response getResponse() {
		return (Response) getProperties().getResponse();
	}
	
	@Override
	public RepresentationFunction setResponse(Response response) {
		getProperties().setResponse(response);
		return this;
	}
	
	@Override
	public RepresentationFunction setEntity(Object entity) {
		return (RepresentationFunction) super.setEntity(entity);
	}
	
	@Override
	public RepresentationFunction setEntityIdentifierValueUsageType(Object valueUsageType) {
		return (RepresentationFunction) super.setEntityIdentifierValueUsageType(valueUsageType);
	}
	
	@Override
	public RepresentationFunction setEntityIdentifier(Object identifier) {
		return (RepresentationFunction) super.setEntityIdentifier(identifier);
	}
	
	@Override
	public RepresentationFunction setAction(SystemAction action) {
		return (RepresentationFunction) super.setAction(action);
	}
	
	@Override
	public RepresentationFunction setEntityClass(Class<?> aClass) {
		return (RepresentationFunction) super.setEntityClass(aClass);
	}
	
	@Override
	protected SystemLayer getSystemLayer() {
		return __inject__(SystemLayerRepresentation.class);
	}

	protected Business __injectBusiness__() {
		return __inject__(Business.class);
	}
}
