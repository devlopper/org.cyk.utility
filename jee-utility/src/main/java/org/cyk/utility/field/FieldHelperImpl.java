package org.cyk.utility.field;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.array.ArrayHelperImpl;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.clazz.ClassHelperImpl;
import org.cyk.utility.clazz.ClassInstancesRuntime;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.collection.CollectionHelperImpl;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.string.Strings;
import org.cyk.utility.value.ValueUsageType;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@ApplicationScoped
public class FieldHelperImpl extends AbstractHelper implements FieldHelper,Serializable {
	private static final long serialVersionUID = -5367150176793830358L;

	private static FieldHelper INSTANCE;
	public static FieldHelper getInstance(Boolean isNew) {
		//if(INSTANCE == null || Boolean.TRUE.equals(isNew))
			INSTANCE =  DependencyInjection.inject(FieldHelper.class);
		return INSTANCE;
	}
	public static FieldHelper getInstance() {
		return getInstance(null);
	}
	
	private static final Map<ClassFieldNameValueUsageType,String> CLASSES_FIELDNAMES_MAP = new HashMap<>();
	private static final Map<Class<?>,Map<String,Field>> CLASSES_FIELDS_MAP = new HashMap<>();
	private static final Map<String,Field> FIELDS_MAP = new HashMap<>();
	
	@Override
	public <TYPE> Class<TYPE> getParameterAt(Field field, Integer index, Class<TYPE> typeClass) {
		//TODO : cache result
		ParameterizedType type = (ParameterizedType) field.getGenericType();
		return (Class<TYPE>) type.getActualTypeArguments()[index];
	}
	
	@Override
	public String join(Collection<String> paths) {
		return __join__(paths);
	}

	@Override
	public String join(String... paths) {
		return __join__(paths);
	}

	@Override
	public Strings disjoin(Collection<String> paths) {
		return __inject__(Strings.class).add(__disjoin__(paths));
	}
	
	@Override
	public Strings disjoin(String... paths) {
		return __inject__(Strings.class).add(__disjoin__(paths));
	}
	
	@Override
	public String buildFieldName(Class<?> klass, FieldName fieldName, ValueUsageType valueUsageType) {
		return __buildFieldName__(klass, fieldName, valueUsageType);
	}
	
	@Override
	public Field getFieldByName(Class<?> klass, String fieldName) {
		return __getFieldByName__(klass, fieldName);
	}
	
	@Override
	public Field getFieldByName(Class<?> klass, FieldName fieldName, ValueUsageType valueUsageType) {
		return __getFieldByName__(klass, fieldName,valueUsageType);
	}
	
	@Override
	public Object getFieldValueSystemIdentifier(Object object) {
		return __readFieldValueSystemIdentifier__(object);
	}

	@Override
	public Object getFieldValueBusinessIdentifier(Object object) {
		return __readFieldValueBusinessIdentifier__(object);
	}

	@Override
	public Object getFieldValueSystemOrBusinessIdentifier(Object object) {
		Object identifier = getFieldValueSystemIdentifier(object);
		if(identifier == null)
			identifier = getFieldValueBusinessIdentifier(object);
		return identifier;
	}
	
	@Override
	public FieldHelper setFieldValueSystemIdentifier(Object object, Object value) {
		__writeFieldValueSystemIdentifier__(object, value);
		return this;
	}
	
	@Override
	public FieldHelper setFieldValueBusinessIdentifier(Object object, Object value) {
		__writeFieldValueBusinessIdentifier__(object, value);
		return this;
	}
	
