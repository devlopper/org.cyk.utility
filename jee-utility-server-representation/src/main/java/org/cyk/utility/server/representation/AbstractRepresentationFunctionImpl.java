package org.cyk.utility.server.representation;

import java.io.Serializable;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.system.AbstractSystemFunctionServerImpl;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.layer.SystemLayer;
import org.cyk.utility.system.layer.SystemLayerRepresentation;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractRepresentationFunctionImpl extends AbstractSystemFunctionServerImpl implements RepresentationFunction, Serializable {
	private static final long serialVersionUID = 1L;
	
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
	public RepresentationFunction setEntityIdentifierValueUsageType(ValueUsageType valueUsageType) {
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

}
