package org.cyk.utility.persistence.server.query;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.persistence.Query;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.object.marker.AuditableWhoDoneWhatWhen;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.time.TimeHelper;
import org.cyk.utility.persistence.entity.AbstractIdentifiableSystemScalarStringAuditedImpl;
import org.cyk.utility.persistence.query.Querier;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder;

public interface ArraysReaderByIdentifiers<ENTITY,IDENTIFIER> extends Reader<ENTITY, IDENTIFIER, Object[]> {

	Collection<Object[]> read(Collection<ENTITY> entities,Map<String,Object> parameters);
	Collection<Object[]> read(Map<String,Object> parameters,ENTITY...entities);
	Collection<Object[]> readByIdentifiers(Collection<IDENTIFIER> identifiers,Map<String,Object> parameters);
	Collection<Object[]> readByIdentifiers(Map<String,Object> parameters,IDENTIFIER...identifiers);
	
	void set(ENTITY entity,Object[] array);
	void set(Collection<ENTITY> entities,Collection<Object[]> arrays);
	
	void readThenSet(Collection<ENTITY> entities,Map<String,Object> parameters);
	
	<T> Collection<T> readByIdentifiersThenInstantiate(Class<T> klass,Collection<IDENTIFIER> identifiers,Map<String,Object> parameters);
	<T> Collection<T> readByIdentifiersThenInstantiate(Class<T> klass,Map<String,Object> parameters,IDENTIFIER...identifiers);
	
	Collection<ENTITY> readByIdentifiersThenInstantiate(Collection<IDENTIFIER> identifiers,Map<String,Object> parameters);
	Collection<ENTITY> readByIdentifiersThenInstantiate(Map<String,Object> parameters,IDENTIFIER...identifiers);
	
	/**/
	
	public static abstract class AbstractImpl<ENTITY,IDENTIFIER> extends Reader.AbstractImpl<ENTITY, IDENTIFIER, Object[]> implements ArraysReaderByIdentifiers<ENTITY,IDENTIFIER>,Serializable {
		
		@Override
		public Collection<Object[]> readByIdentifiers(Collection<IDENTIFIER> identifiers,Map<String,Object> parameters) {
			if(CollectionHelper.isEmpty(identifiers))
				return null;
			return new ReaderByCollection.AbstractImpl<IDENTIFIER,Object[]>() {
				@Override
				public Collection<Object[]> __read__(Collection<IDENTIFIER> values) {
					return __readByIdentifiers__(values,parameters);
				}
			}.read(identifiers);
		}

		protected Collection<Object[]> __readByIdentifiers__(Collection<IDENTIFIER> identifiers,Map<String,Object> parameters) {
			Query query = instantiateQuery();
			setQueryParameters(query, identifiers,parameters);
			return query.getResultList();
		}
		
		protected Collection<IDENTIFIER> processIdentifiers(Collection<IDENTIFIER> identifiers) {
			return identifiers;
		}
		
		protected void setQueryParameters(Query query,Collection<IDENTIFIER> identifiers,Map<String,Object> parameters) {
			query.setParameter("identifiers", processIdentifiers(identifiers));
		}
		
		public static void setQueryParameter(Query query, Collection<String> identifiers, Map<String, Object> parameters,String name,String label) {
			if(StringHelper.isBlank(name))
				return;
			String value = (String) MapHelper.readByKey(parameters, name);
			if(StringHelper.isBlank(value))
				throw new RuntimeException(label+" is required");
			query.setParameter(name, value);
		}
		
		@Override
		public Collection<Object[]> readByIdentifiers(Map<String,Object> parameters,IDENTIFIER... identifiers) {
			if(ArrayHelper.isEmpty(identifiers))
				return null;
			return readByIdentifiers(CollectionHelper.listOf(identifiers),parameters);
		}
		
		@Override
		public Collection<Object[]> read(Map<String,Object> parameters,ENTITY... entities) {
			if(ArrayHelper.isEmpty(entities))
				return null;
			return read(CollectionHelper.listOf(entities),parameters);
		}
		
		@Override
		public Collection<Object[]> read(Collection<ENTITY> entities,Map<String,Object> parameters) {
			if(CollectionHelper.isEmpty(entities))
				return null;
			return readByIdentifiers((Collection<IDENTIFIER>) FieldHelper.readSystemIdentifiers(entities),parameters);
		}
		
		@Override
		public void set(ENTITY entity,Object[] array) {
			if(entity == null || array == null || array.length == 0)
				return;
			__set__(entity, array);
		}
		
		protected void __set__(ENTITY entity,Object[] array) {
			LogHelper.logWarning("set "+getEntityClass()+" not yet implemented", getClass());
		}
		
		protected void __setObject__(Object object,Object[] array) {
			LogHelper.logWarning("set object not yet implemented", getClass());
		}
		