	@Override
	public FieldHelper copy(Object source, Object destination,Properties properties) {
		FieldValueCopy fieldValueCopy = __inject__(FieldValueCopy.class);
		fieldValueCopy.copyProperty(Properties.CONTEXT, properties);
		fieldValueCopy.copyProperty(Properties.REQUEST, properties);
		Boolean isAutomaticallyDetectFields = null;
		Object fields = properties.getFields();
		if(fields instanceof Collection && __inject__(CollectionHelper.class).isNotEmpty((Collection<?>) fields)) {
			for(String index : ((Collection<String>)fields))
				fieldValueCopy.setFieldName(index);
			isAutomaticallyDetectFields = Boolean.FALSE;
		}else {
			isAutomaticallyDetectFields = Boolean.TRUE;
		}
		fieldValueCopy.setSource(source).setDestination(destination).setIsAutomaticallyDetectFields(isAutomaticallyDetectFields).execute();
		return this;
	}
	
	@Override
	public FieldHelper copy(Object source, Object destination) {
		return copy(source, destination, null);
	}
	
	@Override
	public Field getField(Class<?> aClass, Collection<String> fieldNames) {
		CollectionHelper collectionHelper = __inject__(CollectionHelper.class);
		Field field = null;
		if(aClass == null || collectionHelper.isEmpty(fieldNames)) {
			field = null;
		}else {
			String fieldName = join(fieldNames);
			String key = aClass.getName()+DOT+fieldName;
			field = FIELDS_MAP.get(key);	
			if(field == null) {
				fieldNames = collectionHelper.instanciate(StringUtils.split(fieldName, DOT));
				field = collectionHelper.getFirst(__inject__(FieldsGetter.class).execute(aClass, collectionHelper.getElementAt(fieldNames, 0)).getOutput());
				if(collectionHelper.getSize(fieldNames) == 1) {
				
				}else {
					aClass = __inject__(FieldTypeGetter.class).execute(field).getOutput().getType();
					if(Boolean.TRUE.equals(aClass.isInterface()))
						aClass = __inject__(aClass).getClass();
					field = getField(aClass, collectionHelper.getElementsFrom(fieldNames, 1));
				}	
			}
			FIELDS_MAP.put(key, field);
		}
		
		return field;
	}
	
	@Override
	public Field getField(Class<?> aClass, String... fieldNames) {
		return getField(aClass, __inject__(CollectionHelper.class).instanciate(fieldNames));
	}
	
	@Override
	public Field getField(Class<?> klass, FieldName fieldName, ValueUsageType valueUsageType) {
		String name = __inject__(FieldNameGetter.class).execute(klass, fieldName,valueUsageType).getOutput();
		return __inject__(CollectionHelper.class).getFirst(__inject__(FieldsGetter.class).execute(klass, name).getOutput());
	}
	
	@Override
	public <T> Collection<T> getSystemIdentifiers(Class<T> identifierClass,Collection<?> objects) {
		Collection<T> collection = null;
		if(__inject__(CollectionHelper.class).isNotEmpty(objects)) {
			collection = new ArrayList<T>();
			for(Object index : objects)
				collection.add((T) getFieldValueSystemIdentifier(index));
		}
		return collection;
	}
	
	@Override
	public <T> Collection<T> getBusinessIdentifiers(Class<T> identifierClass,Collection<?> objects) {
		Collection<T> collection = null;
		if(__inject__(CollectionHelper.class).isNotEmpty(objects)) {
			collection = new ArrayList<T>();
			for(Object index : objects)
				collection.add((T) getFieldValueBusinessIdentifier(index));
		}
		return collection;
	}
	
	@Override
	public Object readFieldValue(Object object,Field field,Boolean isGettable) {
		return __readFieldValue__(object, field, isGettable);
	}
	
	@Override
	public Object readFieldValue(Object object,Field field) {
		return __readFieldValue__(object, field);
	}
	
	@Override
	public Object readFieldValue(Object object,String fieldName,Boolean isGettable) {
		return __readFieldValue__(object, fieldName, isGettable);
	}
	
	@Override
	public Object readFieldValue(Object object,String fieldName) {
		return __readFieldValue__(object, fieldName);
	}
	
