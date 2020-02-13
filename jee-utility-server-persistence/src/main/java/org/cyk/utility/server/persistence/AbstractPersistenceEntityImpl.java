package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.computation.ArithmeticOperator;
import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.__kernel__.computation.LogicalOperator;
import org.cyk.utility.__kernel__.computation.SortOrder;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.field.FieldInstance;
import org.cyk.utility.__kernel__.field.FieldInstancesRuntime;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.persistence.query.QueryStringHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.stacktrace.StackTraceHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.map.MapHelper;
import org.cyk.utility.__kernel__.persistence.query.Query;
import org.cyk.utility.__kernel__.persistence.query.QueryContext;
import org.cyk.utility.__kernel__.persistence.query.filter.Filter;
import org.cyk.utility.sql.builder.QueryStringBuilder;
import org.cyk.utility.sql.builder.QueryStringBuilderSelect;

public abstract class AbstractPersistenceEntityImpl<ENTITY> extends AbstractPersistenceServiceProviderImpl<ENTITY> implements PersistenceEntity<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;
	/*
	private static final String NAME_TOKENS_LIKE;
	static {
		Collection<String> names = new ArrayList<>();
		for(Integer index = 1; index <=3; index = index + 1)
			names.add("LOWER(tuple.%3$s) LIKE LOWER(:name"+index+")");
		NAME_TOKENS_LIKE = StringHelper.concatenate(names, " AND ");
	}
	*/
	protected String read
		,readSystemIdentifiers,readBusinessIdentifiers
		,readBySystemIdentifiers,readByBusinessIdentifiers
		,readWhereSystemIdentifierContains,readWhereBusinessIdentifierContains,readWhereBusinessIdentifierOrNameContains
		,readWhereSystemIdentifierNotIn,readWhereBusinessIdentifierNotIn
		,readByFiltersLike
		,deleteBySystemIdentifiers,deleteByBusinessIdentifiers,deleteAll
		;
	
	/* Working variables */
	protected Field __systemIdentifierField__,__businessIdentifierField__,__businessNameField__;
	protected Collection<Field> __entityFields__;
	protected Class<ENTITY> __entityClass__;
	protected String __tupleName__;
	final protected Map<ValueUsageType,Map<ArithmeticOperator,String>> __queryIdentifierReadByIdentifiersByArithmeticOperator__ = new HashMap<>();
	
	@Override
	public QueryStringBuilderSelect instanciateReadQueryStringBuilder() {
		QueryStringBuilderSelect queryStringBuilderSelect = __instanciateQuerySelect__();
		if(__businessIdentifierField__ != null)
			queryStringBuilderSelect.orderBy(__businessIdentifierField__.getName(), SortOrder.ASCENDING);
		else if(__systemIdentifierField__ != null)
			queryStringBuilderSelect.orderBy(__systemIdentifierField__.getName(), SortOrder.ASCENDING);
		return queryStringBuilderSelect;
	}
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		__systemIdentifierField__ = FieldHelper.getSystemIdentifier(__entityClass__);
		__businessIdentifierField__ = FieldHelper.getBusinessIdentifier(__entityClass__);
		__businessNameField__ = FieldHelper.getBusinessName(__entityClass__);
		__tupleName__ = ClassHelper.getTupleName(__entityClass__);
		__entityFields__ = FieldHelper.get(__entityClass__);
		
		if(Boolean.TRUE.equals(getIsPhysicallyMapped())) {
			//TODO even not physically mapped we should be able to read
			__addReadQueryCollectInstances__();
			addQueriesByIdentifierField(ValueUsageType.SYSTEM, __tupleName__, readSystemIdentifiers, readBySystemIdentifiers,readWhereSystemIdentifierNotIn,readWhereSystemIdentifierContains, deleteBySystemIdentifiers);
			addQueriesByIdentifierField(ValueUsageType.BUSINESS, __tupleName__, readBusinessIdentifiers, readByBusinessIdentifiers,readWhereBusinessIdentifierNotIn,readWhereBusinessIdentifierContains, deleteByBusinessIdentifiers);
			addQuery(deleteAll, "DELETE FROM "+__tupleName__+" tuple",null);	
		}
	}
	
	protected void __addReadQueryCollectInstances__() {
		addQueryCollectInstances(read, instanciateReadQueryStringBuilder());
	}
	
	protected void addQueriesByIdentifierField(ValueUsageType valueUsageType,String tupleName,String readIdentifiers,String readByIdentifiers,String readWhereIdentifierNotIn,String readWhereIdentifierContains,String deleteByIdentifiers) {
		Field field = ValueUsageType.BUSINESS.equals(valueUsageType) ? __businessIdentifierField__ : __systemIdentifierField__; 
		if(field != null) {
			String columnName = field.getName();
			addQuery(readIdentifiers, String.format("SELECT tuple.%s FROM %s tuple", columnName,tupleName),null);
			addQueryCollectInstances(readByIdentifiers, String.format("SELECT tuple FROM %s tuple WHERE tuple.%s IN :identifiers", tupleName,columnName));
			addQueryCollectInstances(readWhereIdentifierNotIn, String.format("SELECT tuple FROM %s tuple WHERE tuple.%s NOT IN :identifiers", tupleName,columnName));
			FieldInstance fieldInstance = __inject__(FieldInstancesRuntime.class).get(__entityClass__, field.getName());
			if(fieldInstance != null && String.class.equals(fieldInstance.getType())) {
				addQueryCollectInstances(readWhereIdentifierContains, String.format("SELECT tuple FROM %s tuple WHERE lower(tuple.%s) LIKE lower(:identifier)", tupleName,columnName));
				if(ValueUsageType.BUSINESS.equals(valueUsageType) && __businessNameField__ != null && __businessNameField__.getAnnotation(Transient.class) == null) {					
					addQueryCollectInstances(readWhereBusinessIdentifierOrNameContains, 
							String.format("SELECT tuple FROM %1$s tuple WHERE "
									+ QueryStringHelper.formatTupleFieldLike("tuple", columnName,"identifier")
									+ " OR "
									+ QueryStringHelper.formatTupleFieldLikeOrTokens("tuple", __businessNameField__.getName(), 3, LogicalOperator.AND)
							, tupleName,columnName));
				}
			}
			addQuery(deleteByIdentifiers, String.format("DELETE FROM %s tuple WHERE tuple.%s IN :identifiers", tupleName,columnName),null);	
			Map<ArithmeticOperator,String> map = new HashMap<>();
			map.put(ArithmeticOperator.IN, readByIdentifiers);
			map.put(ArithmeticOperator.LIKE, readWhereIdentifierContains);
			__queryIdentifierReadByIdentifiersByArithmeticOperator__.put(valueUsageType, map);
		}
	}
	
	protected Object[] __getQueryParameters__(Query query,Properties properties,Object...objects){
		if(query.isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(read))
			return null;
		return super.__getQueryParameters__(query,properties, objects);
	}
	
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
				properties.setQueryIdentifier(ValueHelper.defaultToIfNull(__getQueryIdentifier__(PersistenceFunctionReader.class, properties),read));
		}
		//System.out.println("AbstractPersistenceEntityImpl.read() QID : "+properties.getQueryIdentifier());
		return __readMany__(properties,____getQueryParameters____(properties));
	}
	
	@Override
	public Collection<ENTITY> read() {
		// TODO user default settings like pagination
		return read(null);
	}
	
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
				properties.setQueryIdentifier(ValueHelper.defaultToIfNull(__getQueryIdentifier__(PersistenceFunctionReader.class, properties)
						,ValueUsageType.BUSINESS.equals(valueUsageType) ? readBusinessIdentifiers : readSystemIdentifiers));
			if(properties.getValueUsageType() == null)
				properties.setValueUsageType(valueUsageType);
		}		
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
				String queryIdentifier = ValueHelper.defaultToIfNull(__getQueryIdentifier__(PersistenceFunctionReader.class, properties),read);
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
			if(__systemIdentifierField__ != null || __businessIdentifierField__ != null) {
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
		Field identifierField = ValueUsageType.BUSINESS.equals(valueUsageType) ? __businessIdentifierField__ : __systemIdentifierField__;
		org.cyk.utility.__kernel__.persistence.query.filter.Field field = null;
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
				ThrowableHelper.throwNotYetImplemented(String.format("filter by identifiers with operator %s and values %s",arithmeticOperator,identifiers));
		}
		return identifier;
	}
	
	@Override
	protected Object[] __getQueryParameters__(QueryContext queryContext, Properties properties,Object... objects) {
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readBySystemIdentifiers)) {
			if(Boolean.TRUE.equals(__inject__(ArrayHelper.class).isEmpty(objects)))
				objects = new Object[] {queryContext.getFilterByKeysValue(__systemIdentifierField__.getName())};
			return new Object[]{"identifiers", objects[0]};
		}else if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByBusinessIdentifiers)) {
			if(Boolean.TRUE.equals(__inject__(ArrayHelper.class).isEmpty(objects)))
				objects = new Object[] {queryContext.getFilterByKeysValue(__businessIdentifierField__.getName())};
			return new Object[]{"identifiers", objects[0]};
		}else if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readWhereSystemIdentifierContains)) {
			if(Boolean.TRUE.equals(__inject__(ArrayHelper.class).isEmpty(objects)))
				objects = new Object[] {queryContext.getFilterByKeysValue(__systemIdentifierField__.getName())};
			return new Object[]{"identifier", "%"+objects[0]+"%"};
		}else if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readWhereBusinessIdentifierContains)) {
			if(Boolean.TRUE.equals(__inject__(ArrayHelper.class).isEmpty(objects)))
				objects = new Object[] {queryContext.getFilterByKeysValue(__businessIdentifierField__.getName())};
			return new Object[]{"identifier", "%"+objects[0]+"%"};
		}else if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readWhereBusinessIdentifierOrNameContains)) {
			if(Boolean.TRUE.equals(__inject__(ArrayHelper.class).isEmpty(objects))) {
				Object businessIdentifierFieldValue = null;
				org.cyk.utility.__kernel__.persistence.query.filter.Field field = queryContext.getFilterFieldByKeys(__businessIdentifierField__.getName());				
				if(field == null || field.getValue() == null || field.getValue() instanceof String) {
					businessIdentifierFieldValue = "%"+(field == null ? ConstantEmpty.STRING : StringUtils.trimToEmpty((String) field.getValue()))+"%";
				}
				
				List<String> businessNameFieldValue = queryContext.getFieldValueLikes(__businessNameField__.getName(),4);
				objects = new Object[] {businessIdentifierFieldValue,businessNameFieldValue.get(0),businessNameFieldValue.get(1),businessNameFieldValue.get(2)
						,businessNameFieldValue.get(3)};
			}
			//System.out.println("AbstractPersistenceEntityImpl.__getQueryParameters__() : "+Arrays.deepToString(objects)+" ::: "+queryContext.getQuery().getValue());
			return new Object[]{"identifier", "%"+objects[0]+"%","name", objects[1],"name1", objects[2],"name2", objects[3],"name3", objects[4]};
		}
		return super.__getQueryParameters__(queryContext, properties, objects);
	}
	
	protected String __buildQueryStringIdentifierFromCurrentCall__(Integer stackTraceMethodAt){
		return __inject__(PersistenceQueryIdentifierStringBuilder.class).setClassSimpleName(__entityClass__)
				.setName(__inject__(StackTraceHelper.class).getAt(stackTraceMethodAt/* TODO index vary on deep. it must be provided as param*/).getMethodName()).execute().getOutput();
	}
	
	/**/
	
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
		reader.setIsQueryResultPaginated(ValueHelper.convertToBoolean(Properties.getFromPath(properties, Properties.IS_QUERY_RESULT_PAGINATED)));
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
		Strings fieldsNames = __getFieldsFromProperties__(properties);
		__listenExecuteReadAfterSetFieldsValues__(entity, fieldsNames, properties);
	}
	
	protected void __listenExecuteReadAfterSetFieldsValues__(ENTITY entity,Strings fieldsNames,Properties properties) {
		Collection<Field> fieldsToBeSet = new ArrayList<>();
		Collection<Field> fieldsToBeSetToNull = new ArrayList<>();
		Collection<String> fieldsToBeSetNames = new ArrayList<>();
		if(CollectionHelper.isNotEmpty(fieldsNames) && CollectionHelper.isNotEmpty(__entityFields__)) {
			//Collection<String> simpleFieldsNames = FieldHelper.getSimpleNames(fieldsNames.get(), Boolean.TRUE);
			for(Field index : __entityFields__) {
				String indexName = index.getName();
				if(!Modifier.isStatic(index.getModifiers()) && !Modifier.isFinal(index.getModifiers()) 
						&& !__systemIdentifierField__.getName().equals(indexName) && !fieldsNames.contains(indexName)) {
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
			
			/*Map<String,Collection<String>> nestedFieldsNames = FieldHelper.getNamesMap(fieldsNames.get());
			if(org.cyk.utility.__kernel__.map.MapHelper.isNotEmpty(nestedFieldsNames)) {
				for(Map.Entry<String, Collection<String>> entry : nestedFieldsNames.entrySet())
					;	
			}*/
						
			for(String index : fieldsNames.get()) {
				if(!fieldsToBeSetNames.contains(index))
					__listenExecuteReadAfterSetFieldValue__(entity, index, properties);
			}
		}
				
		/*Strings uniformResourceIdentifierStringFormats = __getReadOneUniformResourceIdentifierFormats__();
		if(CollectionHelper.isNotEmpty(uniformResourceIdentifierStringFormats)) {
			for(String index : uniformResourceIdentifierStringFormats.get())
				__inject__(RequestProcessor.class).setUniformResourceIdentifierStringFormat(index).setResponseEntity(entity).execute();		
		}*/
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
			//throw new RuntimeException("Parameters of query "+queryIdentifier+" are required");
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
	
}
