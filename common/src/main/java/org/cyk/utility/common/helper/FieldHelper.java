package org.cyk.utility.common.helper;

import java.beans.IntrospectionException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Singleton;

import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.apache.commons.beanutils.IntrospectionContext;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.utility.common.ClassRepository;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.FieldOverrides;
import org.cyk.utility.common.helper.StringHelper.Location;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Singleton
public class FieldHelper extends AbstractReflectionHelper<java.lang.reflect.Field> implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String FIELD_NAME_SEPARATOR = Constant.CHARACTER_DOT.toString();
	
	public static final Set<String> INTROSPECTION_CONTEXT_TARGET_CLASS_NAME_PREFIXES_INCLUDED = new LinkedHashSet<String>();
	public static final Set<String> INTROSPECTION_CONTEXT_TARGET_CLASS_NAME_PREFIXES_EXCLUDED = new LinkedHashSet<String>();
	
	static {
		INTROSPECTION_CONTEXT_TARGET_CLASS_NAME_PREFIXES_INCLUDED.add("org.cyk.");
		PropertyUtils.addBeanIntrospector(new FluentPropertyBeanIntrospector(){
			@Override
			public void introspect(IntrospectionContext introspectionContext) throws IntrospectionException {
				Boolean included = StringHelper.getInstance().isAtLocation(introspectionContext.getTargetClass().getName()
						, INTROSPECTION_CONTEXT_TARGET_CLASS_NAME_PREFIXES_INCLUDED , StringHelper.Location.START);
				Boolean excluded = StringHelper.getInstance().isAtLocation(introspectionContext.getTargetClass().getName()
						, INTROSPECTION_CONTEXT_TARGET_CLASS_NAME_PREFIXES_EXCLUDED , StringHelper.Location.START);
				if(Boolean.TRUE.equals(included) && Boolean.FALSE.equals(excluded)){
					super.introspect(introspectionContext);
				}
			}
		});
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
	
	public String buildPath(String...fieldNames){
		return ArrayHelper.getInstance().isEmpty(fieldNames) ? Constant.EMPTY_STRING : StringUtils.join(fieldNames,FIELD_NAME_SEPARATOR);
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
		return StringUtils.contains(fieldName, FIELD_NAME_SEPARATOR) ? StringUtils.substringBeforeLast(fieldName, FIELD_NAME_SEPARATOR) : fieldName;
	}
	
	public List<String> getFieldNames(String...fieldPaths){
		return Arrays.asList(StringUtils.split(buildPath(fieldPaths), FIELD_NAME_SEPARATOR));
	}
	
	public String getVariableNameFromNames(Collection<String> names){
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
		String beforeLastFieldName = getBeforeLast(fieldName);
		if(fieldName.equals(beforeLastFieldName))
			return instance;
		return read(instance, beforeLastFieldName);
	}
	
	public Object readStatic(java.lang.reflect.Field field){
		try {
			return FieldUtils.readStaticField(field, Boolean.TRUE);
		} catch (IllegalAccessException e) {
			logThrowable(e);
			return null;
		}
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
	
	public void copy(Object source,Object destination,String...fieldNames){
		copy(source, destination, Arrays.asList(fieldNames));
	}
	
	public void copy(Object source,Object destination,Collection<String> fieldNames){
		for(String fieldName : fieldNames){
			Object value = read(source, fieldName);
			set(destination,value, fieldName);
		}
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
	
	private Collection<java.lang.reflect.Field> __getAllFields__(Collection<java.lang.reflect.Field> fields,Class<?> type,String token,Location location,Boolean recursive) {
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
	
	public Collection<java.lang.reflect.Field> get(Class<?> type,String name,Location location,Boolean recursive) {
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
	
	public Collection<java.lang.reflect.Field> get(Class<?> type,String name,Location location) {
		return get(type,name,location,Boolean.TRUE);
	}
	
	public Collection<java.lang.reflect.Field> get(Class<?> type) {
		return get(type,null,null);
	}
	
	public Collection<java.lang.reflect.Field> get(Class<?> type,Boolean recursive) {
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
	
	public Collection<java.lang.reflect.Field> get(Class<?> type,Collection<Class<? extends Annotation>> annotationClasses) {
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
	
	public Collection<java.lang.reflect.Field> get(Class<?> type,Class<? extends Annotation> annotationClass) {
		Collection<Class<? extends Annotation>> collection = new ArrayList<>();
		collection.add(annotationClass);
		return get(type,collection);
	}
	
	public Collection<java.lang.reflect.Field> getByTypes(Class<?> type,Collection<Class<?>> classes) {
		Collection<java.lang.reflect.Field> fields = new ArrayList<>();
		for(java.lang.reflect.Field field : get(type))
			if(CollectionHelper.getInstance().isNotEmpty(classes))
				if(classes.contains(getType(type, field)))
					fields.add(field);
		return fields;
	}
	
	public Collection<java.lang.reflect.Field> getByTypes(Class<?> type,Class<?>...classes) {
		if(ArrayHelper.getInstance().isEmpty(classes))
			return null;
		return getByTypes(type, Arrays.asList(classes));
	}
	
	public Collection<java.lang.reflect.Field> getByTypeIdentified(Class<?> type) {
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
	
	public Collection<String> getNamesWhereReferencedByStaticField(Class<?> aClass,Boolean recursive){
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
	
	public Collection<String> getNamesWhereReferencedByStaticField(Class<?> aClass){
		return getNamesWhereReferencedByStaticField(aClass,Boolean.TRUE);
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
				protected Collection<java.lang.reflect.Field> getTypes(Class<?> clazz) {
					return Arrays.asList(clazz.getDeclaredFields());
				}
				
			}
		}
		
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true) @EqualsAndHashCode(of={"clazz","name"})
	public static class Field implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private static final Collection<Field> COLLECTION = new HashSet<>();
		
		private Class<?> clazz;
		private String name;
		private java.lang.reflect.Field javaField;
		
		//private Object value;
		
		private Constraints constraints = new Constraints();
		
		public Field(Class<?> clazz,String name) {
			this.clazz = clazz;
			this.name = name;
			this.javaField = getInstance().get(this.clazz, this.name);
			constraints.isNullable = this.javaField.getAnnotation(javax.validation.constraints.NotNull.class) == null;
		}
		
		public Constraints getConstraints(){
			if(constraints == null)
				constraints = new Constraints();
			return constraints;
		}
		
		public static Field get(Class<?> clazz,String name){
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
		
		public static void clear(){
			COLLECTION.clear();
		}
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Constraints implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Boolean isNullable,isUnique,isUpdatable;
		private Integer length,precision,scale;
		private Constant.Date.Part datePart;
		
	}
}