	@Override
	public FieldHelper writeFieldValue(Object object,Field field, Object value, Boolean isGettable) {
		__writeFieldValue__(object,field, value, isGettable);
		return this;
	}
	
	@Override
	public FieldHelper writeFieldValue(Object object,Field field, Object value) {
		__writeFieldValue__(object,field, value);
		return this;
	}
	
	@Override
	public FieldHelper writeFieldValue(Object object, String fieldName, Object value, Boolean isGettable) {
		__writeFieldValue__(object, fieldName, value, isGettable);
		return this;
	}
	
	@Override
	public FieldHelper writeFieldValue(Object object, String fieldName, Object value) {
		__writeFieldValue__(object, fieldName, value);
		return this;
	}
	
	@Override
	public FieldHelper nullify(Object object, Collection<String> fieldsNames,Boolean isEqual) {
		Collection<String> collection = null;
		if(Boolean.TRUE.equals(isEqual)) {
			collection = fieldsNames;
		}else {
			Fields fields = __inject__(ClassInstancesRuntime.class).get(object.getClass()).getFields();
			if(__inject__(CollectionHelper.class).isNotEmpty(fields)) {
				collection = new ArrayList<>();
				for(Field index : fields.get()) {
					String fieldName = index.getName();
					if(!Modifier.isFinal(index.getModifiers()) && !Modifier.isStatic(index.getModifiers()) && !index.getType().isPrimitive() && !fieldsNames.contains(fieldName))
						collection.add(fieldName);
				}	
			}
		}
		if(__inject__(CollectionHelper.class).isNotEmpty(collection)) {
			for(String index : collection)
				__writeFieldValue__(object, index, null);
		}
		return this;
	}
	
	@Override
	public FieldHelper nullify(Object object, Strings fieldsNames,Boolean isEqual) {
		if(__inject__(CollectionHelper.class).isNotEmpty(fieldsNames)) {
			nullify(object, fieldsNames.get(),isEqual);
		}
		return this;
	}
	
	@Override
	public FieldHelper nullify(Object object,Boolean isEqual, String...fieldsNames) {
		if(__inject__(ArrayHelper.class).isNotEmpty(fieldsNames)) {
			nullify(object, __inject__(CollectionHelper.class).instanciate(fieldsNames),isEqual);
		}
		return this;
	}
	
	/*
	public FieldHelper __set__(Object source,Object destination){
		for(FieldName indexFieldName : new FieldName[] {FieldName.IDENTIFIER})
			for(ValueUsageType indexValueUsageType : new ValueUsageType[] {ValueUsageType.SYSTEM,ValueUsageType.BUSINESS}) {
				Object value = __inject__(FieldValueGetter.class).execute(persistenceEntity, indexFieldName, indexValueUsageType);
				__inject__(FieldValueSetter.class).execute(this, indexFieldName, indexValueUsageType,__stringify__(value));
			}
				
		//__inject__(FieldValueSetter.class).execute(this, FieldName.IDENTIFIER, ValueUsageType.BUSINESS,);
		return this;
	}*/
	
	/**/
	
	/* join */
	
	/**
	 * Join many field paths to build a one field path
	 * Example : f1 + f2.f3 = f1.f2.f3
	 * @param paths to join
	 * @return field path
	 */
	public static String __join__(Collection<String> paths) {
		return StringUtils.join(paths, DOT);
	}

	/**
	 * {@link #join}
	 * @param paths to join
	 * @return
	 */
	public static String __join__(String... paths) {
		return StringUtils.join(paths, DOT);
	}

	/**
	 * Disjoin many field paths to a collection of field names
	 * @param paths to disjoin
	 * @return
	 */
	public static String[] __disjoin__(Collection<String> paths) {
		String path = __join__(paths);
		return StringUtils.split(path,DOT);
	}
	
