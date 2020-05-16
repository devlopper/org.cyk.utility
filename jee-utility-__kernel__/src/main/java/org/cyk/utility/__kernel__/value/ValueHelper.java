package org.cyk.utility.__kernel__.value;

import java.lang.reflect.Field;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionInstance;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;

public interface ValueHelper {

	static Boolean isNull(Object value,Checker checker) {
		if(checker == null)
			checker = Checker.getInstance();
		return checker.isNull(value);
	}
	
	static Boolean isNull(Object value) {
		return isNull(value,Checker.getInstance());
	}
	
	static Boolean isNotNull(Object value,Checker checker) {
		if(value == null)
			return Boolean.FALSE;
		return !isNull(value);
	}
	
	static Boolean isNotNull(Object value) {
		return isNotNull(value, Checker.getInstance());
	}
	
	static Boolean isEmpty(Object value) {
		if(value == null)
			return Boolean.TRUE;
		if(value instanceof String)
			return ((String) value).isEmpty();
		if(value instanceof Collection)
			return((Collection<?>)value).isEmpty();
		if(value instanceof CollectionInstance<?>)
			return ((CollectionInstance<?>)value).isEmpty();
		return Boolean.FALSE;
	}
	
	static Boolean isNotEmpty(Object value) {
		if(value == null)
			return Boolean.FALSE;
		return !isEmpty(value);
	}
	
	static Boolean isBlank(Object value) {
		if(value == null)
			return Boolean.TRUE;
		if(value instanceof String)
			return ((String) value).isBlank();
		return isEmpty(value);
	}

	static Boolean isNotBlank(Object value) {
		if(value == null)
			return Boolean.FALSE;
		return !isBlank(value);
	}
	
	static <T> T defaultToIfNull(Class<T> klass,T value,T defaultValue){
		return value == null ? defaultValue : value;
	}
	
	static <T> T defaultToIfNull(T value,T defaultValue){
		return value == null ? defaultValue : value;
	}
	
	static <T> T defaultToIfBlank(T value,T defaultValue){
		return isBlank(value) ? defaultValue : value;
	}
	
	@SuppressWarnings("unchecked")
	static <FROM, CLASS> CLASS cast(Object object, CLASS aClass) {
		return (CLASS) object;
	}
	
	static <T> T returnOrThrowIfBlank(String name,T value){
		ThrowableHelper.throwIllegalArgumentExceptionIfBlank(name, value);
		return value;
	}
	
	static <T> T throwIfBlank(String name,T value){
		ThrowableHelper.throwIllegalArgumentExceptionIfBlank(name, value);
		return value;
	}
	
	static <T> T convert(Object value,Class<T> klass)  {
		return ValueConverter.getInstance().convert(value, klass);
	}
	
	static Boolean convertToBoolean(Object value)  {
		if(value == null)
			return null;
		return convert(value, Boolean.class);
	}
	
	static Integer convertToInteger(Object value)  {
		if(value == null)
			return null;
		return convert(value, Integer.class);
	}
	
	static Long convertToLong(Object value)  {
		if(value == null)
			return null;
		return convert(value, Long.class);
	}
	
	static Object convert(Field sourceField,Object sourceFieldValue,Field destinationField) {
		if(sourceField == null || sourceFieldValue == null || destinationField == null)
			return null;
		return ValueConverter.getInstance().convert(sourceField, sourceFieldValue, destinationField);
	}
	
	static Object convertScalar(Field sourceField,Class<?> sourceFieldType,Object sourceFieldValue,Field destinationField,Class<?> destinationFieldType) {
		if(sourceField == null || sourceFieldType == null || sourceFieldValue == null || destinationField == null || destinationFieldType == null)
			return null;	
		return ValueConverter.getInstance().convertScalar(sourceField, sourceFieldType, sourceFieldValue, destinationField, destinationFieldType);
	}
}
