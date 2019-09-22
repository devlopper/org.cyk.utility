package org.cyk.utility.field;

import java.lang.reflect.Field;
import java.util.Collection;

import org.cyk.utility.__kernel__.field.FieldName;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.string.Strings;

public interface FieldHelper extends org.cyk.utility.__kernel__.field.FieldHelper {
	
	String buildFieldName(Class<?> klass,FieldName fieldName,ValueUsageType valueUsageType);
	
	Field getFieldByName(Class<?> klass,String fieldName);
	Field getFieldByName(Class<?> klass,FieldName fieldName,ValueUsageType valueUsageType);
	
	Object getFieldValueSystemIdentifier(Object object);
	Object getFieldValueBusinessIdentifier(Object object);
	Object getFieldValueSystemOrBusinessIdentifier(Object object);
	
	FieldHelper setFieldValueSystemIdentifier(Object object,Object value);
	FieldHelper setFieldValueBusinessIdentifier(Object object,Object value);
	
	FieldHelper copy(Object source,Object destination,Properties properties);
	FieldHelper copy(Object source,Object destination);
	
	<TYPE> Class<TYPE> getParameterAt(Field field, Integer index, Class<TYPE> typeClass);
	
	Field getField(Class<?> aClass,Collection<String> fieldNames);
	Field getField(Class<?> aClass,String...fieldNames);
	
	Field getField(Class<?> klass,FieldName fieldName,ValueUsageType valueUsageType);
	
	<T> Collection<T> getSystemIdentifiers(Class<T> identifierClass,Collection<?> objects);
	<T> Collection<T> getBusinessIdentifiers(Class<T> identifierClass,Collection<?> objects);
	
	Object readFieldValue(Object object, Field field,Boolean isGettable);
	Object readFieldValue(Object object, Field field);
	Object readFieldValue(Object object, String fieldName,Boolean isGettable);
	Object readFieldValue(Object object, String fieldName);
	
	FieldHelper writeFieldValue(Object object, Field field,Object value,Boolean isGettable);
	FieldHelper writeFieldValue(Object object, Field field,Object value);
	FieldHelper writeFieldValue(Object object, String fieldName,Object value,Boolean isGettable);
	FieldHelper writeFieldValue(Object object, String fieldName,Object value);
	
	FieldHelper nullify(Object object,Collection<String> fieldsNames,Boolean isEqual);
	FieldHelper nullify(Object object,Strings fieldsNames,Boolean isEqual);
	FieldHelper nullify(Object object,Boolean isEqual,String...fieldsNames);
	
}
