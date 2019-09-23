package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.computation.ArithmeticOperator;
import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.stacktrace.StackTraceHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.clazz.ClassInstance;
import org.cyk.utility.clazz.ClassInstancesRuntime;
import org.cyk.utility.field.FieldInstance;
import org.cyk.utility.field.FieldInstancesRuntime;
import org.cyk.utility.map.MapHelper;
import org.cyk.utility.number.NumberHelper;
import org.cyk.utility.request.RequestProcessor;
import org.cyk.utility.server.persistence.query.PersistenceQuery;
import org.cyk.utility.server.persistence.query.PersistenceQueryContext;
import org.cyk.utility.server.persistence.query.filter.Filter;
import org.cyk.utility.sql.builder.QueryStringBuilder;
import org.cyk.utility.sql.builder.QueryStringBuilderSelect;
import org.cyk.utility.type.BooleanHelper;

public abstract class AbstractPersistenceEntityImpl<ENTITY> extends AbstractPersistenceServiceProviderImpl<ENTITY> implements PersistenceEntity<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	protected String read
		,readSystemIdentifiers,readBusinessIdentifiers,readBySystemIdentifiers,readByBusinessIdentifiers,readWhereSystemIdentifierContains,readWhereBusinessIdentifierContains
		,deleteBySystemIdentifiers,deleteByBusinessIdentifiers,deleteAll;
	protected ClassInstance __classInstance__;
	protected Class<ENTITY> __entityClass__;
	final protected Map<ValueUsageType,Map<ArithmeticOperator,String>> __queryIdentifierReadByIdentifiersByArithmeticOperator__ = new HashMap<>();
	
	@Override
	public QueryStringBuilderSelect instanciateReadQueryStringBuilder() {
		return __instanciateQuerySelect__();
	}
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		__classInstance__ = __inject__(ClassInstancesRuntime.class).get(__entityClass__);
		String tupleName = __getTupleName__();
		
		if(Boolean.TRUE.equals(getIsPhysicallyMapped())) {
			//TODO even not physically mapped we should be able to read
			__addReadQueryCollectInstances__();
			addQueriesByIdentifierField(ValueUsageType.SYSTEM, tupleName, readSystemIdentifiers, readBySystemIdentifiers,readWhereSystemIdentifierContains, deleteBySystemIdentifiers);
			addQueriesByIdentifierField(ValueUsageType.BUSINESS, tupleName, readBusinessIdentifiers, readByBusinessIdentifiers,readWhereBusinessIdentifierContains, deleteByBusinessIdentifiers);
			addQuery(deleteAll, "DELETE FROM "+__getTupleName__()+" tuple",null);	
		}
	}
	
	protected void __addReadQueryCollectInstances__() {
		addQueryCollectInstances(read, instanciateReadQueryStringBuilder());
	}
	
	protected void addQueriesByIdentifierField(ValueUsageType valueUsageType,String tupleName,String readIdentifiers,String readByIdentifiers,String readWhereIdentifierContains,String deleteByIdentifiers) {
		Field field = ValueUsageType.BUSINESS.equals(valueUsageType) ? __classInstance__.getBusinessIdentifierField() : __classInstance__.getSystemIdentifierField(); 
		if(field != null) {
			String columnName = field.getName();
			addQuery(readIdentifiers, String.format("SELECT tuple.%s FROM %s tuple", columnName,tupleName),null);
			addQueryCollectInstances(readByIdentifiers, String.format("SELECT tuple FROM %s tuple WHERE tuple.%s IN :identifiers", tupleName,columnName));
			FieldInstance fieldInstance = __inject__(FieldInstancesRuntime.class).get(__entityClass__, field.getName());
			if(fieldInstance != null && String.class.equals(fieldInstance.getType())) {
				addQueryCollectInstances(readWhereIdentifierContains, String.format("SELECT tuple FROM %s tuple WHERE lower(tuple.%s) LIKE lower(:identifier)", tupleName,columnName));
			}
			addQuery(deleteByIdentifiers, String.format("DELETE FROM %s tuple WHERE tuple.%s IN :identifiers", tupleName,columnName),null);	
			Map<ArithmeticOperator,String> map = new HashMap<>();
			map.put(ArithmeticOperator.IN, readByIdentifiers);
			map.put(ArithmeticOperator.LIKE, readWhereIdentifierContains);
			__queryIdentifierReadByIdentifiersByArithmeticOperator__.put(valueUsageType, map);
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
		__entityClass__ = (Class<ENTITY>) org.cyk.utility.__kernel__.klass.ClassHelper.getParameterAt(getClass(), 0);
		setIsPhysicallyMapped(__entityClass__.getAnnotation(Entity.class)!=null);
	}

	@Override
	protected String __getQueryIdentifierStringBuilderClassSimpleClassNameProperty__(Object object) {
		return __entityClass__.getSimpleName();
	}
	
	/* Create */
	
	@Override
	public PersistenceEntity<ENTITY> create(ENTITY object) {
		return (PersistenceEntity<ENTITY>) super.create(object);
	}
	
	/* Read */
	
	@Override
	public Collection<ENTITY> read(Properties properties) {
		if(properties == null)
			properties = new Properties();
		if(properties!=null) {
			Filter filter = (Filter) properties.getQueryFilters();
			if(filter != null)
				filter.normalize(__entityClass__);
			if(properties.getQueryIdentifier() == null)
				properties.setQueryIdentifier(__injectValueHelper__().defaultToIfNull(__getQueryIdentifier__(PersistenceFunctionReader.class, properties),read));
		}
		//System.out.println("AbstractPersistenceEntityImpl.read() QID : "+properties.getQueryIdentifier());
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
		Collection<ENTITY> entities = (Collection<ENTITY>) function.getEntities();
		if(CollectionHelper.isNotEmpty(entities))
			__listenExecuteReadAfter__(entities,properties);
		return entities;
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
		Collection<ENTITY> entities = (Collection<ENTITY>) readByIdentifiers(Arrays.asList(identifier),valueUsageType, properties);
		Integer size = CollectionHelper.getSize(entities);
		if(size!=null && size > 1)
			throw new RuntimeException("too much ("+size+") results found");
		ENTITY entity = CollectionHelper.getFirst(entities);
		return entity;
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
			Filter filter = (Filter) properties.getQueryFilters();
			if(filter != null)
				filter.normalize(__entityClass__);
			properties.setIsQueryResultPaginated(null);
			properties.setQueryFirstTupleIndex(null);
			properties.setQueryNumberOfTuple(null);
			if(properties.getQueryIdentifier() == null) {
				String queryIdentifier = __injectValueHelper__().defaultToIfNull(__getQueryIdentifier__(PersistenceFunctionReader.class, properties),read);
				if(StringHelper.isNotBlank(queryIdentifier))
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
	
	/* Delete */
	
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
		addQuery(identifier, value,__entityClass__);
		return this;
	}
	
	@Override
	public PersistenceEntity<ENTITY> addQueryCollectInstances(Object identifier, String value) {
		addQueryCollectInstances(identifier, value, __entityClass__);
		return this;
	}
	
	@Override
	public PersistenceEntity<ENTITY> addQueryCollectInstances(Object identifier, QueryStringBuilder stringBuilder) {
		return addQueryCollectInstances(identifier, stringBuilder.execute().getOutput());
	}
	
	/**/
	
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
		Filter filters = (Filter) Properties.getFromPath(properties, Properties.QUERY_FILTERS);
		if(filters != null) {
			if(__classInstance__ != null) {
				String identifier = __getQueryIdentifierByIdentifierField__(ValueUsageType.SYSTEM, read, filters);
				if(identifier == null)
					identifier = __getQueryIdentifierByIdentifierField__(ValueUsageType.BUSINESS, read, filters);
				return identifier;
			}
		}
		return null;
	}
	
	protected String __getQueryIdentifierByIdentifierField__(ValueUsageType valueUsageType,String defaultQueryIdentifier,Filter filters){
		String identifier = null;
		Field identifierField = ValueUsageType.BUSINESS.equals(valueUsageType) ? __classInstance__.getBusinessIdentifierField() : __classInstance__.getSystemIdentifierField();
		org.cyk.utility.server.persistence.query.filter.Field field = null;
		if(filters != null)
			field = filters.getFieldByPath(identifierField.getName());
		if(field != null) {
			Object identifiers = field.getValue();
			ArithmeticOperator arithmeticOperator = field.getArithmeticOperator();
			if(arithmeticOperator == null) {
				if(identifiers instanceof Collection)
					arithmeticOperator = ArithmeticOperator.IN;
				else if(identifiers instanceof String)
					arithmeticOperator = ArithmeticOperator.LIKE;
			}
			if(identifiers == null)
				identifier =  defaultQueryIdentifier;
			else {
				Map<ArithmeticOperator,String> map = __queryIdentifierReadByIdentifiersByArithmeticOperator__.get(valueUsageType);
				if(map != null)
					identifier = map.get(arithmeticOperator);
			}
			if(identifier == null)
				__injectThrowableHelper__().throwRuntimeExceptionNotYetImplemented(String.format("filter by identifiers with operator %s and values %s",arithmeticOperator,identifiers));
		}
		return identifier;
	}
	
	@Override
	protected Object[] __getQueryParameters__(PersistenceQueryContext queryContext, Properties properties,Object... objects) {
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readBySystemIdentifiers)) {
			if(Boolean.TRUE.equals(__inject__(ArrayHelper.class).isEmpty(objects)))
				objects = new Object[] {queryContext.getFilterByKeysValue(__classInstance__.getSystemIdentifierField().getName())};
			return new Object[]{"identifiers", objects[0]};
		}else if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByBusinessIdentifiers)) {
			if(Boolean.TRUE.equals(__inject__(ArrayHelper.class).isEmpty(objects)))
				objects = new Object[] {queryContext.getFilterByKeysValue(__classInstance__.getBusinessIdentifierField().getName())};
			return new Object[]{"identifiers", objects[0]};
		}else if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readWhereSystemIdentifierContains)) {
			if(Boolean.TRUE.equals(__inject__(ArrayHelper.class).isEmpty(objects)))
				objects = new Object[] {queryContext.getFilterByKeysValue(__classInstance__.getSystemIdentifierField().getName())};
			return new Object[]{"identifier", "%"+objects[0]+"%"};
		}else if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readWhereBusinessIdentifierContains)) {
			if(Boolean.TRUE.equals(__inject__(ArrayHelper.class).isEmpty(objects)))
				objects = new Object[] {queryContext.getFilterByKeysValue(__classInstance__.getBusinessIdentifierField().getName())};
			return new Object[]{"identifier", "%"+objects[0]+"%"};
		}
		return super.__getQueryParameters__(queryContext, properties, objects);
	}
	
	protected String __buildQueryStringIdentifierFromCurrentCall__(Integer stackTraceMethodAt){
		return __inject__(PersistenceQueryIdentifierStringBuilder.class).setClassSimpleName(__entityClass__)
				.setName(__inject__(StackTraceHelper.class).getAt(stackTraceMethodAt/* TODO index vary on deep. it must be provided as param*/).getMethodName()).execute().getOutput();
	}
	
	/**/
	
	@SuppressWarnings("unchecked")
	protected <FUNCTION extends PersistenceFunction> FUNCTION __getFunction__(Class<FUNCTION> aClass,Properties properties,Object...parameters) {
		String queryIdentifier = (String) Properties.getFromPath(properties, Properties.QUERY_IDENTIFIER);
		if(StringHelper.isBlank(queryIdentifier))
			queryIdentifier = __getQueryIdentifier__(aClass, properties, parameters);
		if(StringHelper.isBlank(queryIdentifier))
			queryIdentifier = __buildQueryStringIdentifierFromCurrentCall__(6);
		return (FUNCTION) __inject__(aClass)
				.setQueryIdentifier(queryIdentifier)
				.setQueryParameters(Properties.instanciate(__inject__(MapHelper.class).instanciate(parameters)));
	}
	
	protected PersistenceFunctionReader __getReader__(Properties properties,Object...parameters) {
		PersistenceFunctionReader reader = __getFunction__(PersistenceFunctionReader.class,properties, parameters);
		reader.setIsQueryResultPaginated(BooleanHelper.get(Properties.getFromPath(properties, Properties.IS_QUERY_RESULT_PAGINATED)));
		reader.setQueryFirstTupleIndex(NumberHelper.getLong(Properties.getFromPath(properties, Properties.QUERY_FIRST_TUPLE_INDEX)));
		reader.setQueryNumberOfTuple(NumberHelper.getLong(Properties.getFromPath(properties, Properties.QUERY_NUMBER_OF_TUPLE)));
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
		if(CollectionHelper.isNotEmpty(entities))
			__listenExecuteReadAfter__(entities,properties);
		return entities;
	}
	
	protected void __listenExecuteReadAfter__(Collection<ENTITY> entities,Properties properties) {
		if(CollectionHelper.isNotEmpty(entities)) {
			for(ENTITY index : entities)
				__listenExecuteReadAfter__(index,properties);	
		}
	}
	
	protected void __listenExecuteReadAfter__(ENTITY entity,Properties properties) {
		Strings fields = __getFieldsFromProperties__(properties);
		Collection<Field> fieldsToBeSet = new ArrayList<>();
		Collection<Field> fieldsToBeSetToNull = new ArrayList<>();
		Collection<String> fieldsToBeSetNames = new ArrayList<>();
		if(CollectionHelper.isNotEmpty(fields) && CollectionHelper.isNotEmpty(__classInstance__.getFields())) {
			for(Field index : __classInstance__.getFields().get()) {
				String indexName = index.getName();
				if(!Modifier.isStatic(index.getModifiers()) && !Modifier.isFinal(index.getModifiers()) 
						&& !__classInstance__.getSystemIdentifierField().getName().equals(indexName) && !fields.contains(indexName)) {
					fieldsToBeSetToNull.add(index);
					fieldsToBeSetNames.add(indexName);
				}else if(index.getAnnotation(Transient.class)!=null) {
					fieldsToBeSet.add(index);
					fieldsToBeSetNames.add(indexName);
				}
			}
			
			for(Field index : fieldsToBeSet)
				__listenExecuteReadAfterSetFieldValue__(entity, index,properties);
			for(Field index : fieldsToBeSetToNull)
				__listenExecuteReadAfterSetFieldValueToNull__(entity, index,properties);
			
			for(String index : fields.get()) {
				if(!fieldsToBeSetNames.contains(index))
					__listenExecuteReadAfterSetFieldValue__(entity, index, properties);
			}
		}
				
		Strings uniformResourceIdentifierStringFormats = __getReadOneUniformResourceIdentifierFormats__();
		if(CollectionHelper.isNotEmpty(uniformResourceIdentifierStringFormats)) {
			for(String index : uniformResourceIdentifierStringFormats.get())
				__inject__(RequestProcessor.class).setUniformResourceIdentifierStringFormat(index).setResponseEntity(entity).execute();		
		}
	}
	
	protected void __listenExecuteReadAfterSetFieldValueToNull__(ENTITY entity,Field field,Properties properties) {
		//TODO not working well because of jpa entitymanager that tracks any changes to fields in context
		//__injectFieldValueSetter__().execute(entity, field, null);
	}
	
	protected void __listenExecuteReadAfterSetFieldValue__(ENTITY entity,Field field,Properties properties) {}
	protected void __listenExecuteReadAfterSetFieldValue__(ENTITY entity,String fieldName,Properties properties) {}
	
	protected Strings __getReadOneUniformResourceIdentifierFormats__() {
		return null;
	}
	
	protected ENTITY __readOne__(Properties properties,Object...parameters) {
		@SuppressWarnings("unchecked")
		Collection<ENTITY> entities = (Collection<ENTITY>) __getReader__(properties,parameters).execute().getEntities();
		Integer size = CollectionHelper.getSize(entities);
		if(size!=null && size > 1)
			throw new RuntimeException("too much ("+size+") results found");
		ENTITY entity = CollectionHelper.getFirst(entities);
		__listenExecuteReadAfter__(entity,properties);
		return entity;
	}
	
	protected Long __count__(Properties properties,Object...parameters) {
		return (Long) CollectionHelper.getFirst(__getReader__(properties,parameters).execute().getEntities());
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
		if(StringHelper.isBlank(queryIdentifier))
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
		return __instanciateQuerySelect__(__entityClass__);
	}
	
	protected QueryStringBuilderSelect __instanciateQueryReadBy__(String fieldName){
		return __instanciateQuerySelect__().getWherePredicateBuilderAsEqual().addOperandBuilderByAttribute(fieldName,ComparisonOperator.EQ)
				.getParentAsWhereClause().getParentAs(QueryStringBuilderSelect.class);
	}
	
	@Override
	protected PersistenceQueryIdentifierStringBuilder __injectPersistenceQueryIdentifierStringBuilder__() {
		return super.__injectPersistenceQueryIdentifierStringBuilder__().setClassSimpleName(__entityClass__);
	}
	
	protected String __getTupleName__() {
		return __classInstance__ == null ? __getTupleName__(__entityClass__) : __classInstance__.getTupleName() ;
	}
	
}
