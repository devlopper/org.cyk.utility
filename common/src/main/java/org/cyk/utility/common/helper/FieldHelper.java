package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Singleton;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.utility.common.ClassRepository;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.FieldOverrides;
import org.cyk.utility.common.helper.StringHelper.Location;
import org.cyk.utility.common.property.FluentPropertyBeanIntrospector;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Singleton
public class FieldHelper extends AbstractReflectionHelper<java.lang.reflect.Field> implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String FIELD_NAME_SEPARATOR = Constant.CHARACTER_DOT.toString();
	
	static {
		/*
		 * Take class from cyk model packages
		 */
		
		
		//DefaultBeanIntrospector.INSTANCE = null;
		PropertyUtils.addBeanIntrospector(new FluentPropertyBeanIntrospector().setTargetClassNameIncludedRegularExpression("(^org.cyk.)(.+)(.model.)"));
	}
	
	private static FieldHelper INSTANCE;
	
	public static FieldHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new FieldHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public String buildPath(Collection<String> fieldNames){
		StringBuilder stringBuilder = new StringBuilder();
		if(CollectionHelper.getInstance().isNotEmpty(fieldNames)){
			java.util.Collection<String> strings = new ArrayList<String>();
			for(String index : fieldNames)
				if(StringHelper.getInstance().isNotBlank(index))
					strings.add(index);
			stringBuilder.append(StringUtils.join(strings,FIELD_NAME_SEPARATOR));
		}
		return stringBuilder.toString();
	}
	
	public String buildPath(String...fieldNames){
		if(ArrayHelper.getInstance().isNotEmpty(fieldNames))
			return buildPath(Arrays.asList(fieldNames));
		return null;
	}
	
	public Boolean getIsContainSeparator(String fieldName){
		return StringUtils.contains(fieldName,FIELD_NAME_SEPARATOR);
	}
	
	public String getLast(String fieldName){
		return StringUtils.contains(fieldName, FIELD_NAME_SEPARATOR) ? StringUtils.substringAfterLast(fieldName, FIELD_NAME_SEPARATOR) : fieldName;
	}
	
	public String getAfterFirst(String fieldName){
		return StringUtils.contains(fieldName, FIELD_NAME_SEPARATOR) ? StringUtils.substringAfter(fieldName, FIELD_NAME_SEPARATOR) : fieldName;
	}
	
	public java.lang.reflect.Field getLast(Class<?> aClass,String fieldName){
		return get(aClass,getLast(fieldName));
	}
	
	public String getBeforeLast(String fieldName){
		String result = StringUtils.contains(fieldName, FIELD_NAME_SEPARATOR) ? StringUtils.substringBeforeLast(fieldName, FIELD_NAME_SEPARATOR) : fieldName;
		logTrace("get field name before last , field name={} , result={}",fieldName,result);
		return result;
	}
	
	public List<String> getFieldNames(String...fieldPaths){
		return Arrays.asList(StringUtils.split(buildPath(fieldPaths), FIELD_NAME_SEPARATOR));
	}
	
	public String getVariableNameFromNames(java.util.Collection<String> names){
		final StringBuilder stringBuilder = new StringBuilder();
		new CollectionHelper.Iterator.Adapter.Default<String>(names){
			private static final long serialVersionUID = 1L;
			@Override
			protected void __executeForEach__(String name) {
				for(String string : getFieldNames(name))
					stringBuilder.append(StringHelper.getInstance().applyCaseType(string, stringBuilder.length() == 0 ? StringHelper.CaseType.NONE : StringHelper.CaseType.FU));
			}
		}.execute();
		return stringBuilder.toString();
	}
	
	public String getVariableNameFromNames(String...names){
		if(ArrayHelper.getInstance().isNotEmpty(names))
			return getVariableNameFromNames(Arrays.asList(names));
		return null;
	}
	
	public List<String> getNames(Collection<java.lang.reflect.Field> fields){
		if(CollectionHelper.getInstance().isEmpty(fields))
			return null;
		return new ArrayList<String>(MethodHelper.getInstance().callGet(fields, String.class, Constant.WORD_NAME));
	}
	
	public List<String> getNamesByTypes(Class<?> aClass,Collection<Class<?>> classes){
		if(CollectionHelper.getInstance().isEmpty(classes))
			return null;
		return getNames(getByTypes(aClass, classes));
	}
	
	public List<String> getNamesByTypes(Class<?> aClass,Class<?>...classes){
		if(ArrayHelper.getInstance().isEmpty(classes))
			return null;
		return getNamesByTypes(aClass, Arrays.asList(classes));
	}
	
	public Object read(Object instance,java.lang.reflect.Field field){
		return read(instance,field.getName());
	}
	
	public Object read(Object instance,String...fieldNames){
		try {
			return PropertyUtils.getProperty(instance, buildPath(fieldNames));
		} catch (Exception e) {
			logThrowable(e);
			return null;
		}
	}
	
	public Object readBeforeLast(Object instance,String fieldName){
		LoggingHelper.Message.Builder loggingMessageBuilder = new LoggingHelper.Message.Builder.Adapter.Default();
		loggingMessageBuilder.addManyParameters("read before last");
		if(instance!=null)
			loggingMessageBuilder.addNamedParameters("instance class",instance.getClass());
		loggingMessageBuilder.addNamedParameters("field name",fieldName);
		Object result;
		if(instance==null)
			result = null;
		else {
			String beforeLastFieldName = getBeforeLast(fieldName);
			loggingMessageBuilder.addNamedParameters("before last field name",beforeLastFieldName);
			if(fieldName.equals(beforeLastFieldName))
				result = instance;
			else
				result = read(instance, beforeLastFieldName);
		}
		loggingMessageBuilder.addNamedParameters("result",result);
		logTrace(loggingMessageBuilder);
		return result;
	}
	
	public Object readStatic(java.lang.reflect.Field field){
		try {
			return FieldUtils.readStaticField(field, Boolean.TRUE);
		} catch (IllegalAccessException e) {
			logThrowable(e);
			return null;
		}
	}
	
	public <T> T read(Object instance,Class<T> fieldValueClass,Boolean instanciateIfValueIsNull,String...fieldNames){
		String fieldName = buildPath(fieldNames);
		//Class<?> fieldValueClass = getType(instance.getClass(), fieldName);
		@SuppressWarnings("unchecked")
		T fieldValue = (T) FieldHelper.getInstance().read(instance, fieldName);
		if(fieldValue == null && Boolean.TRUE.equals(instanciateIfValueIsNull))
			FieldHelper.getInstance().set(instance, fieldValue = ClassHelper.getInstance().instanciateOne(fieldValueClass), fieldName);
		return fieldValue;
	}
	
	public <T> T read(Object instance,Boolean instanciateIfValueIsNull,String...fieldNames){
		String fieldName = buildPath(fieldNames);
		String fieldValueClassFieldName = getLast(fieldName)+"Class";
		java.lang.reflect.Field field = get(instance.getClass(), fieldValueClassFieldName);
		@SuppressWarnings("unchecked")
		Class<T> fieldValueClass = (Class<T>) (field == null ? getType(instance.getClass(), fieldName) : read(instance, field));
		return read(instance, fieldValueClass, instanciateIfValueIsNull, fieldName);
	}
	
	public void set(Object object,String...fieldNames){
		LoggingHelper.Message.Builder loggingMessageBuilder = new LoggingHelper.Message.Builder.Adapter.Default();
		loggingMessageBuilder.addManyParameters("set instance field value by instanciating where null");
		for(String p : getFieldNames(fieldNames)){
			Object pValue = read(object, p);
			if(pValue==null){
				java.lang.reflect.Field field = ClassRepository.getInstance().getField(object.getClass(), p);
				try {
					FieldUtils.writeField(object, p, pValue = field.getType().newInstance(), Boolean.TRUE);
				} catch (Exception e) {
					new RuntimeException(e);
				}
				loggingMessageBuilder.addManyParameters(p);
			}
			object = pValue;
		}
		logTrace(loggingMessageBuilder);
	}
	
	/**
	 * Set an instance field value
	 * @param instance
	 * @param value
	 * @param fieldNames
	 */
	public void set(Object instance,Object value,String...fieldNames){
		String path = buildPath(fieldNames);
		set(instance, path);//we want to be sure we can reach the latest field
		try {
	        //org.apache.commons.beanutils.BeanUtils.setProperty( instance, path, value );
			PropertyUtils.setProperty(instance, path, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			logTrace("set instance field value. class = {} , field = {} , value = {}", instance.getClass(),path,value);
		}
	}
	
	public void set(Object instance,Object value,java.lang.reflect.Field field){
		set(instance,value,field.getName());
	}
	
	/**
	 * Set a class static field value
	 * @param aClass
	 * @param fieldName
	 * @param value
	 */
	public void set(Class<?> aClass,String fieldName,Object value){
		try {
			FieldUtils.writeStaticField(aClass, fieldName, value, Boolean.TRUE);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void copy(Object source,Object destination,Boolean overwrite,String...fieldNames){
		copy(source, destination, Arrays.asList(fieldNames),overwrite);
	}
	
	public void copy(Object source,Object destination,String...fieldNames){
		copy(source, destination, Boolean.TRUE,fieldNames);
	}
	
	public void copy(Object source,Object destination,Collection<String> fieldNames,Boolean overwrite){
		for(String fieldName : fieldNames){
			Object value = read(source, fieldName);
			Object currentDestinationValue = read(destination, fieldName);
			if(currentDestinationValue == null || Boolean.TRUE.equals(overwrite))
				set(destination,value, fieldName);
		}
	}
	
	public void copy(Object source,Object destination,Collection<String> fieldNames){
		copy(source,destination,fieldNames,Boolean.TRUE);
	}
	/**/
	
	public java.lang.reflect.Field getByValue(Object source,Object value){
		for(java.lang.reflect.Field field : get(source.getClass()))
			try {
				if(value==FieldUtils.readField(field, source, true))
					return field;
			} catch (Exception e) {
				logTrace("Field named {} cannot be read from object {}",field.getName(),source);
				logThrowable(e);
				return null;
			}
		return null;
	}
	
	private java.util.Collection<java.lang.reflect.Field> __getAllFields__(Collection<java.lang.reflect.Field> fields,Class<?> type,String token,Location location,Boolean recursive) {
		//super class fields first
		/*if(recursive==null || Boolean.TRUE.equals(recursive)){
			if (type.getSuperclass() != null) {
				fields = __getAllFields__(fields, type.getSuperclass(),token,location,recursive);
			}
		}
		//declared class fields second
		for (Field field : type.getDeclaredFields()) {
			if(StringHelper.getInstance().isAtLocation(field.getName(), token, location))
				fields.add(field);
		}
		*/
		return new Get.Adapter.Default(type).setToken(token).setTokenLocation(location).setRecursivable(recursive).execute();
		
		//return fields;
	}
	
	public java.util.Collection<java.lang.reflect.Field> get(Class<?> type,String name,Location location,Boolean recursive) {
		Collection<java.lang.reflect.Field> fields = new ArrayList<>();
		if(Boolean.TRUE.equals(ClassRepository.ENABLED)){
			for(java.lang.reflect.Field field : ClassRepository.getInstance().get(type).getFields())
				if(StringHelper.getInstance().isAtLocation(field.getName(), name, location))
					if(recursive==null || Boolean.TRUE.equals(recursive) || field.getDeclaringClass().equals(type))
						fields.add(field);
		}else{
			__getAllFields__(fields, type,name,location,recursive);
		}
		return fields;
	}
	
	public java.util.Collection<java.lang.reflect.Field> get(Class<?> type,String name,Location location) {
		return get(type,name,location,Boolean.TRUE);
	}
	
	public java.util.Collection<java.lang.reflect.Field> get(Class<?> type) {
		return get(type,null,null);
	}
	
	public java.util.Collection<java.lang.reflect.Field> get(Class<?> type,Boolean recursive) {
		return get(type,null,null,recursive);
	}
	
	public java.lang.reflect.Field get(Class<?> type,String name) {
		if(StringHelper.getInstance().isNotBlank(name)){
			String fieldName = StringUtils.substringBefore(name, FIELD_NAME_SEPARATOR);
			for(java.lang.reflect.Field field : get(type))
				if(StringHelper.getInstance().isAtLocation(field.getName(), fieldName, Location.EXAT))
					return fieldName.equals(name) ? field : get(field.getType(),StringUtils.substringAfter(name, FIELD_NAME_SEPARATOR));	
		}
		return null;
	}
	
	public java.lang.reflect.Field getByFirstNotNull(Class<?> type,String...names) {
		java.lang.reflect.Field field = null;
		for(String index : names){
			field = FieldHelper.getInstance().get(type,index);
			if(field != null)
				return field;
		}
		return null;
	}
	
	public java.util.Collection<java.lang.reflect.Field> get(Class<?> type,Collection<Class<? extends Annotation>> annotationClasses) {
		Collection<java.lang.reflect.Field> fields = new ArrayList<>();
		for(java.lang.reflect.Field field : get(type))
			if(annotationClasses==null || annotationClasses.isEmpty())
				fields.add(field);
			else
				for(Class<? extends Annotation> annotationClass : annotationClasses)
					if(field.isAnnotationPresent(annotationClass)){
						fields.add(field);
						break;
					}
		return fields;
	}
	
	public java.util.Collection<java.lang.reflect.Field> get(Class<?> type,Class<? extends Annotation> annotationClass) {
		Collection<Class<? extends Annotation>> collection = new ArrayList<>();
		collection.add(annotationClass);
		return get(type,collection);
	}
	
	public java.util.Collection<java.lang.reflect.Field> getByTypes(Class<?> type,Collection<Class<?>> classes) {
		Collection<java.lang.reflect.Field> fields = new ArrayList<>();
		for(java.lang.reflect.Field field : get(type))
			if(CollectionHelper.getInstance().isNotEmpty(classes))
				if(classes.contains(getType(type, field)))
					fields.add(field);
		return fields;
	}
	
	public java.util.Collection<java.lang.reflect.Field> getByTypes(Class<?> type,Class<?>...classes) {
		if(ArrayHelper.getInstance().isEmpty(classes))
			return null;
		return getByTypes(type, Arrays.asList(classes));
	}
	
	public java.util.Collection<java.lang.reflect.Field> getByTypeIdentified(Class<?> type) {
		Collection<java.lang.reflect.Field> fields = new ArrayList<>();
		for(java.lang.reflect.Field field : get(type)){
			if(ClassHelper.getInstance().isIdentified(getType(type, field)))
				fields.add(field);
		}
		return fields;
	}
	
	public Object readField(Object object,java.lang.reflect.Field field,Boolean recursive,Boolean createIfNull,Boolean autoSet,Collection<Class<? extends Annotation>> annotationClasses){
		Object r = __readField__(object,field,recursive,annotationClasses);
		try {
			if(r==null && Boolean.TRUE.equals(createIfNull)){
				if(field.getType().equals(Collection.class) || field.getType().equals(List.class))
					r = new ArrayList<>();
				else if(field.getType().equals(Set.class))
					r = new LinkedHashSet<>();
				else
					r = field.getType().newInstance();
				if(Boolean.TRUE.equals(autoSet))
					writeField(field, object, r);
			}
		} catch (Exception e) {
			logThrowable(e);
		}	
		return r;
	}
	
	public Object readField(Object object,java.lang.reflect.Field field,Boolean recursive,Boolean createIfNull,Collection<Class<? extends Annotation>> annotationClasses){
		return readField(object, field,recursive, createIfNull, Boolean.FALSE,annotationClasses);
	}
	
	public Object readField(Object object,java.lang.reflect.Field field,Boolean createIfNull,Boolean autoSet){
		return readField(object, field, Boolean.FALSE, createIfNull,autoSet,null);
	}
	
	public Object readField(Object object,java.lang.reflect.Field field,Boolean createIfNull){
		return readField(object, field, Boolean.FALSE, createIfNull,null);
	}
	
	private Object __readField__(Object object,java.lang.reflect.Field field,Boolean recursive,Collection<Class<? extends Annotation>> annotationClasses){
		Object value = null;
		
		if(object==null)
			;
		else
			try {
				if(Boolean.TRUE.equals(recursive)){
					Collection<java.lang.reflect.Field> fields = get(object.getClass(),annotationClasses);
					if(fields.contains(field))
						value = FieldUtils.readField(field, object,Boolean.TRUE);
					else{
						for(java.lang.reflect.Field f : fields)
							//if(!f.getType().isPrimitive() && !f.getType().getName().startsWith("java.")){
							if(f.getType().getName().startsWith("org.cyk.")){
								value = __readField__(FieldUtils.readField(f, object,Boolean.TRUE),field,recursive,annotationClasses);
								if(value!=null)
									break;
							}
						/*if(value==null){
							System.out.println(field.getName()+" not in "+object.getClass().getSimpleName());
						}else
							System.out.println(field.getName()+"  in "+object.getClass().getSimpleName());*/
					}
				}else{
					value = FieldUtils.readField(field, object, Boolean.TRUE);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return null;
			}	
		
		return value;
	}
	
	public void writeField(java.lang.reflect.Field field, Object target, Object value){
		try {
			FieldUtils.writeField(field, target, value,Boolean.TRUE);
		} catch (IllegalAccessException e) {
			logThrowable(e);
		}
	}

	/**/
	
	public FieldOverride getOverride(Class<?> aClass, String fieldName) {
		FieldOverride fieldOverride = null;
		FieldOverrides fieldOverrides = aClass.getAnnotation(FieldOverrides.class);
		if(fieldOverrides==null){
			
		}else{
			for(FieldOverride index : fieldOverrides.value())
				if( fieldName.equals(index.name()) ){
					fieldOverride = index;
					break;
				}
		}
		
		if(fieldOverride==null)
			for(Annotation annotation : aClass.getAnnotations())
				if(annotation instanceof FieldOverride && ((FieldOverride)annotation).name().equals(fieldName)){
					fieldOverride = (FieldOverride) annotation;
					break;
				}
		
		if(fieldOverride == null)
			if(!aClass.getSuperclass().equals(Object.class))
				return getOverride(aClass.getSuperclass(),fieldName);
		return fieldOverride;
	}
	
	public Class<?> getType(Class<?> aClass,java.lang.reflect.Field field){
		FieldOverride fieldOverride = getOverride(aClass,field.getName());
		if(fieldOverride==null)
			return field.getType();
		return fieldOverride.type();
	}
	
	public Class<?> getType(Class<?> aClass,String name){
		return getType(aClass, get(aClass, name));
	}
	
	public Object getFieldValueContainer(Object object,java.lang.reflect.Field field){
		if(object==null)
			return null;
		logTrace("Class={} , Field={}",object.getClass(),field);
		/*System.out.println("Declaring Class -> "+field.getDeclaringClass());
		System.out.println("Object Class -> "+object.getClass());
		System.out.println("Class -> "+field.getDeclaringClass().equals(object.getClass()));
		System.out.println("Fields -> "+getAllFields(object.getClass()));
		System.out.println("Field -> "+field);
		System.out.println("Contains : "+ArrayUtils.contains(new ArrayList<>(getAllFields(object.getClass())).toArray(), field));
		System.out.println("Object : "+object);
		*/
		if(/*field.getDeclaringClass().equals(object.getClass()) &&*/ ArrayUtils.contains(new ArrayList<>(get(object.getClass())).toArray(), field)){
			return object;
		}
		for(java.lang.reflect.Field f : object.getClass().getDeclaredFields()){
			try {
				Object value = FieldUtils.readField(f, object, Boolean.TRUE);
				logTrace("Field={} , Value={}",f,value);
				if(f.equals(field)){
					logTrace("Field found in {}",object.getClass());
					return object;
				}
				if(value!=null && !value.getClass().getName().startsWith("java."))
					return getFieldValueContainer(value, field);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public java.util.Collection<String> getNamesWhereReferencedByStaticField(Class<?> aClass,Boolean recursive){
		Collection<java.lang.reflect.Field> fields = FieldHelper.getInstance().get(aClass, Constant.FIELD_, Location.START,recursive);
		Collection<String> names = new ArrayList<>();
		for(java.lang.reflect.Field field : fields)
			if(Modifier.isStatic(field.getModifiers()))
				try {
					names.add((String)field.get(null));
				} catch (Exception e) {
					e.printStackTrace();
				}
		return StringHelper.getInstance().removeBlank(names);
	}
	
	public java.util.Collection<String> getNamesWhereReferencedByStaticField(Class<?> aClass){
		return getNamesWhereReferencedByStaticField(aClass,Boolean.TRUE);
	}
	
	public Class<?> getParameterType(java.lang.reflect.Field field,Integer index){
        ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
        return (Class<?>) parameterizedType.getActualTypeArguments()[index];
	}
	
	public Class<?> getParameterType(Class<?> aClass,String name,Integer index){
		return getParameterType(get(aClass, name), index);
	}
	
	/**/
	
	//private static final String METHOD_GET_NAME_SUFFIX = "name";
	
	/**/
	
	public static interface Get extends AbstractReflectionHelper.Get<Class<?>, java.lang.reflect.Field> {
		 
		@Getter @Setter @Accessors(chain=true)
		public static class Adapter extends AbstractReflectionHelper.Get.Adapter.Default<Class<?>, java.lang.reflect.Field> implements Get,Serializable {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			public Adapter(Class<?> input) {
				super(input);
				setInputClass((Class<Class<?>>) ClassHelper.getInstance().getByName(Class.class)); 
				setOutputClass((Class<Collection<java.lang.reflect.Field>>) ClassHelper.getInstance().getByName(Class.class)); 
			}
			
			/**/
			
			public static class Default extends Get.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<?> input) {
					super(input);
				}
				
				@Override
				public Integer getModifiers(java.lang.reflect.Field field) {
					return field.getModifiers();
				}
				
				@Override
				public String getName(java.lang.reflect.Field field) {
					return field.getName();
				}
				
				@Override
				protected Class<?> getParent(Class<?> clazz) {
					return clazz.getSuperclass();
				}
				
				@Override
				protected java.util.Collection<java.lang.reflect.Field> getTypes(Class<?> clazz) {
					return Arrays.asList(clazz.getDeclaredFields());
				}
				
			}
		}
		
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true) @EqualsAndHashCode(of={"clazz","name"})
	public static class Field implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private static final java.util.Collection<Field> COLLECTION = new HashSet<>();
		
		private Class<?> clazz;
		private String name;
		private java.lang.reflect.Field javaField;
		
		private String nameIdentifier,valueFormat;
		
		/**
		 * relationship of field type towards class
		 */
		private Relationship relationship;
		
		private Collection<Constant.Action> cascadedActions;
		
		//private Object value;
		
		private Constraints constraints = new Constraints();
		
		public Field(Class<?> clazz,String name) {
			this.clazz = clazz;
			this.name = name;
			if(this.clazz != null){
				this.javaField = getInstance().get(this.clazz, this.name);
				if(this.javaField==null)
					System.out.println("FieldHelper.Field.Field() "+clazz+"."+name);
				constraints.isNullable = this.javaField.getAnnotation(javax.validation.constraints.NotNull.class) == null;	
			}
			
		}
		
		public Constraints getConstraints(){
			if(constraints == null)
				constraints = new Constraints();
			return constraints;
		}
		
		public String getIdentifier(Class<?> aClass) {
			String identifier = constraints.getIdentifier(aClass);
			if(StringHelper.getInstance().isBlank(identifier)) {
				identifier = StringHelper.getInstance().concatenate(new Object[] {clazz.getSimpleName(),name,aClass.getSimpleName()}, Constant.CHARACTER_UNDESCORE);
				constraints.getIdentifierMap().put(aClass, identifier);
			}
			return identifier;
		}
		
		public Field addCascadedActions(Collection<Constant.Action> cascadedActions){
			if(CollectionHelper.getInstance().isNotEmpty(cascadedActions)){
				if(this.cascadedActions == null)
					this.cascadedActions = new LinkedHashSet<>();
				this.cascadedActions.addAll(cascadedActions);
			}
			return this;
		}
		
		public Field addCascadedActions(Constant.Action...cascadedActions){
			addCascadedActions(CollectionHelper.getInstance().get(cascadedActions));
			return this;
		}
		
		public static Field get(Class<?> clazz,String...names){
			String name = getInstance().buildPath(names);
			Field field = null;
			for(Field index : COLLECTION)
				if(index.clazz.equals(clazz) && index.name.equals(name)){
					field = index;
					break;
				}
			if(field == null){
				field = new Field(clazz, name);
				COLLECTION.add(field);
			}
			return field;
		}
		
		public static Boolean isComputedByUser(Class<?> clazz,String name) {
			Field field = get(clazz, name);
			return field == null ? null : field.getConstraints().getIsComputedByUser();
		}
		
		public static java.util.Collection<Field> get(Class<?> clazz){
			Collection<Field> collection = new ArrayList<FieldHelper.Field>();
			for(Field index : COLLECTION)
				if(index.clazz.equals(clazz)){
					collection.add(index);
				}
			return collection;
		}
		
		public static java.util.Collection<Field> getByClassByRelationshipByActions(Class<?> clazz,Relationship relationship,Constant.Action...actions){
			java.util.Collection<Field> result = new ArrayList<FieldHelper.Field>();
			for(FieldHelper.Field index : FieldHelper.Field.get(clazz)){
		    	if(relationship.equals(index.getRelationship()) && CollectionHelper.getInstance().contains(index.getCascadedActions(), actions)){
		    		result.add(index);
		    	}
		    }
			return result;
		}
		
		public static void clear(){
			COLLECTION.clear();
		}
		
		/**/
	
		public static enum Relationship {
			PARENT,CHILD
		}
		
		@Getter @Setter @Accessors(chain=true)
		public static class Value implements Serializable {
			private static final long serialVersionUID = 1L;
			
			private Field field;
			private Object value;
			
			/**/
			
			@Getter @Setter @Accessors(chain=true)
			public static class Collection implements Serializable {
				private static final long serialVersionUID = 1L;
				
				private java.util.Collection<Value> values;
				
				public Collection add(java.util.Collection<Field.Value> fields) {
					if(CollectionHelper.getInstance().isNotEmpty(fields)) {
						if(this.values == null)
							this.values = new ArrayList<>();
						this.values.addAll(fields);
					}
					return this;
				}
				
				public Collection add(Field.Value...fields) {
					if(ArrayHelper.getInstance().isNotEmpty(fields)) {
						add(Arrays.asList(fields));
					}
					return this;
				}
				
				public Collection addValue(Class<?> aClass,Object value,String...fieldNames) {
					add(new Value().setField(Field.get(aClass, fieldNames)).setValue(value));
					return this;
				}
			}
		}
	
	}	
	
	@Getter @Setter @Accessors(chain=true)
	public static class Constraints implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Boolean isNullable,isUpdatable,isComputedByUser;
		private Integer length,precision,scale,minimumNumberOfOccurence,maximumNumberOfOccurence;
		private Constant.Date.Part datePart;
		
		/**
		 * 
		 */
		private final Map<Object,String> identifierMap = new HashMap<>();
		
		public String getIdentifier(Object object) {
			return identifierMap.get(object);
		}
		
		public Constraints setIsUnique(Boolean isUnique){
			this.minimumNumberOfOccurence = 0;
			this.maximumNumberOfOccurence = 1;
			return this;
		}
		
		public Boolean getIsUnique(){
			return this.minimumNumberOfOccurence!=null && this.minimumNumberOfOccurence == 0 && this.maximumNumberOfOccurence!=null && this.maximumNumberOfOccurence == 1;
		}
	}
}
