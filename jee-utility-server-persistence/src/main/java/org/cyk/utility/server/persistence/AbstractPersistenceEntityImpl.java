package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.stacktrace.StackTraceHelper;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.map.MapHelper;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractPersistenceEntityImpl<ENTITY> extends AbstractPersistenceServiceProviderImpl<ENTITY> implements PersistenceEntity<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void __listenBeforePostConstruct__() {
		super.__listenBeforePostConstruct__();
		setEntityClass((Class<ENTITY>) __inject__(ClassHelper.class).getParameterAt(getClass(), 0, Object.class));
	}

	@Override
	protected String __getQueryIdentifierStringBuilderClassSimpleClassNameProperty__(Field field) {
		return getEntityClass().getSimpleName();
	}
	
	/**/
	
	@SuppressWarnings("unchecked")
	@Override
	public ENTITY readOne(Object identifier, Properties properties) {
		return (ENTITY) __inject__(PersistenceFunctionReader.class).setEntityClass(getEntityClass()).setEntityIdentifier(identifier)
				.setEntityIdentifierValueUsageType(properties == null ? null : (ValueUsageType)properties.getValueUsageType()).execute().getProperties().getEntity();
	}
	
	@Override
	public ENTITY readOne(Object identifier) {
		return readOne(identifier, (Properties)null);
	}
	
	@Override
	public ENTITY readOne(Object identifier, ValueUsageType valueUsageType) {
		return readOne(identifier, new Properties().setValueUsageType(valueUsageType));
	}
	
	@Override
	public ENTITY readOneByBusinessIdentifier(Object identifier) {
		return readOne(identifier, ValueUsageType.BUSINESS);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<ENTITY> readMany(Properties properties) {
		return (Collection<ENTITY>) __inject__(PersistenceFunctionReader.class).setEntityClass(getEntityClass()).execute().getProperties().getEntities();
	}
	
	@Override
	public Collection<ENTITY> readMany() {
		return readMany(null);
	}
	
	@Override
	public Long count(Properties properties) {
		return null;
	}
	
	@Override
	public Long count() {
		return null;
	}
	
	/**/
	
	@Override
	public PersistenceEntity<ENTITY> addQuery(Object identifier, String value) {
		addQuery(identifier, value,getEntityClass());
		return this;
	}
	
	/**/
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<ENTITY> getEntityClass() {
		return (Class<ENTITY>) getProperties().getEntityClass();
	}
	
	@Override
	public PersistenceEntity<ENTITY> setEntityClass(Class<ENTITY> aClass) {
		getProperties().setEntityClass(aClass);
		return this;
	}
	
	protected String __buildQueryStringIdentifierFromCurrentCall__(){
		return __inject__(PersistenceQueryIdentifierStringBuilder.class).setClassSimpleName(getEntityClass())
				.setFieldName(__inject__(StackTraceHelper.class).getAt(5/* TODO index vary on deep. it must be provided as param*/).getMethodName()).execute().getOutput();
	}
	
	protected PersistenceFunctionReader __getReader__(Object...parameters) {
		return __inject__(PersistenceFunctionReader.class)
				.setQueryIdentifier(__buildQueryStringIdentifierFromCurrentCall__())
				.setQueryParameters(Properties.instanciate(__inject__(MapHelper.class).instanciate(parameters)));
	}
	
	@SuppressWarnings("unchecked")
	protected Collection<ENTITY> __readMany__(Object...parameters) {
		return (Collection<ENTITY>) __getReader__(parameters).execute().getEntities();
	}
	
	protected Long __count__(Object...parameters) {
		return (Long) __inject__(CollectionHelper.class).getFirst(__getReader__(parameters).execute().getEntities());
	}
}