	/**
	 * {@link #disjoin}
	 * @param paths to disjoin
	 * @return
	 */
	public static String[] __disjoin__(String... paths) {
		return __disjoin__(__inject__(CollectionHelper.class).instanciate(paths));
	}
	
	/* build field name */
	
	public static String __buildFieldName__(Class<?> klass,FieldName fieldName,ValueUsageType valueUsageType) {
		ClassFieldNameValueUsageType classFieldNameValueUsageType = new ClassFieldNameValueUsageType(klass,fieldName,valueUsageType);
		String value = CLASSES_FIELDNAMES_MAP.get(classFieldNameValueUsageType);
		if(value == null)
			__setFieldName__(classFieldNameValueUsageType, value = fieldName.getByValueUsageType(valueUsageType));
		return value;
	}
	
	public static void __setFieldName__(Class<?> klass,FieldName fieldName,ValueUsageType valueUsageType,String name) {
		__setFieldName__(new ClassFieldNameValueUsageType(klass,fieldName,valueUsageType), name);
	}
	
	public static void __setFieldName__(ClassFieldNameValueUsageType classFieldNameValueUsageType,String name) {
		CLASSES_FIELDNAMES_MAP.put(classFieldNameValueUsageType, name);
	}
	
	/* get field */
	
	public static Field __getFieldByNames__(Class<?> klass,Collection<String> fieldNames) {
		Field field = null;
		if(klass == null || CollectionHelperImpl.__isEmpty__(fieldNames)) {
			field = null;
		}else {
			String fieldName = __join__(fieldNames);
			String key = klass.getName()+DOT+fieldName;
			field = FIELDS_MAP.get(key);	
			if(field == null) {
				field = ____getFieldByNames____(klass, fieldNames);
				FIELDS_MAP.put(key, field);
			}
		}		
		return field;
	}
	
	public static Field __getFieldByNames__(Class<?> klass,String...fieldNames) {
		return __getFieldByNames__(klass,CollectionHelperImpl.__instanciate__(fieldNames));
	}
	
	private static Field ____getFieldByNames____(Class<?> klass,Collection<String> fieldNames) {
		Field field = null;
		if(klass == null || CollectionHelperImpl.__isEmpty__(fieldNames)) {
			field = null;
		}else {
			String fieldName = __join__(fieldNames);
			if(field == null) {
				fieldNames = CollectionHelperImpl.__instanciate__(__disjoin__(fieldName));
				field = __getFieldByName__(klass, CollectionHelperImpl.__getFirst__(fieldNames));
				if(CollectionHelperImpl.__getSize__(fieldNames) == 1) {
				
				}else {
					klass = __getFieldType__(klass, field).getType();
					if(Boolean.TRUE.equals(klass.isInterface()))
						klass = __inject__(klass).getClass();
					field = ____getFieldByNames____(klass, CollectionHelperImpl.__getElementsFrom__(fieldNames, 1));
				}	
			}
		}		
		return field;
	}
	
	public static Field __getFieldByName__(Class<?> klass,String fieldName) {
		if(klass == null || fieldName == null)
			return null;
		Field field = null;
		Map<String,Field> map = CLASSES_FIELDS_MAP.get(klass);
		if(map == null)
			CLASSES_FIELDS_MAP.put(klass, map = new HashMap<>());
		if((field = map.get(fieldName)) == null)
			map.put(fieldName, field = FieldUtils.getField(klass, fieldName, Boolean.TRUE));
		return field;
	}
	
	public static Field __getFieldByName__(Class<?> klass,FieldName fieldName,ValueUsageType valueUsageType) {
		return klass == null ? null : __getFieldByName__(klass, __buildFieldName__(klass, fieldName, valueUsageType));
	}
	
	/* read field value*/
	