		@Override
		public void set(Collection<ENTITY> entities,Collection<Object[]> arrays) {
			if(CollectionHelper.isEmpty(entities))
				return;
			if(CollectionHelper.isEmpty(arrays)) {
				entities.forEach(entity -> {
					processWhenHasNoEntityArray(entity);
				});
				return;
			}
			for(ENTITY entity : entities) {
				Boolean hasEntityArray = null;
				for(Object[] array : arrays) {
					if(isEntityArray(entity, array)) {
						hasEntityArray = Boolean.TRUE;
						__set__(entity, array);
						if(Boolean.TRUE.equals(isEntityHasOnlyArray(entity)))
							break;
					}
				}
				if(Boolean.TRUE.equals(hasEntityArray))
					listenAfterArraySet(entity);
				else
					processWhenHasNoEntityArray(entity);
			}
		}
		
		protected Boolean isEntityArray(ENTITY entity,Object[] array) {
			return array[0].equals(FieldHelper.readSystemIdentifier(entity));
		}
		
		protected Boolean isEntityHasOnlyArray(ENTITY entity) {
			return Boolean.TRUE;
		}
		
		protected void processWhenHasNoEntityArray(ENTITY entity) {
		
		}
		
		protected void listenAfterArraySet(ENTITY entity) {
			
		}
		
		@Override
		public void readThenSet(Collection<ENTITY> entities, Map<String, Object> parameters) {
			Collection<Object[]> arrays = read(entities, parameters);
			set(entities, arrays);
		}
		
		@Override
		public <T> Collection<T> readByIdentifiersThenInstantiate(Class<T> klass, Collection<IDENTIFIER> identifiers,Map<String, Object> parameters) {
			if(klass == null || CollectionHelper.isEmpty(identifiers))
				return null;
			Collection<Object[]> arrays = readByIdentifiers(identifiers, parameters);
			if(CollectionHelper.isEmpty(arrays))
				return null;
			Collection<T> collection = null;
			Class<?> entityClass = getEntityClass();
			for(Object[] array : arrays) {
				if(array == null)
					continue;
				T object = ClassHelper.instanciate(klass);
				if(klass.equals(entityClass))
					__set__((ENTITY) object, array);
				else
					__setObject__(object, array);
				if(collection == null)
					collection = new ArrayList<>();
				collection.add(object);
			}
			return collection;
		}
		
		@Override
		public <T> Collection<T> readByIdentifiersThenInstantiate(Class<T> klass, Map<String, Object> parameters,IDENTIFIER... identifiers) {
			if(klass == null || ArrayHelper.isEmpty(identifiers))
				return null;
			return readByIdentifiersThenInstantiate(klass,CollectionHelper.listOf(Boolean.TRUE, identifiers), parameters);
		}
		
		@Override
		public Collection<ENTITY> readByIdentifiersThenInstantiate(Collection<IDENTIFIER> identifiers,Map<String, Object> parameters) {
			return readByIdentifiersThenInstantiate(getEntityClass(), identifiers, parameters);
		}
		
		@Override
		public Collection<ENTITY> readByIdentifiersThenInstantiate(Map<String, Object> parameters,IDENTIFIER... identifiers) {
			if(ArrayHelper.isEmpty(identifiers))
				return null;
			return readByIdentifiersThenInstantiate(CollectionHelper.listOf(Boolean.TRUE, identifiers), parameters);
		}
		
		protected QueryStringBuilder.Arguments instantiateQueryStringBuilderArguments() {
			QueryStringBuilder.Arguments arguments = super.instantiateQueryStringBuilderArguments();
			arguments.getPredicate(Boolean.TRUE).ands(instantiateQueryStringBuilderArgumentsPredicates());
			return arguments;
		}
		
		protected Collection<String> instantiateQueryStringBuilderArgumentsPredicates() {
			return CollectionHelper.listOf("t.identifier IN :"+Querier.PARAMETER_NAME_IDENTIFIERS);
		}
		/*
		protected static void instantiateQueryStringBuilderArgumentsAudits(QueryStringBuilder.Arguments arguments,String variableName,String identifierFieldName,String auditWhoFieldName,String auditFunctionalityFieldName,String auditWhatFieldName,String auditWhenFieldName) {
			arguments.getProjection(Boolean.TRUE).addFromTuple(variableName,identifierFieldName,auditWhoFieldName,auditFunctionalityFieldName,auditWhatFieldName,auditWhenFieldName);
		}
		
		protected static void instantiateQueryStringBuilderArgumentsAudits(QueryStringBuilder.Arguments arguments) {
			instantiateQueryStringBuilderArgumentsAudits(arguments, "t","identifier","__auditWho__","__auditFunctionality__","__auditWhat__","__auditWhen__");
		}
		*/
		/**/
		
		protected static String getAsString(Object[] array,Integer index) {
			if(ArrayHelper.isEmpty(array) || NumberHelper.isLessThanZero(index))
				return null;
			return (String) array[index];
		}
		
