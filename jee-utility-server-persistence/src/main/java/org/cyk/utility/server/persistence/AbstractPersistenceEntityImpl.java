package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;

import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.stacktrace.StackTraceHelper;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.clazz.ClassInstance;
import org.cyk.utility.clazz.ClassInstancesRuntime;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.map.MapHelper;
import org.cyk.utility.request.RequestProcessor;
import org.cyk.utility.server.persistence.query.PersistenceQuery;
import org.cyk.utility.sql.builder.QueryStringBuilder;
import org.cyk.utility.sql.builder.QueryStringBuilderSelect;
import org.cyk.utility.string.Strings;
import org.cyk.utility.type.BooleanHelper;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractPersistenceEntityImpl<ENTITY> extends AbstractPersistenceServiceProviderImpl<ENTITY> implements PersistenceEntity<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	protected String __tutpleName__;
	protected String read,readSystemIdentifiers,readBusinessIdentifiers,readBySystemIdentifiers,readByBusinessIdentifiers
		,deleteBySystemIdentifiers,deleteByBusinessIdentifiers,deleteAll;
		
	@Override
	public QueryStringBuilderSelect instanciateReadQueryStringBuilder() {
		return __instanciateQuerySelect__();
	}
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		Class<ENTITY> entityClass = getEntityClass();
		ClassInstance classInstance = __inject__(ClassInstancesRuntime.class).get(entityClass);
		String tupleName = classInstance.getTupleName();
		//String systemIdentifierFieldName =__inject__(FieldNameGetter.class).execute(entityClass, FieldName.IDENTIFIER, ValueUsageType.SYSTEM).getOutput();
		//String businessIdentifierFieldName =__inject__(FieldNameGetter.class).execute(entityClass, FieldName.IDENTIFIER, ValueUsageType.BUSINESS).getOutput();
		
		if(Boolean.TRUE.equals(getIsPhysicallyMapped())) {
			//TODO even not physically mapped we should be able to read
			addQueryCollectInstances(read, instanciateReadQueryStringBuilder());
			addQueriesByIdentifierField(classInstance.getSystemIdentifierField(), tupleName, readSystemIdentifiers, readBySystemIdentifiers, deleteBySystemIdentifiers);
			addQueriesByIdentifierField(classInstance.getBusinessIdentifierField(), tupleName, readBusinessIdentifiers, readByBusinessIdentifiers, deleteByBusinessIdentifiers);
			addQuery(deleteAll, "DELETE FROM "+__getTupleName__()+" tuple",null);	
		}
	}
	
	protected void addQueriesByIdentifierField(Field field,String tupleName,String readIdentifiers,String readByIdentifiers,String deleteByIdentifiers) {
		if(field != null) {
			String columnName = field.getName();
			addQuery(readIdentifiers, String.format("SELECT tuple.%s FROM %s tuple", columnName,tupleName),null);
			addQuery(readByIdentifiers, String.format("SELECT tuple FROM %s tuple WHERE tuple.%s IN :identifiers", tupleName,columnName),null);
			addQuery(deleteByIdentifiers, String.format("DELETE FROM %s tuple WHERE tuple.%s IN :identifiers", tupleName,columnName),null);	
		}
	}
	
	protected Object[] __getQueryParameters__(PersistenceQuery query,Properties properties,Object...objects){
		if(query.isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(read))
			return null;
		return super.__getQueryParameters__(query,properties, objects);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __listenBeforePostConstruct__() {
		super.__listenBeforePostConstruct__();
		setEntityClass((Class<ENTITY>) __inject__(ClassHelper.class).getParameterAt(getClass(), 0, Object.class));
		setIsPhysicallyMapped(getEntityClass().getAnnotation(Entity.class)!=null);
	}

	@Override
	protected String __getQueryIdentifierStringBuilderClassSimpleClassNameProperty__(Object object) {
		return getEntityClass().getSimpleName();
	}
	
	/**/
	
	@Override
	public PersistenceEntity<ENTITY> create(ENTITY object) {
		return (PersistenceEntity<ENTITY>) super.create(object);
	}
	
	/**/
	
	@Override
	public Collection<ENTITY> read(Properties properties) {
		if(properties == null)
			properties = new Properties();
		if(properties!=null) {
			if(properties.getQueryIdentifier() == null)
				properties.setQueryIdentifier(__injectValueHelper__().defaultToIfNull(__getQueryIdentifier__(PersistenceFunctionReader.class, properties),read));
		}
		return __readMany__(properties,____getQueryParameters____(properties));
	}
	
	@Override
	public Collection<ENTITY> read() {
		// TODO user default settings like pagination
		return read(null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<ENTITY> readByIdentifiers(Collection<Object> identifiers, ValueUsageType valueUsageType,Properties properties) {
		PersistenceFunctionReader function = __inject__(PersistenceFunctionReader.class);
		function.setQueryIdentifier(ValueUsageType.BUSINESS.equals(valueUsageType) ? readByBusinessIdentifiers : readBySystemIdentifiers);
		function.setQueryParameters(new Properties().set("identifiers",identifiers));
		__copyCommonProperties__(function, properties);
		function.execute();
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
	public ENTITY readByIdentifier(Object identifier,ValueUsageType valueUsageType, Properties properties) {
		/*
		if(properties == null)
			properties = new Properties();
		if(properties!=null) {
			if(properties.getQueryIdentifier() == null)
				properties.setQueryIdentifier(__injectValueHelper__().defaultToIfNull(__getQueryIdentifier__(PersistenceFunctionReader.class, properties)
						,ValueUsageType.BUSINESS.equals(properties.getValueUsageType()) ? readByBusinessIdentifiers : readBySystemIdentifiers));
			if(properties.getParameter() == null)
				properties.setParameters(new Properties().set("identifiers",Arrays.asList(identifier)));
		}
		return __readOne__(properties,____getQueryParameters____(properties));
		*/
		/*PersistenceFunctionReader function = __inject__(PersistenceFunctionReader.class);
		function.setQueryIdentifier(ValueUsageType.BUSINESS.equals(properties.getValueUsageType()) ? readByBusinessIdentifiers : readBySystemIdentifiers);
		function.setQueryParameters(new Properties().set("identifiers",Arrays.asList(identifier)));
		__copyCommonProperties__(function, properties);
		function.execute();
		*/
		return (ENTITY) __injectCollectionHelper__().getFirst(readByIdentifiers(Arrays.asList(identifier),valueUsageType, properties));
		/*
		return (ENTITY) __inject__(PersistenceFunctionReader.class).setEntityClass(getEntityClass()).setEntityIdentifier(identifier)
				.setEntityIdentifierValueUsageType(Properties.getFromPath(properties, Properties.VALUE_USAGE_TYPE)).execute().getProperties().getEntity();
		*/
	}
	
	@Override
	public ENTITY readByIdentifier(Object identifier,ValueUsageType valueUsageType) {
		return readByIdentifier(identifier, valueUsageType, null);
	}
	
	@Override
	public ENTITY readByIdentifier(Object identifier) {
		return readByIdentifier(identifier, ValueUsageType.SYSTEM);
	}
	
	@Override
	public ENTITY readByBusinessIdentifier(Object identifier,Properties properties) {
		return readByIdentifier(identifier, ValueUsageType.BUSINESS,properties);
	}
	
	@Override
	public ENTITY readByBusinessIdentifier(Object identifier) {
		return readByBusinessIdentifier(identifier,null);
	}
	
	@Override
	public ENTITY readBySystemIdentifier(Object identifier,Properties properties) {
		return readByIdentifier(identifier, ValueUsageType.SYSTEM,properties);
	}
	
	@Override
	public ENTITY readBySystemIdentifier(Object identifier) {
		return readBySystemIdentifier(identifier, null);
	}
	
	@Override
	public Collection<Object> readIdentifiers(ValueUsageType valueUsageType, Properties properties) {
		if(properties == null)
			properties = new Properties();
		if(properties!=null) {
			if(properties.getQueryIdentifier() == null)
				properties.setQueryIdentifier(__injectValueHelper__().defaultToIfNull(__getQueryIdentifier__(PersistenceFunctionReader.class, properties)
						,ValueUsageType.BUSINESS.equals(valueUsageType) ? readBusinessIdentifiers : readSystemIdentifiers));
			if(properties.getValueUsageType() == null)
				properties.setValueUsageType(valueUsageType);
		}
		
		@SuppressWarnings("unchecked")
		Collection<Object> identifiers = (Collection<Object>) __getReader__(properties).execute().getEntities();
		
		return identifiers;
	}
	
	@Override
	public Collection<Object> readIdentifiers(ValueUsageType valueUsageType) {
		return readIdentifiers(valueUsageType, null);
	}
	
	@Override
	public Collection<Object> readSystemIdentifiers(Properties properties) {
		return readIdentifiers(ValueUsageType.SYSTEM, properties);
	}
	
	@Override
	public Collection<Object> readSystemIdentifiers() {
		return readSystemIdentifiers(null);
	}
	
	@Override
	public Collection<Object> readBusinessIdentifiers(Properties properties) {
		return readIdentifiers(ValueUsageType.BUSINESS, properties);
	}
	
	@Override
	public Collection<Object> readBusinessIdentifiers() {
		return readBusinessIdentifiers(null);
	}
	
	@Override
	public Long count(Properties properties) {
		if(properties == null)
			properties = new Properties();
		if(properties!=null) {
			properties.setIsQueryResultPaginated(null);
			properties.setQueryFirstTupleIndex(null);
			properties.setQueryNumberOfTuple(null);
			if(properties.getQueryIdentifier() == null) {
				String queryIdentifier = __injectValueHelper__().defaultToIfNull(__getQueryIdentifier__(PersistenceFunctionReader.class, properties),read);
				if(__injectStringHelper__().isNotBlank(queryIdentifier))
					queryIdentifier = __inject__(PersistenceQueryIdentifierStringBuilder.class).setIsDerivedFromQueryIdentifier(Boolean.TRUE)
						.setDerivedFromQueryIdentifier(queryIdentifier).setIsCountInstances(Boolean.TRUE)
						.execute().getOutput();
				properties.setQueryIdentifier(queryIdentifier);
			}
		}
		return __count__(properties,____getQueryParameters____(properties));
	}
	
	@Override
	public Long count() {
		return count(null);
	}
	
	@Override
	public PersistenceEntity<ENTITY> deleteByIdentifier(Object identifier, ValueUsageType valueUsageType,Properties properties) {
		//delete(readByIdentifier(identifier, valueUsageType), properties);
		return deleteByIdentifiers(Arrays.asList(identifier), valueUsageType, properties);
	}
	
	@Override
	public PersistenceEntity<ENTITY> deleteByIdentifier(Object identifier, ValueUsageType valueUsageType) {
		return deleteByIdentifier(identifier, valueUsageType, null);
	}
	
	@Override
	public PersistenceEntity<ENTITY> deleteBySystemIdentifier(Object identifier, Properties properties) {
		return deleteByIdentifier(identifier, ValueUsageType.SYSTEM, properties);
	}
	
	@Override
	public PersistenceEntity<ENTITY> deleteBySystemIdentifier(Object identifier) {
		return deleteBySystemIdentifier(identifier, null);
	}
	
	@Override
	public PersistenceEntity<ENTITY> deleteByBusinessIdentifier(Object identifier, Properties properties) {
		return deleteByIdentifier(identifier, ValueUsageType.BUSINESS, properties);
	}
	
	@Override
	public PersistenceEntity<ENTITY> deleteByBusinessIdentifier(Object identifier) {
		return deleteByBusinessIdentifier(identifier, null);
	}
	
	
	@Override
	public PersistenceEntity<ENTITY> deleteByIdentifiers(Collection<Object> identifiers,ValueUsageType valueUsageType, Properties properties) {
		PersistenceFunctionRemover function = __inject__(PersistenceFunctionRemover.class);
		function.setQueryIdentifier(ValueUsageType.BUSINESS.equals(valueUsageType) ? deleteByBusinessIdentifiers : deleteBySystemIdentifiers);
		function.setQueryParameters(new Properties().set("identifiers",identifiers));
		__copyCommonProperties__(function, properties);
		function.execute();
		return this;
	}
	
	@Override
	public PersistenceEntity<ENTITY> deleteByIdentifiers(Collection<Object> identifiers,ValueUsageType valueUsageType) {
		return deleteByIdentifiers(identifiers, valueUsageType, null);
	}
	
	@Override
	public PersistenceEntity<ENTITY> deleteBySystemIdentifiers(Collection<Object> identifiers,Properties properties) {
		return deleteByIdentifiers(identifiers, ValueUsageType.SYSTEM, properties);
	}
	
	@Override
	public PersistenceEntity<ENTITY> deleteBySystemIdentifiers(Collection<Object> identifiers) {
		return deleteBySystemIdentifiers(identifiers, null);
	}
	
	@Override
	public PersistenceEntity<ENTITY> deleteByBusinessIdentifiers(Collection<Object> identifiers,Properties properties) {
		return deleteByIdentifiers(identifiers, ValueUsageType.BUSINESS, properties);
	}
	
	@Override
	public PersistenceEntity<ENTITY> deleteByBusinessIdentifiers(Collection<Object> identifiers) {
		return deleteByBusinessIdentifiers(identifiers, null);
	}
	
	@Override
	public PersistenceServiceProvider<ENTITY> deleteAll(Properties properties) {
		PersistenceFunctionRemover function = __inject__(PersistenceFunctionRemover.class);
		__copyCommonProperties__(function, properties);
		function.setQueryIdentifier(deleteAll).execute();
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<ENTITY> deleteAll() {
		__inject__(PersistenceFunctionRemover.class).setQueryIdentifier(deleteAll).execute();
		return this;
	}
	
	/**/
	
	@Override
	public PersistenceEntity<ENTITY> addQuery(Object identifier, String value) {
		addQuery(identifier, value,getEntityClass());
		return this;
	}
	
	@Override
	public PersistenceEntity<ENTITY> addQueryCollectInstances(Object identifier, String value) {
		addQueryCollectInstances(identifier, value, getEntityClass());
		return this;
	}
	
	@Override
	public PersistenceEntity<ENTITY> addQueryCollectInstances(Object identifier, QueryStringBuilder stringBuilder) {
		return addQueryCollectInstances(identifier, stringBuilder.execute().getOutput());
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
	
	@Override
	public Boolean getIsPhysicallyMapped() {
		return (Boolean) getProperties().get("isPhysicallyMapped");
	}
	
	@Override
	public PersistenceEntity<ENTITY> setIsPhysicallyMapped(Boolean isPhysicallyMapped) {
		getProperties().set("isPhysicallyMapped", isPhysicallyMapped);
		return this;
	}
	
	protected String __getQueryIdentifier__(Class<?> functionClass,Properties properties,Object...parameters){
		return null;
	}
	
	protected String __buildQueryStringIdentifierFromCurrentCall__(Integer stackTraceMethodAt){
		return __inject__(PersistenceQueryIdentifierStringBuilder.class).setClassSimpleName(getEntityClass())
				.setName(__inject__(StackTraceHelper.class).getAt(stackTraceMethodAt/* TODO index vary on deep. it must be provided as param*/).getMethodName()).execute().getOutput();
	}
	
	/**/
	
	@SuppressWarnings("unchecked")
	protected <FUNCTION extends PersistenceFunction> FUNCTION __getFunction__(Class<FUNCTION> aClass,Properties properties,Object...parameters) {
		String queryIdentifier = (String) Properties.getFromPath(properties, Properties.QUERY_IDENTIFIER);
		if(__injectStringHelper__().isBlank(queryIdentifier))
			queryIdentifier = __getQueryIdentifier__(aClass, properties, parameters);
		if(__injectStringHelper__().isBlank(queryIdentifier))
			queryIdentifier = __buildQueryStringIdentifierFromCurrentCall__(6);
		return (FUNCTION) __inject__(aClass)
				.setQueryIdentifier(queryIdentifier)
				.setQueryParameters(Properties.instanciate(__inject__(MapHelper.class).instanciate(parameters)));
	}
	
	protected PersistenceFunctionReader __getReader__(Properties properties,Object...parameters) {
		PersistenceFunctionReader reader = __getFunction__(PersistenceFunctionReader.class,properties, parameters);
		reader.setIsQueryResultPaginated(__inject__(BooleanHelper.class).get(Properties.getFromPath(properties, Properties.IS_QUERY_RESULT_PAGINATED)));
		reader.setQueryFirstTupleIndex(__injectNumberHelper__().getLong(Properties.getFromPath(properties, Properties.QUERY_FIRST_TUPLE_INDEX)));
		reader.setQueryNumberOfTuple(__injectNumberHelper__().getLong(Properties.getFromPath(properties, Properties.QUERY_NUMBER_OF_TUPLE)));
		return reader;
	}
	
	protected PersistenceFunctionModifier __getModifier__(Properties properties,Object...parameters) {
		return __getFunction__(PersistenceFunctionModifier.class,properties, parameters);
	}
	
	protected PersistenceFunctionRemover __getRemover__(Properties properties,Object...parameters) {
		return __getFunction__(PersistenceFunctionRemover.class,properties, parameters);
	}
	
	/**/
	
	@SuppressWarnings("unchecked")
	protected Collection<ENTITY> __readMany__(Properties properties,Object...parameters) {
		Collection<ENTITY> entities = (Collection<ENTITY>) __getReader__(properties,parameters).execute().getEntities();
		if(__injectCollectionHelper__().isNotEmpty(entities))
			__listenReadManyAfter__(entities);
		return entities;
	}
	
	protected void __listenReadManyAfter__(Collection<ENTITY> entities) {
		if(__injectCollectionHelper__().isNotEmpty(entities)) {
			for(ENTITY index : entities)
				__processAfterRead__(index);	
		}
	}
	
	protected void __processAfterRead__(ENTITY entity) {
		Strings uniformResourceIdentifierStringFormats = __getReadOneUniformResourceIdentifierFormats__();
		if(__injectCollectionHelper__().isNotEmpty(uniformResourceIdentifierStringFormats)) {
			for(String index : uniformResourceIdentifierStringFormats.get())
				__inject__(RequestProcessor.class).setUniformResourceIdentifierStringFormat(index).setResponseEntity(entity).execute();		
		}
	}
	
	protected Strings __getReadOneUniformResourceIdentifierFormats__() {
		return null;
	}
	
	protected ENTITY __readOne__(Properties properties,Object...parameters) {
		@SuppressWarnings("unchecked")
		Collection<ENTITY> entities = (Collection<ENTITY>) __getReader__(properties,parameters).execute().getEntities();
		Integer size = __injectCollectionHelper__().getSize(entities);
		if(size!=null && size > 1)
			throw new RuntimeException("too much ("+size+") results found");
		ENTITY entity = __injectCollectionHelper__().getFirst(entities);
		__processAfterRead__(entity);
		return entity;
	}
	
	protected Long __count__(Properties properties,Object...parameters) {
		return (Long) __inject__(CollectionHelper.class).getFirst(__getReader__(properties,parameters).execute().getEntities());
	}
	
	protected Long __modify__(Properties properties,Object...parameters) {
		__getModifier__(properties,parameters).execute();//TODO we should get the number of entities modified
		return null;
	}
	
	protected Long __delete__(Properties properties,Object...parameters) {
		__getRemover__(properties,parameters).execute();//TODO we should get the number of entities deleted
		return null;
	}
	
	protected Object[] ____getQueryParameters____(Properties properties,Object...objects){
		String queryIdentifier = (String) Properties.getFromPath(properties, Properties.QUERY_IDENTIFIER);
		if(__injectStringHelper__().isBlank(queryIdentifier))
			queryIdentifier = __buildQueryStringIdentifierFromCurrentCall__(4);
		Object[] parameters = (Object[]) Properties.getFromPath(properties, Properties.QUERY_PARAMETERS);
		if(parameters == null)
			parameters = __getQueryParameters__(queryIdentifier,properties, objects);
		if(__inject__(ArrayHelper.class).isEmpty(parameters)){
			//Query can have no parameters
			//TODO base on query string structure you can know the expected parameters and decide to throw exception
			//__inject__(ThrowableHelper.class).throwRuntimeException("Parameters of query "+queryIdentifier+" are required");
		}
		return parameters;
	}
	
	protected Object[] __getCollectInstancesQueryParameters__(Properties properties,Object...objects){
		return ____getQueryParameters____(properties,objects);
	}
	
	protected Object[] __getCountInstancesQueryParameters__(Properties properties,Object...objects){
		return ____getQueryParameters____(properties,objects);
	}
	
	protected QueryStringBuilderSelect __instanciateQuerySelect__(){
		return __instanciateQuerySelect__(getEntityClass());
	}
	
	protected QueryStringBuilderSelect __instanciateQueryReadBy__(String fieldName){
		return __instanciateQuerySelect__().getWherePredicateBuilderAsEqual().addOperandBuilderByAttribute(fieldName,ComparisonOperator.EQ)
				.getParentAsWhereClause().getParentAs(QueryStringBuilderSelect.class);
	}
	
	@Override
	protected PersistenceQueryIdentifierStringBuilder __injectPersistenceQueryIdentifierStringBuilder__() {
		return super.__injectPersistenceQueryIdentifierStringBuilder__().setClassSimpleName(getEntityClass());
	}
	
	protected String __getTupleName__() {
		return __getTupleName__(getEntityClass());
	}
}
