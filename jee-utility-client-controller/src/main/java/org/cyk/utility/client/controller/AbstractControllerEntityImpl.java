package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.proxy.ProxyGetter;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.system.action.SystemAction;
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
		ControllerFunctionReader function = ____inject____(ControllerFunctionReader.class);
		function.setProperty(Properties.IS_MANY, Boolean.TRUE);
		function.setEntityClass(getEntityClass());
		function.setDataTransferClass(dataTransferClass);
		__copyReadProperties__(function, properties);
		//function.getAction().getEntities(Boolean.TRUE).add(object);
		function.execute();
		if(properties!=null) {
			properties.setResponse(function.getProperties().getResponse());
			properties.setAction(function.getProperties().getAction());
		}
		return (Collection<ENTITY>) function.getEntities();
	}
	
	@Override
	public Collection<ENTITY> read() {
		return read(null);
	}
	
	@Override
	public Collection<ENTITY> readByIdentifiers(Collection<Object> identifiers, ValueUsageType valueUsageType,Properties properties) {
		ControllerFunctionReader function = ____inject____(ControllerFunctionReader.class);
		function.setProperty(Properties.IS_MANY, Boolean.TRUE);
		function.setEntityClass(getEntityClass());
		function.setDataTransferClass(dataTransferClass);
		function.getAction().getEntitiesIdentifiers(Boolean.TRUE).add(identifiers);
		__copyReadProperties__(function, properties);
		function.setEntityIdentifierValueUsageType(valueUsageType);
		//function.getAction().getEntities(Boolean.TRUE).add(object);
		function.execute();
		if(properties!=null) {
			properties.setResponse(function.getProperties().getResponse());
			properties.setAction(function.getProperties().getAction());
		}
		return (Collection<ENTITY>) function.getEntities();
	}
	
	@Override
	public Collection<ENTITY> readByIdentifiers(Collection<Object> identifiers, ValueUsageType valueUsageType) {
		return readByIdentifiers(identifiers, valueUsageType, null);
	}
	
	@Override
	public Collection<ENTITY> readBySystemIdentifiers(Collection<Object> identifiers, Properties properties) {
		return readByIdentifiers(identifiers, ValueUsageType.SYSTEM, properties);
	}
	
	@Override
	public Collection<ENTITY> readBySystemIdentifiers(Collection<Object> identifiers) {
		return readBySystemIdentifiers(identifiers, null);
	}
	
	@Override
	public Collection<ENTITY> readByBusinessIdentifiers(Collection<Object> identifiers, Properties properties) {
		return readByIdentifiers(identifiers, ValueUsageType.BUSINESS, properties);
	}
	
	@Override
	public Collection<ENTITY> readByBusinessIdentifiers(Collection<Object> identifiers) {
		return readByBusinessIdentifiers(identifiers, null);
	}
	
	@Override
	public ENTITY readByIdentifier(Object identifier, ValueUsageType valueUsageType, Properties properties) {
		ControllerFunctionReader function = ____inject____(ControllerFunctionReader.class);
		function.setProperty(Properties.IS_MANY, Boolean.FALSE);
		function.setEntityClass(getEntityClass());
		function.setDataTransferClass(dataTransferClass);
		function.setEntityIdentifier(identifier);// getAction().getEntitiesIdentifiers(Boolean.TRUE).add(identifier);
		__copyReadProperties__(function, properties);
		function.setEntityIdentifierValueUsageType(valueUsageType);
		//function.getAction().getEntities(Boolean.TRUE).add(object);
		function.execute();
		if(properties!=null) {
			properties.setResponse(function.getProperties().getResponse());
			properties.setAction(function.getProperties().getAction());
		}
		return (ENTITY) function.getEntity();
	}
	
	@Override
	public ENTITY readByIdentifier(Object identifier, ValueUsageType valueUsageType) {
		return readByIdentifier(identifier, valueUsageType, null);
	}
	
	@Override
	public ENTITY readBySystemIdentifier(Object identifier, Properties properties) {
		return readByIdentifier(identifier, ValueUsageType.SYSTEM, properties);
	}
	
	@Override
	public ENTITY readBySystemIdentifier(Object identifier) {
		return readBySystemIdentifier(identifier, null);
	}
	
	@Override
	public ENTITY readByBusinessIdentifier(Object identifier, Properties properties) {
		return readByIdentifier(identifier, ValueUsageType.BUSINESS, properties);
	}
	
	@Override
	public ENTITY readByBusinessIdentifier(Object identifier) {
		return readByBusinessIdentifier(identifier, null);
	}
	
	@Override
	public Long count(Properties properties) {
		ControllerFunctionCounter function = ____inject____(ControllerFunctionCounter.class);
		function.setEntityClass(getEntityClass());
		function.setDataTransferClass(dataTransferClass);
		__copyCountProperties__(function, properties);
		function.execute();
		if(properties!=null) {
			properties.setResponse(function.getProperties().getResponse());
			properties.setAction(function.getProperties().getAction());
		}
		return (Long) function.getEntitiesCount();
	}
	
	@Override
	public Long count() {
		return count(null);
	}
	
	@Override
	public ControllerServiceProvider<ENTITY> deleteAll(Properties properties) {
		if(properties == null)
			properties = new Properties();
		
		ControllerFunctionRemover function = ____inject____(ControllerFunctionRemover.class);
		function.setProperty(Properties.ALL, Boolean.TRUE);
		function.getAction().setEntityClass(getEntityClass());
		function.copyProperty(Properties.REQUEST,properties);
		function.copyProperty(Properties.CONTEXT,properties);
		function.execute();	
		if(properties!=null) {
			properties.setResponse(function.getProperties().getResponse());
			properties.setAction(function.getProperties().getAction());
		}
		return this;
	}
	
	@Override
	public ControllerEntity<ENTITY> select(Collection<Object> identifiers, Properties properties) {
		return redirect(__inject__(CollectionHelper.class).getFirst(identifiers), properties);
	}
	
	@Override
	public ControllerEntity<ENTITY> select(Collection<Object> identifiers) {
		return select(identifiers,null);
	}
	
	@Override
	public ControllerEntity<ENTITY> redirect(Object identifier, Properties properties) {
		ControllerFunctionRedirector function = __inject__(ControllerFunctionRedirector.class)
				.setTargetSystemAction((SystemAction) Properties.getFromPath(properties, Properties.SYSTEM_ACTION));
		function.setEntityClass(getEntityClass());
		function.getAction().getEntitiesIdentifiers(Boolean.TRUE).add(identifier);
		function.setDataTransferClass(dataTransferClass);
		function.copyProperty(Properties.REQUEST,properties);
		function.copyProperty(Properties.CONTEXT,properties);
		function.copyProperty(Properties.SYSTEM_ACTION,properties);
		//function.getAction().getEntities(Boolean.TRUE).add(object);
		function.execute();
		
		/*ENTITY entity = null;
		try {
			entity = readOneByBusinessIdentifier(identifier);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(entity == null)
			__injectThrowableHelper__().throwRuntimeException("Le code "+identifier+" n'existe pas.");
		else {
			SystemActionRead systemActionRead = __inject__(SystemActionRead.class);
			systemActionRead.setEntityClass(getEntityClass());
			systemActionRead.getEntitiesIdentifiers(Boolean.TRUE).add(__injectFieldHelper__().getFieldValueSystemIdentifier(entity));
			
			NavigationBuilder navigationBuilder = __inject__(NavigationBuilder.class).setIdentifierBuilderSystemAction(systemActionRead);
			Navigation navigation = navigationBuilder.execute().getOutput();
			__inject__(NavigationRedirector.class).setNavigation(navigation).execute();
		}
		*/
		return this;
	}
	
	@Override
	public ControllerEntity<ENTITY> redirect(Object identifier) {
		Properties properties = new Properties().setValueUsageType(ValueUsageType.BUSINESS);
		return redirect(identifier, properties);
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
