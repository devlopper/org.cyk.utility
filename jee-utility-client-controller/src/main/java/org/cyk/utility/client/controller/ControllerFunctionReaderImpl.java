package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.data.DataRepresentationClassGetter;
import org.cyk.utility.client.controller.data.DataTransferObjectClassGetter;
import org.cyk.utility.client.controller.proxy.ProxyGetter;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionRead;

public class ControllerFunctionReaderImpl extends AbstractControllerFunctionImpl implements ControllerFunctionReader , Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionRead.class));
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected void __execute__(SystemAction action) {
		Class<?> dataRepresentationClass = __inject__(DataRepresentationClassGetter.class).setDataClass(action.getEntityClass()).execute().getOutput();
		RepresentationEntity representation = (RepresentationEntity) __inject__(ProxyGetter.class).setClazz(dataRepresentationClass).execute().getOutput();	
		representation.getMany();
		
		Class<?> dataTransferClass = __inject__(DataTransferObjectClassGetter.class).setDataClass(action.getEntityClass()).execute().getOutput();
		Collection<?> dataTransferObjects = __injectInstanceHelper__().buildMany(dataTransferClass, action.getEntities().get());
	}
	
	@Override
	public ControllerFunctionReader setActionEntityIdentifierClass(Class<?> entityIdentifierClass) {
		return (ControllerFunctionReader) super.setActionEntityIdentifierClass(entityIdentifierClass);
	}
	
	@Override
	public ControllerFunctionReader addActionEntitiesIdentifiers(Collection<Object> entitiesIdentifiers) {
		return (ControllerFunctionReader) super.addActionEntitiesIdentifiers(entitiesIdentifiers);
	}
	
	@Override
	public ControllerFunctionReader addActionEntitiesIdentifiers(Object... entitiesIdentifiers) {
		return (ControllerFunctionReader) super.addActionEntitiesIdentifiers(entitiesIdentifiers);
	}

}
