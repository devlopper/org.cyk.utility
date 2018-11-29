package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.proxy.ProxyGetter;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.type.TypeHelper;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractControllerEntityImpl<ENTITY> extends AbstractControllerServiceProviderImpl<ENTITY> implements ControllerEntity<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	private Class<ENTITY> entityClass;
	private Class<?> representationClass;
	private Class<?> dataTransferClass;
	
	@Override
	protected void __listenBeforePostConstruct__() {
		super.__listenBeforePostConstruct__();
		setEntityClass((Class<ENTITY>) __injectClassHelper__().getParameterAt(getClass(), 0, Object.class));
	}
	
	@Override
	public Class<ENTITY> getEntityClass() {
		return entityClass;
	}
	
	@Override
	public ControllerEntity<ENTITY> setEntityClass(Class<ENTITY> entityClass) {
		this.entityClass = entityClass;
		if(this.entityClass == null) {
			representationClass = null;
			dataTransferClass = null;
		}else {
			representationClass = __inject__(ControllerLayer.class).getDataRepresentationClassFromEntityClass(this.entityClass);
			dataTransferClass = __inject__(ControllerLayer.class).getDataTransferClassFromEntityClass(this.entityClass);
		}
		return this;
	}
	
	@Override
	public Collection<ENTITY> read(Properties properties) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Collection<ENTITY> read() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ENTITY readOne(Object identifier, Properties properties) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ENTITY readOne(Object identifier, ValueUsageType valueUsageType) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ENTITY readOne(Object identifier) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ENTITY readOneByBusinessIdentifier(Object identifier) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Collection<ENTITY> readMany(Properties properties) {
		Response response = getRepresentation().getMany();
		Collection<ENTITY> collection = (Collection<ENTITY>) response.readEntity(__inject__(TypeHelper.class)
				.instanciateGenericCollectionParameterizedTypeForJaxrs(Collection.class,dataTransferClass));
		return collection;
	}
	
	@Override
	public Collection<ENTITY> readMany() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**/
	
	protected RepresentationEntity<?, ?, ?> getRepresentation(){
		RepresentationEntity<?, ?, ?> representation = null;
		if(representationClass == null) {
			__injectThrowableHelper__().throwRuntimeException("No representation class found for "+getEntityClass());
		}else {
			representation = (RepresentationEntity<?, ?, ?>) __inject__(ProxyGetter.class).setClazz(representationClass).execute().getOutput();
		}
		return representation;
	}
	
}
