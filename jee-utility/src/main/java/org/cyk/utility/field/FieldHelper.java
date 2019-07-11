package org.cyk.utility.field;

import java.lang.reflect.Field;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.helper.Helper;
import org.cyk.utility.string.Strings;
import org.cyk.utility.value.ValueUsageType;

public interface FieldHelper extends Helper {

	//Object getFieldValue(Object object,FieldName fieldName);
	
	/**
	 * Join many field paths to build a one field path
	 * Example : f1 + f2.f3 = f1.f2.f3
	 * @param paths to join
	 * @return field path
	 */
	String join(Collection<String> paths);
	/**
	 * {@link #join}
	 * @param paths to join
	 * @return
	 */
	String join(String...paths);
	
	/**
	 * Disjoin many field paths to a collection of field names
	 * @param paths to disjoin
	 * @return
	 */
	Strings disjoin(Collection<String> paths);
	/**
	 * {@link #disjoin}
	 * @param paths to disjoin
	 * @return
	 */
	Strings disjoin(String...paths);
	
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
	Object readFieldValue(Object object, Field field);
	Object readFieldValue(Object object, String fieldName);
}