	public static Object __readFieldValue__(Object object,Field field,Boolean isGettable) {
		try {
			return object == null ? null : FieldUtils.readField(field,object, Boolean.TRUE);
		} catch (IllegalAccessException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public static Object __readFieldValue__(Object object,Field field) {
		return __readFieldValue__(object, field, null);
	}
	
	public static Object __readFieldValue__(Object object,String fieldName,Boolean isGettable) {
		if(object == null)
			return null;
		if(StringUtils.contains(fieldName, DOT)) {
			try {
				return PropertyUtils.getNestedProperty(object, fieldName);
			} catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}else
			try {
				return object == null ? null : FieldUtils.readField(object, fieldName, Boolean.TRUE);
			} catch (IllegalAccessException exception) {
				throw new RuntimeException(exception);
			}
	}

	public static Object __readFieldValue__(Object object,String fieldName) {
		return __readFieldValue__(object, fieldName, null);
	}
	
	public static Object __readFieldValue__(Object object,Collection<String> fieldNames,Boolean isGettable) {
		return __readFieldValue__(object, __join__(fieldNames), isGettable);
	}
	
	public static Object __readFieldValue__(Object object,Collection<String> fieldNames) {
		return __readFieldValue__(object, __join__(fieldNames));
	}
	
	public static Object __readFieldValue__(Object object,String[] fieldNames,Boolean isGettable) {
		return __readFieldValue__(object, __join__(fieldNames), isGettable);
	}
	
	public static Object __readFieldValue__(Object object,String[] fieldNames) {
		return __readFieldValue__(object, __join__(fieldNames));
	}
	
	public static Object __readFieldValue__(Object object,FieldName fieldName,ValueUsageType valueUsageType,Boolean isGettable) {
		return object == null ? null : __readFieldValue__(object, __getFieldByName__(object.getClass(), fieldName, valueUsageType), isGettable);
	}
	
	public static Object __readFieldValue__(Object object,FieldName fieldName,ValueUsageType valueUsageType) {
		return object == null ? null : __readFieldValue__(object, __getFieldByName__(object.getClass(), fieldName, valueUsageType));
	}
	
	public static Object __readFieldValueSystemIdentifier__(Object object,Boolean isGettable) {
		return __readFieldValue__(object, FieldName.IDENTIFIER, ValueUsageType.SYSTEM, isGettable);
	}
	
	public static Object __readFieldValueSystemIdentifier__(Object object) {
		return __readFieldValue__(object, FieldName.IDENTIFIER, ValueUsageType.SYSTEM);
	}
	
	public static Object __readFieldValueBusinessIdentifier__(Object object,Boolean isGettable) {
		return __readFieldValue__(object, FieldName.IDENTIFIER, ValueUsageType.BUSINESS, isGettable);
	}
	
	public static Object __readFieldValueBusinessIdentifier__(Object object) {
		return __readFieldValue__(object, FieldName.IDENTIFIER, ValueUsageType.BUSINESS);
	}
	
	/* write field value*/
	
	public static void __writeFieldValue__(Object object,Field field, Object value, Boolean isGettable) {
		if(object == null)
			return;
		try {
			FieldUtils.writeField(field,object,value, Boolean.TRUE);
		} catch (IllegalAccessException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public static void __writeFieldValue__(Object object,Field field, Object value) {
		__writeFieldValue__(object,field, value, null);
	}
	
	public static void __writeFieldValue__(Object object, String fieldName, Object value, Boolean isGettable) {
		if(object == null)
			return;
		if(StringUtils.contains(fieldName, DOT)) {
			try {
				PropertyUtils.setNestedProperty(object, fieldName, value);
			} catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}else
			try {
				FieldUtils.writeField(object,fieldName,value, Boolean.TRUE);
			} catch (Exception exception) {
				throw new RuntimeException(exception);
			}
	}
	
	public static void __writeFieldValue__(Object object, String fieldName, Object value) {
		__writeFieldValue__(object, fieldName, value, null);
	}
	
	public static void __writeFieldValue__(Object object, Collection<String> fieldNames, Object value, Boolean isGettable) {
		__writeFieldValue__(object, __join__(fieldNames), value, isGettable);
	}
	
	public static void __writeFieldValue__(Object object, Collection<String> fieldNames, Object value) {
		__writeFieldValue__(object, __join__(fieldNames), value);
	}
	
	public static void __writeFieldValue__(Object object, String[] fieldNames, Object value, Boolean isGettable) {
		__writeFieldValue__(object, __join__(fieldNames), value, isGettable);
	}
	
	public static void __writeFieldValue__(Object object, String[] fieldNames, Object value) {
		__writeFieldValue__(object, __join__(fieldNames), value);
	}
	
	public static void __writeFieldValue__(Object object, FieldName fieldName,ValueUsageType valueUsageType, Object value, Boolean isGettable) {
		if(object != null)
			__writeFieldValue__(object, __buildFieldName__(object.getClass(), fieldName, valueUsageType), value, isGettable);
	}
	
	public static void __writeFieldValueSystemIdentifier__(Object object, Object value, Boolean isGettable) {
		if(object != null)
			__writeFieldValue__(object, __buildFieldName__(object.getClass(), FieldName.IDENTIFIER, ValueUsageType.SYSTEM), value, isGettable);
	}
	
	public static void __writeFieldValueSystemIdentifier__(Object object, Object value) {
		if(object != null)
			__writeFieldValue__(object, __buildFieldName__(object.getClass(), FieldName.IDENTIFIER, ValueUsageType.SYSTEM), value);
	}
	
	public static void __writeFieldValueBusinessIdentifier__(Object object, Object value, Boolean isGettable) {
		if(object != null)
			__writeFieldValue__(object, __buildFieldName__(object.getClass(), FieldName.IDENTIFIER, ValueUsageType.BUSINESS), value, isGettable);
	}
	
	public static void __writeFieldValueBusinessIdentifier__(Object object, Object value) {
		if(object != null)
			__writeFieldValue__(object, __buildFieldName__(object.getClass(), FieldName.IDENTIFIER, ValueUsageType.BUSINESS), value);
	}
	
	/* get field type*/
	
	public static FieldType __getFieldType__(Class<?> klass,Field field) {
		FieldType fieldType = null;
		if(field!=null){
			if(klass == null)
				klass = field.getDeclaringClass();
			fieldType = __inject__(FieldType.class).setField(field);
			if(field.getType().equals(field.getGenericType())) {
				fieldType.setType(field.getType());
			}else {
				if(field.getGenericType() instanceof ParameterizedType) {
					ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
					if(ArrayHelperImpl.__isEmpty__(parameterizedType.getActualTypeArguments())) {
							
					}else {
						fieldType.setType(field.getType());
						for(Integer index = 0 ; index < parameterizedType.getActualTypeArguments().length ; index = index + 1) {
							Type type = parameterizedType.getActualTypeArguments()[index];
							Class<?> argumentClass = null;
							if(type instanceof TypeVariable) {
								argumentClass = ClassHelperImpl.__getParameterAt__(klass, index, Object.class);
							}else
								argumentClass = (Class<?>) type;
							fieldType.getParameterizedClasses(Boolean.TRUE).set(index,argumentClass);
						}
					}
				}else {
					fieldType.setType(__inject__(ClassHelper.class).getParameterAt(klass, 0, Object.class));
				}
			}
			if(fieldType.getType() == null)
				fieldType.setType(Object.class);
		}
		return fieldType;
	}
	
	/**/
	
	private static final String DOT = ConstantCharacter.DOT.toString();
	
	/**/
	
	@Getter @Setter @Accessors(chain=true) @EqualsAndHashCode(of= {"klass","fieldName","valueUsageType"}) @AllArgsConstructor
	private static class ClassFieldNameValueUsageType implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Class<?> klass;
		private FieldName fieldName;
		private ValueUsageType valueUsageType;
		
	}
}
