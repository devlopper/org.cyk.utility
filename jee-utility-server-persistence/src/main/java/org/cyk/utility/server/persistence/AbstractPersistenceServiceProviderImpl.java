package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.stacktrace.StackTraceHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.method.MethodGetter;
import org.cyk.utility.server.persistence.annotation.Query;
import org.cyk.utility.server.persistence.query.PersistenceQuery;
import org.cyk.utility.server.persistence.query.PersistenceQueryContext;
import org.cyk.utility.server.persistence.query.PersistenceQueryContextImpl;
import org.cyk.utility.server.persistence.query.PersistenceQueryRepository;
import org.cyk.utility.server.persistence.query.filter.Filter;
import org.cyk.utility.sql.builder.QueryStringBuilder;
import org.cyk.utility.sql.builder.QueryStringBuilderSelect;
import org.cyk.utility.sql.builder.Tuple;
import org.cyk.utility.sql.jpql.JpqlQualifier;
import org.cyk.utility.system.AbstractSystemServiceProviderImpl;

public abstract class AbstractPersistenceServiceProviderImpl<OBJECT> extends AbstractSystemServiceProviderImpl implements PersistenceServiceProvider<OBJECT>, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		__listenPostConstructPersistenceQueries__();
		__listenPostConstructGetQueriesFromAnnotation__();
	}
	
	@Override
	protected void __listenAfterPostConstruct__() {
		super.__listenAfterPostConstruct__();
		__logInfo__(getIdentifier()+" : Number of queries : "+CollectionHelper.getSize(getQueries()));
	}
	
	/**
	 * Query registration process : Query identifier is computed from attribute name or method name
	 */
	protected void __listenPostConstructPersistenceQueries__(){
		__listenPostConstructPersistenceQueriesIdentifiers__();
	}
	
	/** 1 - get class fields matching query identifier rules</br>
	 * 2 - set fields value with a uniquely computed query identifier
	 */
	protected void __listenPostConstructPersistenceQueriesIdentifiers__(){
		Collection<Field> fields = __inject__(PersistenceQueryIdentifierFieldGetter.class).setClazz(getClass()).execute().getOutput();
		if(CollectionHelper.isNotEmpty(fields)){
			for(Field index : fields){
				FieldHelper.write(this, index, __buildQueryIdentifierString__(index));
			}
		}
	}
	
	protected String __buildQueryIdentifierString__(Object object){
		return  __inject__(PersistenceQueryIdentifierStringBuilder.class).setClassSimpleName(__getQueryIdentifierStringBuilderClassSimpleClassNameProperty__(object))
				.setName(object instanceof Field ? ((Field)object).getName() : (object instanceof Method ? ((Method)object).getName() : object.toString())).execute().getOutput();
	}
	
	protected String __getQueryIdentifierStringBuilderClassSimpleClassNameProperty__(Object object){
		return getClass().getSimpleName();
	}
	
	protected void __listenPostConstructGetQueriesFromAnnotation__(){
		//1 - from class
		//2 - from field
		//3 - from method
		Collection<Method> methods = __inject__(MethodGetter.class).setClazz(getClass()).addAnnotationClasses(Query.class).execute().getOutput();
		if(CollectionHelper.isNotEmpty(methods)){
			for(Method index : methods){
				Query annotation = index.getAnnotation(Query.class);
				PersistenceQuery query = new PersistenceQuery();
				String identifier = annotation.identifier();
				if(StringHelper.isBlank(identifier)){
					identifier = __buildQueryIdentifierString__(index);
				}
				if(StringHelper.isBlank(identifier))
					throw new RuntimeException("Query identifier is required.");
				query.setIdentifier(identifier);
				
				String value = annotation.value();
				if(StringHelper.isBlank(value)){
					
				}
				if(StringHelper.isBlank(value))
					throw new RuntimeException("Query value is required.");
				query.setValue(value);
				
				Class<?> aClass = annotation.resultClass();
				if(aClass == null)
					throw new RuntimeException("Query result class is required.");
				query.setResultClass(aClass);
				
				addQueries(query);
			}
		}
	}
	
	protected final Object[] __getQueryParameters__(String queryIdentifier,Properties properties,Object...objects){
		PersistenceQuery persistenceQuery = __inject__(PersistenceQueryRepository.class).getBySystemIdentifier(queryIdentifier);
		if(persistenceQuery == null)
			throw new RuntimeException("persistence query with identifier "+queryIdentifier+" not found.");
		return __getQueryParameters__(persistenceQuery, properties, objects);
	}
	
	protected Object[] __getQueryParameters__(PersistenceQuery query,Properties properties,Object...objects){
		PersistenceQueryContext queryContext = __inject__(PersistenceQueryContext.class).setQuery(query).setParameters(objects);
		if(properties!=null && properties.getQueryFilters() instanceof Filter)
			queryContext.setFilter((Filter) properties.getQueryFilters());
			//queryContext.setFilters((Map<String, Object>) properties.getQueryFilters());
		return __getQueryParameters__(queryContext, properties, objects);
	}
	
	protected Object[] __getQueryParameters__(PersistenceQueryContext queryContext,Properties properties,Object...objects){
		return null;
	}
	
	/*
	protected void addDerivedQueryIdentifier(Object from,Object value){
		if(StringHelper.isNotBlank((String)from) &&StringHelper.isNotBlank((String)value)){
			if(derivedQueryIdentifierMap == null)
				derivedQueryIdentifierMap = new HashMap<Object, Set<Object>>();
			Set<Object> values = derivedQueryIdentifierMap.get(from);
			if(values == null)
				derivedQueryIdentifierMap.put(from, values = new HashSet<Object>());
			values.add(value);
		}
	}
	
	protected Object getQueryIdentifierDerivedFrom(Object value){
		if(derivedQueryIdentifierMap!=null){
			for(Map.Entry<Object,Set<Object>> entry : derivedQueryIdentifierMap.entrySet()){
				if(entry.getValue().contains(value))
					return entry.getKey();
			}
		}
		return null;
	}
	*/
	/**/
	
	/* Create */
	
	@Override
	public PersistenceServiceProvider<OBJECT> createMany(Collection<OBJECT> objects,Properties properties) {
		PersistenceFunctionCreator function = __inject__(PersistenceFunctionCreator.class);
		__copyCommonProperties__(function, properties);
		function.setEntities(objects);
		__listenExecuteCreateBefore__(objects, properties, function);
		function.execute();
		__listenExecuteCreateAfter__(objects, properties, function);
		return this;
	}
	
	/*
	@Override
	public PersistenceServiceProvider<OBJECT> createMany(Collection<OBJECT> objects,Properties properties) {
		EntityCreator.getInstance().createMany((Collection<Object>)objects,__inject__(JavaPersistenceApiHelper.class).getEntityManager(properties));
		return this;
	}*/
	
	@Override
	public PersistenceServiceProvider<OBJECT> createMany(Collection<OBJECT> objects) {
		return createMany(objects, null);
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> create(OBJECT object,Properties properties) {
		createMany((Collection<OBJECT>) Arrays.asList(object), properties);
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> create(OBJECT object) {
		return create(object, null);
	}
	
	protected void __listenExecuteCreateBefore__(Collection<OBJECT> objects, Properties properties,PersistenceFunctionCreator function){
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(objects)))
			for(OBJECT index : objects)
				__listenExecuteCreateBefore__(index, properties, function);
	}
	
	protected void __listenExecuteCreateAfter__(Collection<OBJECT> objects, Properties properties,PersistenceFunctionCreator function){
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(objects)))
			for(OBJECT index : objects)
				__listenExecuteCreateAfter__(index, properties, function);
	}
	
	protected void __listenExecuteCreateBefore__(OBJECT object, Properties properties,PersistenceFunctionCreator function){}
	
	protected void __listenExecuteCreateAfter__(OBJECT object, Properties properties,PersistenceFunctionCreator function){}
	
	/* Update */
	
	@Override
	public PersistenceServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects,Properties properties) {
		PersistenceFunctionModifier function = ____inject____(PersistenceFunctionModifier.class);
		__copyCommonProperties__(function, properties);
		function.setEntities(objects);
		__listenExecuteUpdateBefore__(objects, properties, function);
		function.execute();
		__listenExecuteUpdateAfter__(objects, properties, function);
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects) {
		return updateMany(objects, null);
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> update(OBJECT object, Properties properties) {
		return updateMany(Arrays.asList(object),properties);
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> update(OBJECT object) {
		update(object, null);
		return this;
	}
	
	protected void __listenExecuteUpdateBefore__(Collection<OBJECT> objects, Properties properties,PersistenceFunctionModifier function){
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(objects)))
			for(OBJECT index : objects)
				__listenExecuteUpdateBefore__(index, properties, function);
	}
	
	protected void __listenExecuteUpdateAfter__(Collection<OBJECT> objects, Properties properties,PersistenceFunctionModifier function){
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(objects)))
			for(OBJECT index : objects)
				__listenExecuteUpdateAfter__(index, properties, function);
	}
	
	protected void __listenExecuteUpdateBefore__(OBJECT object, Properties properties,PersistenceFunctionModifier function){}
	
	protected void __listenExecuteUpdateAfter__(OBJECT object, Properties properties,PersistenceFunctionModifier function){}
	
	/* Delete */

	@Override
	public PersistenceServiceProvider<OBJECT> deleteMany(Collection<OBJECT> objects, Properties properties) {
		PersistenceFunctionRemover function = ____inject____(PersistenceFunctionRemover.class);
		__copyCommonProperties__(function, properties);
		function.setEntities(objects);
		__listenExecuteDeleteBefore__(objects, properties, function);
		function.execute();
		__listenExecuteDeleteAfter__(objects, properties, function);
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> deleteMany(Collection<OBJECT> objects) {
		return deleteMany(objects, null);
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> delete(OBJECT object, Properties properties) {
		return deleteMany(Arrays.asList(object),properties);
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> delete(OBJECT object) {
		return delete(object, null);
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> deleteAll(Properties properties) {
		ThrowableHelper.throwNotYetImplemented();
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> deleteAll() {
		deleteAll(null);
		return this;
	}
	
	protected void __listenExecuteDeleteBefore__(Collection<OBJECT> objects, Properties properties,PersistenceFunctionRemover function){
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(objects)))
			for(OBJECT index : objects)
				__listenExecuteDeleteBefore__(index, properties, function);
	}
	
	protected void __listenExecuteDeleteAfter__(Collection<OBJECT> objects, Properties properties,PersistenceFunctionRemover function){
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(objects)))
			for(OBJECT index : objects)
				__listenExecuteDeleteAfter__(index, properties, function);
	}
	
	protected void __listenExecuteDeleteBefore__(OBJECT object, Properties properties,PersistenceFunctionRemover function){}
	
	protected void __listenExecuteDeleteAfter__(OBJECT object, Properties properties,PersistenceFunctionRemover function){}
	
	/* Save */
	
	@Override
	public PersistenceServiceProvider<OBJECT> saveMany(Collection<OBJECT> objects,Properties properties) {
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(objects))) {
			for(OBJECT index : objects) {
				if(Boolean.TRUE.equals(isPersisted(index)))
					update(index, properties);
				else
					create(index, properties);
			}
		}
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> saveMany(Collection<OBJECT> objects) {
		return saveMany(objects, null);
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> save(OBJECT object,Properties properties) {
		return saveMany(Arrays.asList(object),properties);
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> save(OBJECT object) {
		return save(object, null);
	}
	
	/**/
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<PersistenceQuery> getQueries() {
		return (Collection<PersistenceQuery>) getProperties().getQueries();
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> setQueries(Collection<PersistenceQuery> queries) {
		getProperties().setQueries(queries);
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> addQueries(Collection<PersistenceQuery> queries) {
		setQueries(CollectionHelper.add(HashSet.class, getQueries(), Boolean.TRUE, queries));
		__inject__(PersistenceQueryRepository.class).add(getQueries());//TODO is it the right place ? what if call multiple times ??? are the old ones overwritten ???
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> addQueries(PersistenceQuery... queries) {
		return addQueries(List.of(queries));
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> addQuery(Object identifier, String value, Class<?> resultClass) {
		addQueries(new PersistenceQuery().setIdentifier(identifier).setValue(value).setResultClass(resultClass));
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> addQueryCollectInstances(Object identifier, String value,Class<?> resultClass) {
		addQuery(identifier, value, resultClass);
		addQueryCountInstancesFromCollection(identifier);
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> addQueryCollectInstances(Object identifier,QueryStringBuilder stringBuilder, Class<?> resultClass) {
		return addQueryCollectInstances(identifier, stringBuilder.execute().getOutput(), resultClass);
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> addQueryCountInstancesFromCollection(Object collectionIdentifier) {
		PersistenceQuery persistenceQuery = __inject__(PersistenceQueryRepository.class).getBySystemIdentifier(collectionIdentifier, Boolean.TRUE);
		if(persistenceQuery!=null){
			String value = "SELECT COUNT("
					+StringUtils.substringBetween(persistenceQuery.getValue(),"SELECT ", " FROM ")
					+") FROM "+StringUtils.substringAfter(persistenceQuery.getValue(), " FROM ");
			String identifier = __inject__(PersistenceQueryIdentifierStringBuilder.class).setIsDerivedFromQueryIdentifier(Boolean.TRUE)
					.setDerivedFromQueryIdentifier(collectionIdentifier).setIsCountInstances(Boolean.TRUE).execute().getOutput();
			value = StringUtils.substringBefore(value, "ORDER BY");
			addQuery(identifier, value, Long.class);
			__inject__(PersistenceQueryRepository.class).getBySystemIdentifier(identifier, Boolean.TRUE).setQueryDerivedFromQuery(persistenceQuery);
			//addDerivedQueryIdentifier(collectionIdentifier, identifier);
		}
		return this;
	}
	
	protected QueryStringBuilderSelect __instanciateQuerySelect__(Class<?> entityClass){
		Tuple tuple = new Tuple().setName(entityClass.getSimpleName());
		return JpqlQualifier.map(QueryStringBuilderSelect.class).from(tuple);
	}
	
	/**/
	
	protected static PersistenceLayer __injectPersistenceLayer__() {
		return __inject__(PersistenceLayer.class);
	}
	
	protected PersistenceQueryIdentifierStringBuilder __injectPersistenceQueryIdentifierStringBuilder__() {
		return __inject__(PersistenceQueryIdentifierStringBuilder.class);
	}
	
	protected static String __getStackTraceCallerMethodName__() {
		return __inject__(StackTraceHelper.class).getCallerMethodName(1);
	}
	
	protected static Boolean __isFilterByKeys__(Filter filter,String... keys) {
		return PersistenceQueryContextImpl.isFilterByKeys(filter, keys);
	}
	
	protected static Boolean __isFilterByKeys__(Properties properties,String... keys) {
		return properties == null ? null :__isFilterByKeys__((Filter) properties.getQueryFilters(), keys);
	}
	
}
