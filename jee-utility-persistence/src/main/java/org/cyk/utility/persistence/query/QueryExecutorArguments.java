package org.cyk.utility.persistence.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.computation.SortOrder;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.mapping.MapperSourceDestination;
import org.cyk.utility.__kernel__.mapping.MappingHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemImpl;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.persistence.EntityManagerGetter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class QueryExecutorArguments extends AbstractObject implements Serializable {
	private Query query;
	private Collection<Projection> projections;
	private Collection<String> processableTransientFieldsNames;
	private Collection<String> resultsFieldsNames;
	private Collection<String> flags;
	private Map<Object,Object> parameters;
	private PropertiesArguments properties;
	private Map<String,Object> hints;
	private Boolean isProcessableAsAuditByDates;
	private Boolean collectionable;
	private Boolean isResultCachable;
	private Boolean isResultProcessable;
	private Filter filter,filterBackup;
	private Map<String,SortOrder> sortOrders;
	private Integer firstTupleIndex;
	private Integer numberOfTuples;
	private Collection<Object> objects;
	private Collection<Object> systemIdentifiers;
	private Boolean isTransactional;
	private EntityManager entityManager;
	private Boolean isEntityManagerFlushable;
	private Boolean isEntityManagerClearable;
	private Boolean isEntityManagerClosable;
	private Boolean isNative;
	private Boolean isThrowExceptionIfIdentifierIsBlank;
	private Boolean isThrowExceptionIfResultIsBlank;
	
	private EntityManager __entityManager__;
	private Map<Object,Object> __parameters__;
	private Map<String,Object> __hints__;
	private Boolean __isEntityManagerFlushable__;
	private Boolean __isEntityManagerClearable__;
	private Boolean __isEntityManagerClosable__;
	private Class<?> __resultClass__;
	private Collection<?> __objects__;
	private Query __query__;
	private Collection<String> __projections__;
	private Filter __filter__;
	private Collection<String> __resultsFieldsNames__;
	
	private java.util.logging.Level loggingLevel;
	
	/* runtime */
	private Boolean queryBuildableAtRuntime;
	private Query runtimeQuery;	
	private Filter runtimeFilter;
	
	public Collection<String> getFlags(Boolean injectIfNull) {
		if(flags == null && Boolean.TRUE.equals(injectIfNull))
			flags = new ArrayList<>();
		return flags;
	}
	
	public QueryExecutorArguments addFlags(Collection<String> flags) {
		if(CollectionHelper.isEmpty(flags))
			return this;
		getFlags(Boolean.TRUE).addAll(flags);
		return this;
	}
	
	public QueryExecutorArguments addFlags(String...flags) {
		if(ArrayHelper.isEmpty(flags))
			return this;
		return addFlags(CollectionHelper.listOf(flags));
	}
	
	public Boolean isFlagged(String flag) {
		return CollectionHelper.contains(flags, flag);
	}
	
	public Collection<Projection> getProjections(Boolean injectIfNull) {
		if(projections == null && Boolean.TRUE.equals(injectIfNull))
			projections = new ArrayList<>();
		return projections;
	}
	
	public QueryExecutorArguments addProjections(Collection<Projection> projections) {
		if(CollectionHelper.isEmpty(projections))
			return this;
		getProjections(Boolean.TRUE).addAll(projections);
		return this;
	}
	
	public QueryExecutorArguments addProjections(Projection...projections) {
		if(ArrayHelper.isEmpty(projections))
			return this;
		return addProjections(CollectionHelper.listOf(projections));
	}
	
	public QueryExecutorArguments addProjectionsFromStrings(Collection<String> strings) {
		if(CollectionHelper.isEmpty(strings))
			return this;
		Collection<Projection> projections = new ArrayList<>();
		for(String string : strings) {
			Projection projection = new Projection().setFieldName(string);
			projections.add(projection);
		}
		getProjections(Boolean.TRUE).addAll(projections);
		return this;
	}
	
	public QueryExecutorArguments addProjectionsFromStrings(String...strings) {
		if(ArrayHelper.isEmpty(strings))
			return this;
		ArrayList<String> list = new ArrayList<>();
		list.addAll(CollectionHelper.listOf(strings));
		return addProjectionsFromStrings(list);
	}
	
	public Collection<String> getProcessableTransientFieldsNames(Boolean injectIfNull) {
		if(processableTransientFieldsNames == null && Boolean.TRUE.equals(injectIfNull))
			processableTransientFieldsNames = new ArrayList<>();
		return processableTransientFieldsNames;
	}
	
	public QueryExecutorArguments addProcessableTransientFieldsNames(Collection<String> processableTransientFieldsNames) {
		if(CollectionHelper.isEmpty(processableTransientFieldsNames))
			return this;
		getProcessableTransientFieldsNames(Boolean.TRUE).addAll(processableTransientFieldsNames);
		return this;
	}
	
	public QueryExecutorArguments addProcessableTransientFieldsNames(String...processableTransientFieldsNames) {
		if(ArrayHelper.isEmpty(processableTransientFieldsNames))
			return this;
		return addProcessableTransientFieldsNames(CollectionHelper.listOf(processableTransientFieldsNames));
	}
	
	public Boolean isTransientFieldNameProcessable(String fieldName) {
		if(StringHelper.isBlank(fieldName))
			return Boolean.FALSE;
		return CollectionHelper.isEmpty(processableTransientFieldsNames) ? Boolean.FALSE
				: processableTransientFieldsNames.contains(fieldName);
	}
	
	public QueryExecutorArguments setQueryFromIdentifier(String identifier) {
		if(StringHelper.isBlank(identifier))
			query = null;
		else
			query = QueryGetter.getInstance().get(identifier);
		return this;
	}
	
	public QueryExecutorArguments prepare(Class<?> resultClass) {
		if(runtimeQuery == null)
			__query__ = query;
		else
			__query__ = runtimeQuery;
		
		if(__query__ != null) {
			if(__query__.getIntermediateResultClass() == null)
				__resultClass__ = __query__.getResultClass();
			else
				__resultClass__ = __query__.getIntermediateResultClass();
		}
		
		if(__resultClass__ == null)
			__resultClass__ = resultClass;
		
		if(__resultsFieldsNames__ == null)
			__resultsFieldsNames__ = resultsFieldsNames;
		
		__isEntityManagerFlushable__ = isEntityManagerFlushable;
		__isEntityManagerClearable__ = isEntityManagerClearable;
		__isEntityManagerClosable__ = isEntityManagerClosable;		
		__entityManager__ = entityManager;
		if(__entityManager__ == null) {
			__entityManager__ = EntityManagerGetter.getInstance().get();
			if(__isEntityManagerFlushable__ == null)
				__isEntityManagerFlushable__ = Boolean.TRUE;
			if(__isEntityManagerClearable__ == null)
				__isEntityManagerClearable__ = Boolean.TRUE;
			if(__isEntityManagerClosable__ == null)
				__isEntityManagerClosable__ = Boolean.TRUE;		
		}
		
		/* parameters */
		
		if(runtimeFilter == null)
			__filter__ = filter;
		else
			__filter__ = runtimeFilter;
		
		if(__filter__ != null) {
			Map<Object,Object> map = __filter__.generateMap();
			if(MapHelper.isNotEmpty(map)) {
				for(Map.Entry<Object,Object> entry : map.entrySet()) {
					if(__parameters__ == null || !__parameters__.containsKey(entry.getKey())) {
						if(__parameters__ == null)
							__parameters__ = new LinkedHashMap<>();
						__parameters__.put(entry.getKey(), entry.getValue());
					}
				}
			}
		}
		if(MapHelper.isNotEmpty(parameters))
			for(Map.Entry<Object,Object> entry : parameters.entrySet()) {
				if(entry.getKey() instanceof String) {
					__parameters__.put((String) entry.getKey(), entry.getValue());
				}
			}
		
		/* hints */
		__hints__ = new HashMap<>();
		if(MapHelper.isNotEmpty(hints))
			__hints__.putAll(hints);
		
		
		if(Boolean.TRUE.equals(__isEntityManagerClearable__) || Boolean.TRUE.equals(__isEntityManagerClosable__)) {
			__hints__.put("org.hibernate.readOnly",Boolean.TRUE);
		}
		
		if(Boolean.TRUE.equals(isResultCachable)) {
			__hints__.put("org.hibernate.cacheable", Boolean.TRUE);
		}
		return this;
	}
	
	public QueryExecutorArguments finalise() {
		if(__entityManager__ == null)
			return this;
		//if(Boolean.TRUE.equals(__isEntityManagerFlushable__))
		//	__entityManager__.flush();
		if(Boolean.TRUE.equals(__isEntityManagerClearable__))
			__entityManager__.clear();
		if(Boolean.TRUE.equals(__isEntityManagerClosable__))
			__entityManager__.close();
		if(Boolean.TRUE.equals(isResultProcessable) && CollectionHelper.isNotEmpty(__objects__))
			QueryResultProcessor.getInstance().process((Class<Object>)__objects__.iterator().next().getClass(), (Collection<Object>)__objects__, this);
		return this;
	}
	
	public Map<Object,Object> getParameters(Boolean injectIfNull) {
		if(parameters == null && Boolean.TRUE.equals(injectIfNull))
			parameters = new HashMap<>();
		return parameters;
	}
	
	public QueryExecutorArguments setParameters(Object...objects) {
		MapHelper.set(getParameters(Boolean.TRUE), objects);
		return this;
	}
	
	public QueryExecutorArguments addParameters(Object...objects) {
		MapHelper.set(getParameters(Boolean.TRUE), objects);
		return this;
	}
	
	public Filter getFilter(Boolean injectIfNull) {
		if(filter == null && Boolean.TRUE.equals(injectIfNull))
			filter = new Filter();
		return filter;
	}
	
	public QueryExecutorArguments addFilterField(String fieldName, Object fieldValue) {
		getFilter(Boolean.TRUE).addField(fieldName, fieldValue);
		return this;
	}
	
	public QueryExecutorArguments addFilterFieldsValues(Object...filterFieldsValues) {
		if(ArrayHelper.isEmpty(filterFieldsValues))
			return this;
		for(Integer index = 0 ; index < filterFieldsValues.length ; index = index + 2)
			addFilterField((String)filterFieldsValues[index], filterFieldsValues[index+1]);	
		return this;
	}

	public Object getFilterFieldValue(Collection<String> paths) {
		if(filter == null)
			return null;
		return filter.getFieldValue(paths);
	}
	
	public Object getFilterFieldValue(String...paths) {
		if(filter == null)
			return null;
		return filter.getFieldValue(paths);
	}
	
	public Field getFilterField(Collection<String> paths) {
		if(filter == null)
			return null;
		return filter.getField(paths);
	}
	
	public Field getFilterField(String...paths) {
		if(filter == null)
			return null;
		return filter.getField(paths);
	}
	
	public List<String> getFilterFieldValueLikes(String fieldName,Integer numberOfTokens) {
		if(StringHelper.isEmpty(fieldName))
			return null;
		Object value = filter == null ? null : filter.getFieldValue(fieldName);
		return QueryArgumentHelper.getLikes(value,numberOfTokens);
	}
	
	public QueryExecutorArguments transformFieldValueToLike(Collection<String> paths) {
		if(CollectionHelper.isEmpty(paths))
			return this;
		if(filter == null)
			return this;
		filter.transformFieldValueToLike(paths);
		return this;
	}
	
	public QueryExecutorArguments transformFieldValueToLike(String...paths) {
		if(ArrayHelper.isEmpty(paths))
			return this;
		return transformFieldValueToLike(CollectionHelper.listOf(paths));
	}
	
	public Collection<Object> getObjects(Boolean injectIfNull) {
		if(objects == null && Boolean.TRUE.equals(injectIfNull))
			objects = new ArrayList<>();
		return objects;
	}
	
	public QueryExecutorArguments addObjects(Collection<Object> objects) {
		if(CollectionHelper.isEmpty(objects))
			return this;
		getObjects(Boolean.TRUE).addAll(objects);
		return this;
	}
	
	public QueryExecutorArguments addObjects(Object...objects) {
		if(ArrayHelper.isEmpty(objects))
			return this;
		addObjects(CollectionHelper.listOf(objects));
		return this;
	}
	
	public Collection<Object> getSystemIdentifiers(Boolean injectIfNull) {
		if(systemIdentifiers == null && Boolean.TRUE.equals(injectIfNull))
			systemIdentifiers = new ArrayList<>();
		return systemIdentifiers;
	}
	
	public QueryExecutorArguments addSystemIdentifiers(Collection<Object> systemIdentifiers) {
		if(CollectionHelper.isEmpty(systemIdentifiers))
			return this;
		getSystemIdentifiers(Boolean.TRUE).addAll(systemIdentifiers);
		return this;
	}
	
	public QueryExecutorArguments addSystemIdentifiers(Object...systemIdentifiers) {
		if(ArrayHelper.isEmpty(systemIdentifiers))
			return this;
		addSystemIdentifiers(CollectionHelper.listOf(systemIdentifiers));
		return this;
	}

	public Boolean isFilterFieldTrue(String...paths) {
		return Boolean.TRUE.equals(ValueHelper.convertToBoolean(getFilterFieldValue(paths)));
	}
	
	public QueryExecutorArguments normalize(Collection<String> defaultProjectionsFieldsNames,Map<String,SortOrder> defaultSortOrders) {
		if(CollectionHelper.isEmpty(projections))
			addProjectionsFromStrings(defaultProjectionsFieldsNames);
		
		Collection<String> tuplePersistedFieldsNames = null;
		Collection<String> tupleTransientFieldsNames = null;
		if(CollectionHelper.isNotEmpty(projections)) {
			for(Projection projection : projections) {
				java.lang.reflect.Field field = FieldHelper.getByName(query.getTupleClass(), projection.getFieldName());
				if(field == null)
					continue;
				if(field.getAnnotation(Transient.class) == null) {
					if(tuplePersistedFieldsNames == null)
						tuplePersistedFieldsNames = new ArrayList<>();
					tuplePersistedFieldsNames.add(projection.getFieldName());
				}else {
					if(tupleTransientFieldsNames == null)
						tupleTransientFieldsNames = new ArrayList<>();
					tupleTransientFieldsNames.add(projection.getFieldName());
				}
			}
		}
		if(CollectionHelper.isNotEmpty(tupleTransientFieldsNames)) {
			if(CollectionHelper.isNotEmpty(projections))
				for(String tupleTransientFieldName : tupleTransientFieldsNames) {
					org.cyk.utility.persistence.query.Projection projectionIndex = null;
					for(org.cyk.utility.persistence.query.Projection index : projections)
						if(index.getFieldName().equals(tupleTransientFieldName)) {
							projectionIndex = index;
							break;
						}
					if(projectionIndex != null)
						projections.remove(projectionIndex);
				}
			addProcessableTransientFieldsNames(tupleTransientFieldsNames);
		}
		
		if(CollectionHelper.isEmpty(tuplePersistedFieldsNames) && CollectionHelper.isNotEmpty(tupleTransientFieldsNames)) {
			addProjectionsFromStrings(AbstractIdentifiableSystemImpl.FIELD_IDENTIFIER);
		}
		
		if(MapHelper.isEmpty(sortOrders))
			sortOrders = defaultSortOrders;
		return this;
	}
	
	public QueryExecutorArguments normalize() {
		return normalize(null,null);
	}
	
	public QueryExecutorArguments removeFilterFields(Collection<String> filterFieldsNames) {
		if(CollectionHelper.isEmpty(filterFieldsNames) || filter == null)
			return this;
		filter.removeFields(filterFieldsNames);
		return this;
	}
	
	public QueryExecutorArguments removeFilterFields(String...filterFieldsNames) {
		if(ArrayHelper.isEmpty(filterFieldsNames))
			return this;
		removeFilterFields(CollectionHelper.listOf(filterFieldsNames));
		return this;
	}
	
	@Override
	public String toString() {
		Collection<String> strings = new ArrayList<>();
		if(query != null)
			strings.add("Query="+query.getIdentifier());
		if(firstTupleIndex != null)
			strings.add("FTI="+firstTupleIndex);
		if(numberOfTuples != null)
			strings.add("NOT="+numberOfTuples);
		if(filter != null)
			strings.add("filter("+filter+")");
		if(isResultCachable != null)
			strings.add("QRC="+isResultCachable);
		if(collectionable != null)
			strings.add("COL="+collectionable);
		if(loggingLevel != null)
			strings.add("LOG="+loggingLevel);
		return StringHelper.concatenate(strings, " ");
	}
	
	/**/
	
	public static QueryExecutorArguments instantiate(Class<?> resultClass,QueryName queryName) {
		ThrowableHelper.throwIllegalArgumentExceptionIfNull("result class", resultClass);
		ThrowableHelper.throwIllegalArgumentExceptionIfNull("query name", queryName);		
		return instantiate(resultClass, QueryIdentifierGetter.getInstance().get(resultClass, queryName));
	}
	
	public static QueryExecutorArguments instantiate(Class<?> resultClass,String queryIdentifier) {
		ThrowableHelper.throwIllegalArgumentExceptionIfNull("result class", resultClass);
		ThrowableHelper.throwIllegalArgumentExceptionIfNull("query identifier", queryIdentifier);		
		Query query = QueryGetter.getInstance().get(queryIdentifier);
		if(query == null)
			throw new RuntimeException("query not found under identifier <<"+queryIdentifier+">>");
		QueryExecutorArguments arguments = new QueryExecutorArguments().setQuery(query);			
		return arguments;
	}
	
	public static QueryExecutorArguments setQueryIfNull(QueryExecutorArguments arguments,Class<?> klass,String queryName) {
		if(arguments == null)
			arguments = new QueryExecutorArguments();
		if(arguments.getQuery() == null)
			arguments.setQueryFromIdentifier(QueryIdentifierGetter.getInstance().get(klass, queryName));
		return arguments;
	}
	
	public static QueryExecutorArguments setQueryIfNull(QueryExecutorArguments arguments,Class<?> klass,QueryName queryName) {
		return setQueryIfNull(arguments, klass, queryName.getValue());
	}
	
	/*public static Boolean queryIdentifierEquals(QueryExecutorArguments arguments,Class<?> klass,QueryName queryName) {
		System.out.println("QueryExecutorArguments.queryIdentifierEquals(): "+arguments.getQuery());
		if(arguments == null || arguments.getQuery() == null || arguments.getQuery().getTupleClass() == null || klass == null || queryName == null)
			return null;
		return QueryIdentifierGetter.getInstance().get(klass, QueryName.READ_DYNAMIC)
				.equals(QueryIdentifierGetter.getInstance().get(arguments.getQuery().getTupleClass(), QueryName.READ_DYNAMIC));
	}*/
	
	/**/
	
	@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
	public static class Dto extends AbstractObject implements Serializable {
		private String queryIdentifier;
		private ArrayList<Projection.Dto> projections;
		private ArrayList<String> processableTransientFieldsNames;
		private ArrayList<String> flags;
		private ArrayList<String> resultsFieldsNames;
		private Filter.Dto filter;
		private LinkedHashMap<String,SortOrder> sortOrders;
		private Integer firstTupleIndex;
		private Integer numberOfTuples;
		private Boolean isResultCachable;
		private Boolean collectionable;
		private Boolean loggableAsInfo;
		private Boolean isProcessableAsAuditByDates;
		
		private Boolean queryBuildableAtRuntime;
		
		public Collection<String> getFlags(Boolean injectIfNull) {
			if(flags == null && Boolean.TRUE.equals(injectIfNull))
				flags = new ArrayList<>();
			return flags;
		}
		
		public Dto addFlags(Collection<String> flags) {
			if(CollectionHelper.isEmpty(flags))
				return this;
			getFlags(Boolean.TRUE).addAll(flags);
			return this;
		}
		
		public Dto addFlags(String...flags) {
			if(ArrayHelper.isEmpty(flags))
				return this;
			return addFlags(CollectionHelper.listOf(flags));
		}
		
		public Filter.Dto getFilter(Boolean injectIfNull) {
			if(filter == null && Boolean.TRUE.equals(injectIfNull))
				filter = new Filter.Dto();
			return filter;
		}
		
		public Dto addFilterField(String fieldName, Object fieldValue) {
			getFilter(Boolean.TRUE).addField(fieldName, fieldValue);
			return this;
		}
		
		public Dto addFilterFieldsValues(Object...filterFieldsValues) {
			if(ArrayHelper.isEmpty(filterFieldsValues))
				return this;
			for(Integer index = 0 ; index < filterFieldsValues.length ; index = index + 2)
				addFilterField((String)filterFieldsValues[index], filterFieldsValues[index+1]);	
			return this;
		}
		
		public ArrayList<Projection.Dto> getProjections(Boolean injectIfNull) {
			if(projections == null && Boolean.TRUE.equals(injectIfNull))
				projections = new ArrayList<>();
			return projections;
		}
		
		public Dto addProjections(ArrayList<Projection.Dto> projections) {
			if(CollectionHelper.isEmpty(projections))
				return this;
			getProjections(Boolean.TRUE).addAll(projections);
			return this;
		}
		
		public Dto addProjections(Projection.Dto...projections) {
			if(ArrayHelper.isEmpty(projections))
				return this;
			ArrayList<Projection.Dto> list = new ArrayList<>();
			list.addAll(CollectionHelper.listOf(projections));
			return addProjections(list);
		}
		
		public Dto addProjectionsFromStrings(ArrayList<String> strings) {
			if(CollectionHelper.isEmpty(strings))
				return this;
			ArrayList<Projection.Dto> projections = new ArrayList<>();
			for(String string : strings) {
				Projection.Dto projection = new Projection.Dto().setFieldName(string);
				projections.add(projection);
			}
			getProjections(Boolean.TRUE).addAll(projections);
			return this;
		}
		
		public Dto addProjectionsFromStrings(String...strings) {
			if(ArrayHelper.isEmpty(strings))
				return this;
			ArrayList<String> list = new ArrayList<>();
			list.addAll(CollectionHelper.listOf(strings));
			return addProjectionsFromStrings(list);
		}
		
		public ArrayList<String> getProcessableTransientFieldsNames(Boolean injectIfNull) {
			if(processableTransientFieldsNames == null && Boolean.TRUE.equals(injectIfNull))
				processableTransientFieldsNames = new ArrayList<>();
			return processableTransientFieldsNames;
		}
		
		public Dto addProcessableTransientFieldsNames(ArrayList<String> processableTransientFieldsNames) {
			if(CollectionHelper.isEmpty(processableTransientFieldsNames))
				return this;
			getProcessableTransientFieldsNames(Boolean.TRUE).addAll(processableTransientFieldsNames);
			return this;
		}
		
		public Dto addProcessableTransientFieldsNames(String...processableTransientFieldsNames) {
			if(ArrayHelper.isEmpty(processableTransientFieldsNames))
				return this;
			ArrayList<String> list = new ArrayList<>();
			list.addAll(CollectionHelper.listOf(processableTransientFieldsNames));
			return addProcessableTransientFieldsNames(list);
		}
		
		public ArrayList<String> getResultsFieldsNames(Boolean injectIfNull) {
			if(resultsFieldsNames == null && Boolean.TRUE.equals(injectIfNull))
				resultsFieldsNames = new ArrayList<>();
			return resultsFieldsNames;
		}
		
		public Dto addResultsFieldsNames(ArrayList<String> resultsFieldsNames) {
			if(CollectionHelper.isEmpty(resultsFieldsNames))
				return this;
			getResultsFieldsNames(Boolean.TRUE).addAll(resultsFieldsNames);
			return this;
		}
		
		public Dto addResultsFieldsNames(String...resultsFieldsNames) {
			if(ArrayHelper.isEmpty(resultsFieldsNames))
				return this;
			ArrayList<String> list = new ArrayList<>();
			list.addAll(CollectionHelper.listOf(resultsFieldsNames));
			return addResultsFieldsNames(list);
		}
		
		@Override
		public String toString() {
			Collection<String> strings = new ArrayList<>();
			if(StringHelper.isNotBlank(queryIdentifier))
				strings.add("QID="+queryIdentifier);
			if(firstTupleIndex != null)
				strings.add("FTI="+firstTupleIndex);
			if(numberOfTuples != null)
				strings.add("NOT="+numberOfTuples);
			if(filter != null)
				strings.add("filter("+filter+")");
			if(isResultCachable != null)
				strings.add("QRC="+isResultCachable);
			if(collectionable != null)
				strings.add("COL="+collectionable);
			if(loggableAsInfo != null)
				strings.add("LOG="+loggableAsInfo);
			return StringHelper.concatenate(strings, " ");
		}
		
		/**/
		
		@org.mapstruct.Mapper
		public static abstract class Mapper extends MapperSourceDestination.AbstractImpl<Dto, QueryExecutorArguments> {

			@Override
			protected void __listenGetDestinationAfter__(Dto source, QueryExecutorArguments destination) {
				super.__listenGetDestinationAfter__(source, destination);
				if(StringHelper.isNotBlank(source.getQueryIdentifier()))
					destination.setQuery(QueryGetter.getInstance().get(source.getQueryIdentifier()));
				if(destination.getQuery() == null)
					destination.setQuery(new Query().setIdentifier(source.getQueryIdentifier()));
				if(Boolean.TRUE.equals(source.getLoggableAsInfo()))
					destination.setLoggingLevel(java.util.logging.Level.INFO);
			}
			
			public Filter.Dto getFilterDto(Filter filter) {
				if(filter == null)
					return null;
				return MappingHelper.getSource(filter, Filter.Dto.class);
			}
			
			public Filter getFilter(Filter.Dto filter) {
				if(filter == null)
					return null;
				return MappingHelper.getDestination(filter, Filter.class);
			}
		}
	}
}