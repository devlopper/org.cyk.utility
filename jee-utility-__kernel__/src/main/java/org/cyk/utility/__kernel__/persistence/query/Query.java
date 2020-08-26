package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Tuple;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.jboss.weld.exceptions.IllegalArgumentException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @EqualsAndHashCode(of = {"identifier"},callSuper = false)
public class Query extends AbstractObject implements Serializable {

	private Class<?> tupleClass;
	private String identifier;
	private String value;
	private Class<?> intermediateResultClass;
	private Class<?> resultClass;
	private Query queryDerivedFromQuery;
	private Map<String,Integer> tupleFieldsNamesIndexes;
	
	public Query setQueryDerivedFromQueryIdentifier(Object identifier){
		setQueryDerivedFromQuery(QueryHelper.getQueries().getBySystemIdentifier(identifier, Boolean.TRUE));
		return this;
	}
	
	public Boolean isQueryDerivedFromQueryIdentifierEqualsTo(Object identifer){
		return getQueryDerivedFromQuery()!=null && getQueryDerivedFromQuery().getIdentifier().equals(identifer);
	}
	
	public Boolean isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(Object value){
		Object identifier = getIdentifier();
		return identifier!=null && (identifier.equals(value) || isQueryDerivedFromQueryIdentifierEqualsTo(value));
	}
	
	public Query setTupleFieldsNamesIndexesFromFieldsNames(String...fieldsNames) {
		setTupleFieldsNamesIndexes(MapHelper.instantiateStringIntegerByStrings(fieldsNames));
		return this;
	}
	
	@Override
	public String toString() {
		return String.format(TO_STRING_FORMAT, Query.class.getSimpleName(),getIdentifier(),getValue(),getTupleClass(),getResultClass());
	}
	
	/**/
	
	public static final String FIELD_TUPLE_CLASS = "tupleClass";
	public static final String FIELD_IDENTIFIER = "identifier";
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_RESULT_CLASS = "resultClass";
	
	/**/
	
	public static class ConfiguratorImpl extends Configurator.AbstractImpl<Query> implements Serializable {
		
		@Override
		public void configure(Query query, Map<Object, Object> arguments) {
			super.configure(query, arguments);
			if(StringHelper.isBlank(query.identifier)) {
				if(query.tupleClass != null && StringHelper.isNotBlank((String) MapHelper.readByKey(arguments, FIELD_NAME))) {
					query.identifier = QueryIdentifierBuilder.getInstance().build(query.tupleClass, (String) MapHelper.readByKey(arguments, FIELD_NAME));
				}
			}
			if(query.resultClass == null || Void.class.equals(query.resultClass)) {
				if(StringHelper.isNotBlank(query.value) && query.value.toLowerCase().startsWith("select"))
					query.resultClass = query.tupleClass;
			}
			
			if(!Long.class.equals(query.resultClass) && query.tupleFieldsNamesIndexes == null && StringHelper.isNotBlank(query.value)) {
				if(StringUtils.startsWithIgnoreCase(query.value, "select")) {
					String stringBetweenSelectAndFrom = StringUtils.substring(query.value, "select".length(), StringUtils.indexOfIgnoreCase(query.value, "from")).strip();
					if(!StringUtils.startsWithIgnoreCase(stringBetweenSelectAndFrom, "new")) {
						List<String> fieldsNames = CollectionHelper.listOf(stringBetweenSelectAndFrom
								.split(",")).stream().map(fieldName -> StringUtils.substringAfter(fieldName, ".")).collect(Collectors.toList());					
						Integer index = 0;
						for(String fieldName : fieldsNames) {
							fieldName = fieldName.strip();
							if(fieldName.isBlank())
								continue;
							if(query.tupleFieldsNamesIndexes == null)
								query.tupleFieldsNamesIndexes = new HashMap<>();
							query.tupleFieldsNamesIndexes.put(fieldName, index++);
						}
					}				
				}
			}
			
			if(query.intermediateResultClass == null && MapHelper.isNotEmpty(query.tupleFieldsNamesIndexes) && !Tuple.class.equals(query.resultClass)) {
				query.intermediateResultClass = Object[].class;
			}
		}
		
		@Override
		protected Class<Query> __getClass__() {
			return Query.class;
		}
		
		public static final String FIELD_NAME = "name";
	}
	
	public static Query build(Map<Object,Object> arguments) {
		return Builder.build(Query.class,arguments);
	}
	
