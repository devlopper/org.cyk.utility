package org.cyk.utility.__kernel__.value;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Transient;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.instance.InstanceHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.mapping.MapperGetter;
import org.cyk.utility.__kernel__.number.NumberHelper;

public interface ValueConverter {

	default <T> T convert(Object value,Class<T> klass) {
		if(value == null)
			return null;
		if(value.getClass().equals(klass))
			return (T) value;
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
		
		if(Date.class.equals(klass)) {
			/*if(value instanceof LocalDate)
				return (T) Date.from( ((LocalDate)value).toInstant(ZoneOffset.UTC));
			else if(value instanceof LocalTime)
				return (T) Date.from( ((LocalTime)value).toInstant(ZoneOffset.UTC));
			else */if(value instanceof LocalDateTime)
				return (T) Date.from( ((LocalDateTime)value).toInstant(ZoneOffset.UTC));
		}
		
		if(LocalDate.class.equals(klass)) {
			if(value instanceof Date) {
				return (T) LocalDate.ofInstant(((Date)value).toInstant(),ZoneId.systemDefault());
			}
		}
		
		if(LocalTime.class.equals(klass)) {
			if(value instanceof Date) {
				return (T) LocalTime.ofInstant(((Date)value).toInstant(),ZoneId.systemDefault());
			}
		}
		
		if(LocalDateTime.class.equals(klass)) {
			if(value instanceof Date) {
				return (T) LocalDateTime.ofInstant(((Date)value).toInstant(),ZoneId.systemDefault());
			}
		}
		
		if(Object.class.equals(klass)) {
			if(value instanceof String)
				return (T) value;
			return (T) value.toString();
		}
		
		if(value instanceof org.cyk.utility.__kernel__.object.__static__.representation.AbstractObjectImpl) {
			if(ClassHelper.isInstanceOf(klass, org.cyk.utility.__kernel__.object.__static__.persistence.embeddedable.AbstractObjectImpl.class)) {
				return MapperGetter.getInstance().getBySourceByDestinationClass(value, klass).getDestination(value);
			}
		}
		
		throw new RuntimeException("convert type <<"+value.getClass()+">> to type "+klass+" not yet implemented");
	}
	
	default Boolean convertToBoolean(Object value)  {
		if(value == null)
			return null;
		return convert(value, Boolean.class);
	}
	
	default Integer convertToInteger(Object value)  {
		if(value == null)
			return null;
		return convert(value, Integer.class);
	}
	
	default Object convert(Field sourceField,Object sourceFieldValue,Field destinationField) {
		if(sourceField == null || sourceFieldValue == null || destinationField == null)
			return null;
		Type sourceFieldType = FieldHelper.getType(sourceField, null);
		Type destinationFieldType = FieldHelper.getType(destinationField, null);	
		if(sourceFieldValue instanceof Collection) {
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
	
	default Object convertScalar(Field sourceField,Class<?> sourceFieldType,Object sourceFieldValue,Field destinationField,Class<?> destinationFieldType) {
		if(sourceField == null || sourceFieldType == null || sourceFieldValue == null || destinationField == null || destinationFieldType == null)
			return null;	
		//JDK classes : Primitive or char sequence or enumeration
		if(ClassHelper.isBelongsToJavaPackages(sourceFieldType)) {
			//if(ClassHelper.isNumberOrCharSequenceOrEnum(sourceFieldType) && ClassHelper.isNumberOrCharSequenceOrEnum(destinationFieldType))
			if(ClassHelper.isBelongsToJavaPackages(destinationFieldType))	
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
						if(identifier!=null) {
							Object object = InstanceHelper.getByIdentifier(destinationFieldType, identifier);
							//copy transients fields
							Collection<Field> fields = FieldHelper.getByAnnotationClass(destinationFieldType, Transient.class);
							if(CollectionHelper.isNotEmpty(fields)) {
								for(Field field : fields) {
									Field transientSourceField = FieldHelper.getByName(sourceFieldType, field.getName());
									if(transientSourceField == null)
										continue;
									Object transientSourceFieldValue = FieldHelper.read(sourceFieldValue, transientSourceField);
									if(transientSourceFieldValue == null)
										continue;
									FieldHelper.copy(sourceFieldValue, transientSourceField, transientSourceFieldValue, object, field);
								}
							}
							return object;
						}
					//}
				}else if(ClassHelper.isInstanceOf(destinationFieldType, org.cyk.utility.__kernel__.object.__static__.persistence.embeddedable.AbstractObjectImpl.class)) {
					return convert(sourceFieldValue, destinationFieldType);
				}
			}
		}
		return sourceFieldValue;
	}
	
	/**/
	
	static ValueConverter getInstance() {
		return Helper.getInstance(ValueConverter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}