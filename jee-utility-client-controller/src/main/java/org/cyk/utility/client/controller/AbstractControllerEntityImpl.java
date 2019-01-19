package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.proxy.ProxyGetter;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractControllerEntityImpl<ENTITY> extends AbstractControllerServiceProviderImpl<ENTITY> implements ControllerEntity<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	private Class<ENTITY> entityClass;
	private Class<?> representationClass;
	private Class<?> dataTransferClass;
	
	@Override
	protected void __listenBeforePostConstruct__() {
		super.__listenBeforePostConstruct__();
		Class<ENTITY> entityClass = __getEntityClass__();
		if(entityClass == null)
			System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+getClass()+" : controller entity class cannot be derived <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		else
			setEntityClass(entityClass);
	}
	
	protected Class<ENTITY> __getEntityClass__() {
		return (Class<ENTITY>) __injectClassHelper__().getParameterAt(getClass(), 0, Object.class);
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
		return readMany(properties);
	}
	
	@Override
	public Collection<ENTITY> read() {
		//TODO derive properties
		return read(null);
	}
	
	@Override
	public ENTITY readOne(Object identifier, Properties properties) {
		ControllerFunctionReader function = ____inject____(ControllerFunctionReader.class);
		function.setEntityIdentifier(identifier);
		function.setEntityIdentifierValueUsageType(properties == null ? ValueUsageType.SYSTEM : properties.getValueUsageType());
		function.setEntityClass(getEntityClass());
		//function.getAction().getEntities(Boolean.TRUE).add(object);
		function.execute();
		return (ENTITY) function.getEntity();
	}
	
	@Override
	public ENTITY readOne(Object identifier, ValueUsageType valueUsageType) {
		return readOne(identifier, new Properties().setValueUsageType(valueUsageType));
	}
	
	@Override
	public ENTITY readOne(Object identifier) {
		return readOne(identifier,ValueUsageType.SYSTEM);
	}
	
	@Override
	public ENTITY readOneByBusinessIdentifier(Object identifier) {
		return readOne(identifier,ValueUsageType.BUSINESS);
	}
	
	@Override
	public Collection<ENTITY> readMany(Properties properties) {
		/*Response response = getRepresentation().getMany();
		Collection<ENTITY> collection = (Collection<ENTITY>) response.readEntity(__inject__(TypeHelper.class)
				.instanciateGenericCollectionParameterizedTypeForJaxrs(Collection.class,dataTransferClass));
		return collection;
		*/
		
		ControllerFunctionReader function = ____inject____(ControllerFunctionReader.class);
		function.setEntityClass(getEntityClass());
		function.setDataTransferClass(dataTransferClass);
		function.copyProperty(Properties.REQUEST,properties);
		//function.getAction().getEntities(Boolean.TRUE).add(object);
		function.execute();
		return (Collection<ENTITY>) function.getEntities();
	}
	
	@Override
	public Collection<ENTITY> readMany() {
		return readMany(null);
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