	public static Query build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	public static Collection<Query> buildFromAnnotation(org.cyk.utility.__kernel__.persistence.query.annotation.Query annotation) {
		if(annotation == null)
			return null;
		Collection<Query> queries = null;
		Query query =  build(FIELD_TUPLE_CLASS,annotation.tupleClass(),FIELD_IDENTIFIER,ValueHelper.defaultToIfBlank(annotation.identifier(),null)
				,ConfiguratorImpl.FIELD_NAME,ValueHelper.defaultToIfBlank(annotation.name(),null)
				,FIELD_VALUE,ValueHelper.defaultToIfBlank(annotation.value(),null),FIELD_RESULT_CLASS
				,annotation.resultClass());
		if(query == null)
			return null;
		if(queries == null)
			queries = new ArrayList<>();
		queries.add(query);
		if(Boolean.TRUE.equals(annotation.isDerivable())) {
			if(!query.getResultClass().equals(Long.class)) {
				query = Query.buildCountFromSelect(query);
				if(query != null)
					queries.add(query);	
			}						
		}
		return queries;
	}
	
	public static Collection<Query> buildFromAnnotation(org.cyk.utility.__kernel__.persistence.query.annotation.Queries annotation) {
		if(annotation == null || ArrayHelper.isEmpty(annotation.value()))
			return null;
		Collection<Query> queries = null;
		for(org.cyk.utility.__kernel__.persistence.query.annotation.Query queryAnnotation : annotation.value()) {
			Collection<Query> __queries__ = buildFromAnnotation(queryAnnotation);
			if(CollectionHelper.isEmpty(__queries__))
				continue;
			if(queries == null)
				queries = new ArrayList<>();
			queries.addAll(__queries__);
		}
		return queries;
	}
	
	public static Query build(Class<?> tupleClass,String name,String value,Class<?> resultClass) {
		return build(FIELD_TUPLE_CLASS,tupleClass,ConfiguratorImpl.FIELD_NAME,name,FIELD_VALUE,value,FIELD_RESULT_CLASS,resultClass);
	}
	
	public static Query build(Class<?> tupleClass,String name,String value) {
		return build(tupleClass, name, value, null);
	}
	
	/* Select */
	
	public static Query buildSelect(Class<?> tupleClass,String identifier,String value,Map<String,Integer> tupleFieldsNamesIndexes) {
		return Query.build(Query.FIELD_IDENTIFIER,identifier,Query.FIELD_TUPLE_CLASS,tupleClass,Query.FIELD_RESULT_CLASS,tupleClass,Query.FIELD_VALUE,value)
				.setTupleFieldsNamesIndexes(tupleFieldsNamesIndexes);
	}
	
	public static Query buildSelect(Class<?> tupleClass,String identifier,String value) {
		return buildSelect(tupleClass, identifier, value, null);
	}
	
	public static Query buildSelect(Class<?> tupleClass,String value) {
		return build(tupleClass, QueryName.READ.getValue(), value, null);
	}
	
	public static Query buildSelectBySystemIdentifiers(Class<?> tupleClass,String value) {
		return build(tupleClass, QueryName.READ_BY_SYSTEM_IDENTIFIERS.getValue(), value, null);
	}
	
	public static Query buildSelectByBusinessIdentifiers(Class<?> tupleClass,String value) {
		return build(tupleClass, QueryName.READ_BY_BUSINESS_IDENTIFIERS.getValue(), value, null);
	}
	
	/* Count */
	
	public static Query buildCount(String identifier,String value) {
		return Query.build(Query.FIELD_IDENTIFIER,identifier,Query.FIELD_RESULT_CLASS,Long.class,Query.FIELD_VALUE,value);			
	}
	
	public static Query buildCount(Class<?> tupleClass,String name,String value) {
		return build(tupleClass, name, value, Long.class);
	}
	
	public static Query buildCount(Class<?> tupleClass,String value) {
		return buildCount(tupleClass, QueryName.COUNT.getValue(), value);
	}
	
	public static Query buildCountFromSelect(Query selectQuery) {
		if(selectQuery == null || StringHelper.isBlank(selectQuery.getValue()))
			throw new IllegalArgumentException("select query is required");
		String identifier = QueryIdentifierBuilder.getInstance().buildCountFrom(selectQuery.getIdentifier());
		String value = QueryValueBuilder.buildCountFromSelect(selectQuery.getValue());
		if(StringHelper.isNotBlank(value))
			return new Query().setIdentifier(identifier).setValue(value).setTupleClass(selectQuery.getTupleClass()).setResultClass(Long.class);
		throw new IllegalArgumentException(String.format("we cannot build count query from following select query : %s",selectQuery));
	}
	
	/* Select And Derivation*/
	
	public static Collection<Query> buildSelect(Class<?> tupleClass,String value,Boolean isCountDerivable) {
		Collection<Query> queries = null;
		Query query = buildSelect(tupleClass, value);
		if(query != null) {
			if(queries == null)
				queries = new ArrayList<>();
			queries.add(query);
		}
		if(Boolean.TRUE.equals(isCountDerivable)) {
			query = buildCountFromSelect(query);
			if(query != null) {
				if(queries == null)
					queries = new ArrayList<>();
				queries.add(query);
			}			
		}		
		return queries;
	}
	
	/**/
	
	static {
		Configurator.set(Query.class, new ConfiguratorImpl());
	}
	
	private static final String TO_STRING_FORMAT = "%s(%s , %s , %s , %s)";
}
