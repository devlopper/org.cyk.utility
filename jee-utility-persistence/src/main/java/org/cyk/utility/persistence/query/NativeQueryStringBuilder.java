package org.cyk.utility.persistence.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.persistence.PersistenceHelper;

public interface NativeQueryStringBuilder {

	String buildInsertOneFromMap(Class<?> klass,Map<String,String> map);
	<T> String buildInsertOne(Class<T> klass,T object);
	
	String buildInsertManyFromMaps(Class<?> klass,Collection<Map<String,String>> maps);
	//<T> String buildInsertMany(Class<T> klass,Collection<T> collection,Boolean isBooleanAsNumber);
	<T> String buildInsertMany(Class<T> klass,Collection<T> collection);
	
	String buildUpdateManyFromMaps(Class<?> klass,Collection<Map<String,String>> maps);
	<T> String buildUpdateMany(Class<T> klass,Collection<Object> objects);
	
	String buildDeleteManyByIdentifiers(Class<?> klass,Collection<Object> identifiers);
	<T> String buildDeleteMany(Class<T> klass,Collection<Object> objects);
	
	Collection<String> buildDeleteManyByIdentifiersByBatches(Class<?> klass,List<Object> identifiers,Integer batchSize);
	<T> Collection<String> buildDeleteManyByBatches(Class<T> klass,List<Object> objects,Integer batchSize);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements NativeQueryStringBuilder,Serializable {
		
		/* Insert */
		
		@Override
		public String buildInsertOneFromMap(Class<?> klass, Map<String, String> map) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("klass", klass);
			ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("map", map);
			String tableName = getTableName(klass);
			return String.format(INSERT_FORMAT,getIntoValues(tableName, map.keySet(), map.values()));
		}
		
		@Override
		public <T> String buildInsertOne(Class<T> klass, T object) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("klass", klass);
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("object", object);
			return buildInsertOneFromMap(klass, PersistenceHelper.getColumnsValuesAsMap(object));
		}
		
		protected String getIntoValues(String tableName,Set<String> columnsNames,Collection<String> values) {
			return String.format(INTO_FORMAT,tableName,StringUtils.join(columnsNames,","),StringUtils.join(values,","));
		}
		
		protected Boolean getIsBooleanAsNumber() {
			return Boolean.FALSE;
		}
		
		/* Update */
		
		@Override
		public <T> String buildUpdateMany(Class<T> klass, Collection<Object> objects) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("klass", klass);
			ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("objects", objects);
			return buildUpdateManyFromMaps(klass, objects.stream().map(object -> PersistenceHelper.getColumnsValuesAsMap(object)).collect(Collectors.toList()));
		}
		
		@Override
		public String buildUpdateManyFromMaps(Class<?> klass, Collection<Map<String, String>> maps) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("klass", klass);
			ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("map", maps);
			Collection<String> names = maps.iterator().next().keySet();
			ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("names", names);
			String tableName = getTableName(klass);
			String identifierName = getPrimaryKeyColumnName(klass);
			Collection<String> identifiers = new ArrayList<>();
			Collection<String> sets = new ArrayList<>();
			for(String name : names) {
				if(name.equals(identifierName))
					continue;
				Map<Object, Object> values = new HashMap<>();
				for(Map<String, String> map : maps) {
					String identifier = map.get(identifierName);
					identifiers.add(identifier);
					values.put(identifier, map.get(name));
				}
				sets.add(buildUpdateSet(name, values, identifierName));
			}
			return String.format(UPDATE_BY_IDENTIFIERS_FORMAT, tableName,String.format(UPDATE_SET_FORMAT, StringUtils.join(sets,",")),identifierName,StringUtils.join(identifiers,","));
		}
		
		protected String buildUpdateSet(String name,Map<Object, Object> values,String identifierName) {
			String whens = values.entrySet().stream().map(entry -> String.format(UPDATE_CASE_WHEN_IDENTIFIER_FORMAT, identifierName,entry.getKey(),entry.getValue())).collect(Collectors.joining(" "));
			return String.format(UPDATE_SET_FORMAT_NAME_VALUE,name, String.format(UPDATE_CASE_FORMAT, whens));
		}
		
		/* Delete */
		
		@Override
		public <T> String buildDeleteMany(Class<T> klass, Collection<Object> objects) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("klass", klass);
			ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("objects", objects);
			return buildDeleteManyByIdentifiers(klass, FieldHelper.readSystemIdentifiers(objects));
		}
		
		@Override
		public String buildDeleteManyByIdentifiers(Class<?> klass, Collection<Object> identifiers) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("klass", klass);
			ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("identifiers", identifiers);
			String tableName = getTableName(klass);
			String identifierName = getPrimaryKeyColumnName(klass);
			return String.format(DELETE_BY_IDENTIFIERS_FORMAT, tableName,identifierName,identifiers.stream()
					.map(identifier -> PersistenceHelper.stringifyColumnValue(identifier)).collect(Collectors.joining(",")));
		}
		
		@Override
		public <T> Collection<String> buildDeleteManyByBatches(Class<T> klass, List<Object> objects,Integer batchSize) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("klass", klass);
			ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("objects", objects);
			Collection<String> queries = null;
			for(List<Object> batch : CollectionHelper.getBatches(objects, batchSize)) {
				String query = buildDeleteMany(klass, batch);
				if(StringHelper.isBlank(query))
					continue;
				if(queries == null)
					queries = new ArrayList<>();
				queries.add(query);
			}
			return queries;
		}
		
		@Override
		public Collection<String> buildDeleteManyByIdentifiersByBatches(Class<?> klass, List<Object> identifiers,Integer batchSize) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("klass", klass);
			ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("identifiers", identifiers);
			Collection<String> queries = null;
			for(List<Object> batch : CollectionHelper.getBatches(identifiers, batchSize)) {
				String query = buildDeleteManyByIdentifiers(klass, batch);
				if(StringHelper.isBlank(query))
					continue;
				if(queries == null)
					queries = new ArrayList<>();
				queries.add(query);
			}
			return queries;
		}
		
		/**/
		
		protected String getTableName(Class<?> klass) {
			String name = PersistenceHelper.getTableName(klass);
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank(String.format("Table name of class %s",klass), name);
			return name;
		}
		
		protected String getPrimaryKeyColumnName(Class<?> klass) {
			String name = PersistenceHelper.getPrimaryKeyColumnName(klass);
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank(String.format("Primary column name of class %s",klass), name);
			return name;
		}
		
		/**/
		
		private static final String INSERT_FORMAT = "INSERT %s";
		private static final String INTO_FORMAT = "INTO %s (%s) VALUES (%s)";
		
		private static final String UPDATE_BY_IDENTIFIERS_FORMAT = "UPDATE %s %s WHERE %s IN (%s)";
		private static final String UPDATE_SET_FORMAT = "SET %s";
		private static final String UPDATE_SET_FORMAT_NAME_VALUE = "%s = %s";
		private static final String UPDATE_CASE_FORMAT = "CASE %s END";
		private static final String UPDATE_CASE_WHEN_IDENTIFIER_FORMAT = "WHEN %s = %s THEN %s";
		
		private static final String DELETE_BY_IDENTIFIERS_FORMAT = "DELETE FROM %s WHERE %s IN (%s)";
	}
}