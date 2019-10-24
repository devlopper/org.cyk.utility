package org.cyk.utility.__kernel__.value;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionInstance;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.instance.InstanceHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;
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
	
	@SuppressWarnings("unchecked")
	static <T> T convert(Object value,Class<T> klass)  {
		if(value == null)
			return null;
		if(String.class.equals(klass)) {
			if(value instanceof String)
				return (T) value;
			return (T) value.toString();
		}
		if(ClassHelper.isInstanceOfNumber(klass))
			return NumberHelper.get(klass, value);	
		if(Boolean.class.equals(klass)) {
			if(value instanceof Boolean)
				return (T) value;
			if(value instanceof String)
				return (T) Boolean.valueOf((String)value);
			if(value instanceof Number)
				return (T) NumberHelper.isGreaterThanZero((Number)value);
			
		}
		if(Object.class.equals(klass)) {
			if(value instanceof String)
				return (T) value;
			return (T) value.toString();
		}
		throw new RuntimeException("convert type <<"+value.getClass()+">> to type "+klass+" not yet implemented");
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
	
	static Object convert(Field sourceField,Object sourceFieldValue,Field destinationField) {
		if(sourceField == null || sourceFieldValue == null || destinationField == null)
			return null;
		Type sourceFieldType = FieldHelper.getType(sourceField, null);
		Type destinationFieldType = FieldHelper.getType(destinationField, null);	
		if(sourceFieldValue instanceof Collection) {
			@SuppressWarnings("unchecked")
			Collection<Object> collection = (Collection<Object>) ClassHelper.instanciate((Class<?>) ((ParameterizedType)destinationFieldType).getRawType());
			sourceFieldType = ((ParameterizedType)sourceFieldType).getActualTypeArguments()[0];
			destinationFieldType = ((ParameterizedType)destinationFieldType).getActualTypeArguments()[0];
			for(Object index : ((Collection<?>)sourceFieldValue))
				collection.add(convertScalar(sourceField, (Class<?>)sourceFieldType, index, destinationField, (Class<?>)destinationFieldType));
			return collection;
		}else{
			return convertScalar(sourceField, (Class<?>)sourceFieldType, sourceFieldValue, destinationField, (Class<?>)destinationFieldType);
		}
	}
	
	static Object convertScalar(Field sourceField,Class<?> sourceFieldType,Object sourceFieldValue,Field destinationField,Class<?> destinationFieldType) {
		if(sourceField == null || sourceFieldType == null || sourceFieldValue == null || destinationField == null || destinationFieldType == null)
			return null;	
		//JDK classes : Primitive or char sequence or enumeration
		if(ClassHelper.isBelongsToJavaPackages(sourceFieldType)) {
			if(ClassHelper.isNumberOrCharSequenceOrEnum(sourceFieldType) && ClassHelper.isNumberOrCharSequenceOrEnum(destinationFieldType))
				return convert(sourceFieldValue, destinationFieldType);			
			if(!ClassHelper.isBelongsToJavaPackages(destinationFieldType))
				//value might be a destination type instance business identifier
				return InstanceHelper.getByBusinessIdentifier(destinationFieldType, sourceFieldValue);
		}
		//Non JDK classes : means custom classes
		else {
			if(ClassHelper.isNumberOrCharSequenceOrEnum(destinationFieldType))
				return FieldHelper.readBusinessIdentifier(sourceFieldValue);
			if(!ClassHelper.isBelongsToJavaPackages(destinationFieldType)) {
				if(ClassHelper.isPersistable(destinationFieldType)) {
					//if(destinationField.isAnnotationPresent(javax.persistence.ManyToOne.class)) {
						//Find the object to be linked by its identifier (system and/or business)
						Identifier identifier = FieldHelper.readIdentifier(sourceFieldValue);
						if(identifier!=null)
							return InstanceHelper.getByIdentifier(destinationFieldType, identifier);
					//}
				}				
			}
		}
		return sourceFieldValue;
	}
}