		protected static Long getAsLong(Object[] array,Integer index) {
			if(ArrayHelper.isEmpty(array) || NumberHelper.isLessThanZero(index))
				return null;
			return (Long) array[index];
		}
		
		protected static Integer getAsInteger(Object[] array,Integer index) {
			if(ArrayHelper.isEmpty(array) || NumberHelper.isLessThanZero(index))
				return null;
			return (Integer) array[index];
		}
		
		protected static Short getAsShort(Object[] array,Integer index) {
			if(ArrayHelper.isEmpty(array) || NumberHelper.isLessThanZero(index))
				return null;
			return (Short) array[index];
		}
		
		protected static Byte getAsByte(Object[] array,Integer index) {
			if(ArrayHelper.isEmpty(array) || NumberHelper.isLessThanZero(index))
				return null;
			return (Byte) array[index];
		}
		
		protected static Boolean getAsBoolean(Object[] array,Integer index) {
			if(ArrayHelper.isEmpty(array) || NumberHelper.isLessThanZero(index))
				return null;
			return (Boolean) array[index];
		}
		
		protected static LocalDateTime getAsLocalDateTime(Object[] array,Integer index) {
			if(ArrayHelper.isEmpty(array) || NumberHelper.isLessThanZero(index))
				return null;
			return (LocalDateTime) array[index];
		}
		
		protected static LocalDate getAsLocalDate(Object[] array,Integer index) {
			if(ArrayHelper.isEmpty(array) || NumberHelper.isLessThanZero(index))
				return null;
			return (LocalDate) array[index];
		}
		
		protected static String formatLocalDateTime(Object[] array,Integer index) {
			if(ArrayHelper.isEmpty(array) || NumberHelper.isLessThanZero(index))
				return null;
			return TimeHelper.formatLocalDateTime((LocalDateTime) array[index],"dd/MM/yyyy à HH:mm");
		}
		
		protected static String formatLocalDate(Object[] array,Integer index) {
			if(ArrayHelper.isEmpty(array) || NumberHelper.isLessThanZero(index))
				return null;
			return TimeHelper.formatLocalDate((LocalDate) array[index],"dd/MM/yyyy");
		}
		
		protected static void addAuditProjectionsFromTuple(QueryStringBuilder.Arguments arguments,String variableName) {
			arguments.getProjection(Boolean.TRUE).addFromTuple(variableName,AbstractIdentifiableSystemScalarStringAuditedImpl.FIELD___AUDIT_WHO__,AbstractIdentifiableSystemScalarStringAuditedImpl.FIELD___AUDIT_FUNCTIONALITY__
					,AbstractIdentifiableSystemScalarStringAuditedImpl.FIELD___AUDIT_WHAT__,AbstractIdentifiableSystemScalarStringAuditedImpl.FIELD___AUDIT_WHEN__);
		}
		
		protected static void addAuditProjectionsFromTuple(QueryStringBuilder.Arguments arguments) {
			addAuditProjectionsFromTuple(arguments, "t");
		}
		
		protected static void __setAudits__(AuditableWhoDoneWhatWhen audited, Object[] array,Integer index) {
			audited.set__auditWho__(getAsString(array, index++));
			audited.set__auditFunctionality__(getAsString(array, index++));
			audited.set__auditWhat__(getAsString(array, index++));
			audited.set__auditWhen__((LocalDateTime) array[index++]);
		}
		
		protected static void __setAuditsAsStrings__(AuditableWhoDoneWhatWhen audited, Object[] array) {
			audited.set__auditWhenAsString__(TimeHelper.formatLocalDateTime(audited.get__auditWhen__()));
			audited.set__auditWhen__(null);
		}
		
		protected static void __setAudit__(AuditableWhoDoneWhatWhen audited, Object[] array) {
			audited.set__audit__(String.format(AUDIT_FORMAT, audited.get__auditFunctionality__(),audited.get__auditWho__(),audited.get__auditWhenAsString__()));
			audited.set__auditWho__(null);
			audited.set__auditFunctionality__(null);
			audited.set__auditWhat__(null);
			audited.set__auditWhen__(null);
		}
		
		protected static void addJoin(QueryStringBuilder.Arguments arguments,Class<?> entityClass,String variable,String joinedIdentifierFieldName) {
			arguments.getTuple(Boolean.TRUE).addJoins(String.format("LEFT JOIN %1$s %2$s ON %2$s.%3$s = t.%4$s", FieldHelper.readStatic(entityClass, "ENTITY_NAME"),variable
					,FieldHelper.readStatic(entityClass, "FIELD_IDENTIFIER"),joinedIdentifierFieldName));
		}
		
		public static final String AUDIT_FORMAT = "%s par %s le %s";
		
		/**/
		
		/**/
		
		public static abstract class DefaultImpl<ENTITY> extends ArraysReaderByIdentifiers.AbstractImpl<ENTITY, String> implements Serializable {
			
		}
	}
}