package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.identifier.resource.ProxyGetter;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;
import org.cyk.utility.persistence.query.QueryIdentifierBuilder;
import org.cyk.utility.persistence.query.Filter;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.server.persistence.PersistenceEntity;
import org.cyk.utility.server.representation.RepresentationEntity;

public abstract class AbstractControllerEntityImpl<ENTITY> extends AbstractControllerServiceProviderImpl<ENTITY> implements ControllerEntity<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	protected Class<ENTITY> __entityClass__;
	protected Class<?> __representationClass__;
	protected Class<?> __dataTransferClass__;
	
	@Override
	protected void __listenBeforePostConstruct__() {
		super.__listenBeforePostConstruct__();
		if(__entityClass__ == null) {
			String name = StringUtils.substringBefore(getClass().getName() , "ControllerImpl");
			name = StringUtils.replaceOnce(name, ".api.", ".entities.");
			__entityClass__ = (Class<ENTITY>) ClassHelper.getByName(name);
		}
		if(__entityClass__ == null)
			System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+getClass()+" : controller entity class cannot be derived <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		else {
			if(__representationClass__ == null)
				__representationClass__ = __inject__(ControllerLayer.class).getDataRepresentationClassFromEntityClass(__entityClass__);
			if(__dataTransferClass__ == null)
				__dataTransferClass__ = __inject__(ControllerLayer.class).getDataTransferClassFromEntityClass(__entityClass__);
		}
	}
	
	@Override
	public Collection<ENTITY> read(Properties properties) {
		ControllerFunctionReader function = ____inject____(ControllerFunctionReader.class);
		function.setProperty(Properties.IS_MANY, Boolean.TRUE);
		function.setEntityClass(__entityClass__);
		function.setDataTransferClass(__dataTransferClass__);
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
		function.setEntityClass(__entityClass__);
		function.setDataTransferClass(__dataTransferClass__);
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
		function.setEntityClass(__entityClass__);
		function.setDataTransferClass(__dataTransferClass__);
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
	/*
	@Override
	public ENTITY readBySystemIdentifierFromRequestParameter(Properties properties) {
		return readByIdentifier(identifier, ValueUsageType.SYSTEM, properties);
	}
	
	@Override
	public ENTITY readBySystemIdentifierFromRequestParameter() {
		return readBySystemIdentifier(identifier, null);
	}
	*/
	@Override
	public ENTITY readByBusinessIdentifier(Object identifier, Properties properties) {
		return readByIdentifier(identifier, ValueUsageType.BUSINESS, properties);
	}
	
	@Override
	public ENTITY readByBusinessIdentifier(Object identifier) {
		return readByBusinessIdentifier(identifier, null);
	}
	
	@Override
	public Collection<ENTITY> readByString(String string) {
		String queryIdentifier = StringHelper.isBlank(string) ? "read" : PersistenceEntity.FIELD_NAME_READ_WHERE_CODE_OR_NAME_CONTAINS;
		Collection<ENTITY> entities = read(new Properties().setQueryIdentifier(QueryIdentifierBuilder.getInstance()
				.build(__entityClass__,queryIdentifier)).setIsPageable(Boolean.TRUE)
						.setFilters(new Filter.Dto().addField(AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl.FIELD_CODE, string)
								.addField(AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl.FIELD_NAME, string)));
		return entities;
	}
	
	@Override
	public Long count(Properties properties) {
		ControllerFunctionCounter function = ____inject____(ControllerFunctionCounter.class);
		function.setEntityClass(__entityClass__);
		function.setDataTransferClass(__dataTransferClass__);
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
		function.getAction().setEntityClass(__entityClass__);
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
		return redirect(CollectionHelper.getFirst(identifiers), properties);
	}
	
	@Override
	public ControllerEntity<ENTITY> select(Collection<Object> identifiers) {
		return select(identifiers,null);
	}
	
	@Override
	public ControllerEntity<ENTITY> redirect(Object identifier, Properties properties) {
		ControllerFunctionRedirector function = __inject__(ControllerFunctionRedirector.class)
				.setTargetSystemAction((SystemAction) Properties.getFromPath(properties, Properties.SYSTEM_ACTION));
		function.setEntityClass(__entityClass__);
		function.getAction().getEntitiesIdentifiers(Boolean.TRUE).add(identifier);
		function.setDataTransferClass(__dataTransferClass__);
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
			throw new RuntimeException("Le code "+identifier+" n'existe pas.");
		else {
			SystemActionRead systemActionRead = __inject__(SystemActionRead.class);
			systemActionRead.setEntityClass(getEntityClass());
			systemActionRead.getEntitiesIdentifiers(Boolean.TRUE).add(FieldHelper.readSystemIdentifier(entity));
			
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
	
	protected RepresentationEntity<?> getRepresentation(){
		System.out.println("AbstractControllerEntityImpl.getRepresentation()");
		RepresentationEntity<?> representation = null;
		if(__representationClass__ == null) {
			throw new RuntimeException("No representation class found for "+__entityClass__);
		}else {
			representation = (RepresentationEntity<?>) ProxyGetter.getInstance().get(__representationClass__);
		}
		return representation;
	}
	
}
