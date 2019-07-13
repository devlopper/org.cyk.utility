package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.stacktrace.StackTraceHelper;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldValueSetter;
import org.cyk.utility.method.MethodGetter;
import org.cyk.utility.server.persistence.annotation.Query;
import org.cyk.utility.server.persistence.query.PersistenceQuery;
import org.cyk.utility.server.persistence.query.PersistenceQueryContext;
import org.cyk.utility.server.persistence.query.PersistenceQueryContextImpl;
import org.cyk.utility.server.persistence.query.PersistenceQueryRepository;
import org.cyk.utility.sql.builder.QueryStringBuilder;
import org.cyk.utility.sql.builder.QueryStringBuilderSelect;
import org.cyk.utility.sql.builder.Tuple;
import org.cyk.utility.sql.jpql.JpqlQualifier;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.system.AbstractSystemServiceProviderImpl;
import org.cyk.utility.throwable.ThrowableHelper;

public abstract class AbstractPersistenceServiceProviderImpl<OBJECT> extends AbstractSystemServiceProviderImpl implements PersistenceServiceProvider<OBJECT>, Serializable {
	private static final long serialVersionUID = 1L;

	public static Boolean IS_CREATE_ONE_BY_ONE = Boolean.FALSE;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		__listenPostConstructPersistenceQueries__();
		__listenPostConstructGetQueriesFromAnnotation__();
	}
	
	@Override
	protected void __listenAfterPostConstruct__() {
		super.__listenAfterPostConstruct__();
		__logInfo__(getIdentifier()+" : Number of queries : "+__inject__(CollectionHelper.class).getSize(getQueries()));
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
		if(__inject__(CollectionHelper.class).isNotEmpty(fields)){
			for(Field index : fields){
				__inject__(FieldValueSetter.class).setObject(this).setField(index).setValue(__buildQueryIdentifierString__(index)).execute();
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
		if(__inject__(CollectionHelper.class).isNotEmpty(methods)){
			for(Method index : methods){
				Query annotation = index.getAnnotation(Query.class);
				PersistenceQuery query = new PersistenceQuery();
				String identifier = annotation.identifier();
				if(__inject__(StringHelper.class).isBlank(identifier)){
					identifier = __buildQueryIdentifierString__(index);
				}
				if(__inject__(StringHelper.class).isBlank(identifier))
					__inject__(ThrowableHelper.class).throwRuntimeException("Query identifier is required.");
				query.setIdentifier(identifier);
				
				String value = annotation.value();
				if(__inject__(StringHelper.class).isBlank(value)){
					
				}
				if(__inject__(StringHelper.class).isBlank(value))
					__inject__(ThrowableHelper.class).throwRuntimeException("Query value is required.");
				query.setValue(value);
				
				Class<?> aClass = annotation.resultClass();
				if(aClass == null)
					__inject__(ThrowableHelper.class).throwRuntimeException("Query result class is required.");
				query.setResultClass(aClass);
				
				addQueries(query);
			}
		}
	}
	
	protected final Object[] __getQueryParameters__(String queryIdentifier,Properties properties,Object...objects){
		PersistenceQuery persistenceQuery = __inject__(PersistenceQueryRepository.class).getBySystemIdentifier(queryIdentifier);
		if(persistenceQuery == null)
			__inject__(ThrowableHelper.class).throwRuntimeException("persistence query with identifier "+queryIdentifier+" not found.");
		return __getQueryParameters__(persistenceQuery, properties, objects);
	}
	
	@SuppressWarnings("unchecked")
	protected Object[] __getQueryParameters__(PersistenceQuery query,Properties properties,Object...objects){
		PersistenceQueryContext queryContext = __inject__(PersistenceQueryContext.class).setQuery(query).setParameters(objects);
		if(properties!=null && properties.getQueryFilters() instanceof Map)
			queryContext.setFilters((Map<String, Object>) properties.getQueryFilters());
		return __getQueryParameters__(queryContext, properties, objects);
	}
	
	protected Object[] __getQueryParameters__(PersistenceQueryContext queryContext,Properties properties,Object...objects){
		return null;
	}
	
	/*
	protected void addDerivedQueryIdentifier(Object from,Object value){
		if(__inject__(StringHelper.class).isNotBlank((String)from) && __inject__(StringHelper.class).isNotBlank((String)value)){
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
	
	protected Boolean __isCreateManyOneByOne__() {
		return Boolean.TRUE.equals(IS_CREATE_ONE_BY_ONE);
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> create(OBJECT object,Properties properties) {
		PersistenceFunctionCreator function = __inject__(PersistenceFunctionCreator.class);
		__copyCommonProperties__(function, properties);
		function.setEntity(object).execute();
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> create(OBJECT object) {
		return create(object, null);
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> createMany(Collection<OBJECT> objects,Properties properties) {
		PersistenceFunctionCreator function = __inject__(PersistenceFunctionCreator.class);
		__copyCommonProperties__(function, properties);
		Boolean __isCreateManyOneByOne__ = Boolean.TRUE.equals(__isCreateManyOneByOne__());
		if(__isCreateManyOneByOne__) {
			//Loop execution
			function.try_().setIsCodeFromFunctionExecutable(Boolean.FALSE).run().addRunnables(new Runnable() {
				@Override
				public void run() {
					for(OBJECT index : objects) {
						create(index);
					}
				}
			});
		}else {
			//Batch execution
			function.setEntities(objects);
		}
		function.execute();
		
		if(__isCreateManyOneByOne__) {
			//Loop execution
		}else {
			//Batch execution
		}
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> createMany(Collection<OBJECT> objects) {
		return createMany(objects, null);
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> update(OBJECT object, Properties properties) {
		PersistenceFunctionModifier function = ____inject____(PersistenceFunctionModifier.class);
		__copyCommonProperties__(function, properties);
		function.setEntity(object).execute();
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> update(OBJECT object) {
		update(object, null);
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects,Properties properties) {
		PersistenceFunctionModifier function = ____inject____(PersistenceFunctionModifier.class);
		__copyCommonProperties__(function, properties);
		function.setEntities(objects).execute();
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects) {
		return updateMany(objects, null);
	}

	@Override
	public PersistenceServiceProvider<OBJECT> delete(OBJECT object, Properties properties) {
		PersistenceFunctionRemover function = ____inject____(PersistenceFunctionRemover.class);
		__copyCommonProperties__(function, properties);
		function.setEntity(object).execute();
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> delete(OBJECT object) {
		return delete(object, null);
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> deleteMany(Collection<OBJECT> objects, Properties properties) {
		PersistenceFunctionRemover function = ____inject____(PersistenceFunctionRemover.class);
		__copyCommonProperties__(function, properties);
		function.setEntities(objects).execute();
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> deleteMany(Collection<OBJECT> objects) {
		return deleteMany(objects, null);
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> deleteAll(Properties properties) {
		__injectThrowableHelper__().throwRuntimeExceptionNotYetImplemented();
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> deleteAll() {
		deleteAll(null);
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> save(OBJECT object,Properties properties) {
		if(Boolean.TRUE.equals(isPersisted(object)))
			update(object, properties);
		else
			create(object, properties);
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> save(OBJECT object) {
		return save(object, null);
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> saveMany(Collection<OBJECT> objects,Properties properties) {
		if(Boolean.TRUE.equals(__injectCollectionHelper__().isNotEmpty(objects))) {
			for(OBJECT index : objects) {
				save(index, properties);
			}
		}
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> saveMany(Collection<OBJECT> objects) {
		return saveMany(objects, null);
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
		setQueries(__inject__(CollectionHelper.class).add(HashSet.class, getQueries(), Boolean.TRUE, queries));
		__inject__(PersistenceQueryRepository.class).add(getQueries());//TODO is it the right place ? what if call multiple times ??? are the old ones overwritten ???
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> addQueries(PersistenceQuery... queries) {
		return addQueries(__inject__(CollectionHelper.class).instanciate(queries));
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
	
	protected static Boolean __isFilterByKeys__(Map<String,Object> filters,String... keys) {
		return PersistenceQueryContextImpl.isFilterByKeys(filters, keys);
	}
	
	@SuppressWarnings("unchecked")
	protected static Boolean __isFilterByKeys__(Properties properties,String... keys) {
		return properties == null ? null :__isFilterByKeys__((Map<String, Object>) properties.getQueryFilters(), keys);
	}
	
	protected static String __getTupleName__(Class<?> klass) {
		return klass == null ? null : klass.getSimpleName();
	}
}
