package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
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

@Singleton
public class FieldHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String FIELD_NAME_SEPARATOR = Constant.CHARACTER_DOT.toString();
	
	public String buildPath(String...fieldNames){
		return StringUtils.join(fieldNames,FIELD_NAME_SEPARATOR);
	}
	
	public List<String> getFieldNames(String...fieldPaths){
		return Arrays.asList(StringUtils.split(buildPath(fieldPaths), FIELD_NAME_SEPARATOR));
	}
	
	public Object read(Object instance,String...fieldNames){
		try {
			return PropertyUtils.getProperty(instance, buildPath(fieldNames));
		} catch (Exception e) {
			logThrowable(e);
			return null;
		}
	}
	
	public void set(Object object,String...fieldNames){
		for(String p : getFieldNames(fieldNames)){
			Object pValue = read(object, p);
			if(pValue==null){
				Field field = ClassRepository.getInstance().getField(object.getClass(), p);
				try {
					FieldUtils.writeField(object, p, pValue = field.getType().newInstance(), Boolean.TRUE);
				} catch (Exception e) {
					new RuntimeException(e);
				}
				logTrace("Field {} of object {} instanciated",field,object);
			}
			object = pValue;
		}
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
			PropertyUtils.setProperty(instance, path, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
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
	
	public Field getByValue(Object source,Object value){
		for(Field field : get(source.getClass()))
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
	
	private Collection<Field> __getAllFields__(Collection<Field> fields,Class<?> type,String token,Location location) {
		//super class fields first
		if (type.getSuperclass() != null) {
			fields = __getAllFields__(fields, type.getSuperclass(),token,location);
		}
		//declared class fields second
		for (Field field : type.getDeclaredFields()) {
			if(StringHelper.getInstance().isAtLocation(field.getName(), token, location))
				fields.add(field);
		}
		
		return fields;
	}
	
	public Collection<Field> get(Class<?> type,String name,Location location) {
		Collection<Field> fields = new ArrayList<>();
		if(Boolean.TRUE.equals(ClassRepository.ENABLED)){
			for(Field field : ClassRepository.getInstance().get(type).getFields())
				if(StringHelper.getInstance().isAtLocation(field.getName(), name, location))
					fields.add(field);
		}else{
			__getAllFields__(fields, type,name,location);
		}
		return fields;
	}
	
	public Collection<Field> get(Class<?> type) {
		return get(type,null,null);
	}
	
	public Field get(Class<?> type,String name) {
		StringHelper stringHelper = new StringHelper();
		for(Field field : get(type))
			if(stringHelper.isAtLocation(field.getName(), name, Location.EXAT))
				return field;
		return null;
	}
	
	public Collection<Field> get(Class<?> type,Collection<Class<? extends Annotation>> annotationClasses) {
		Collection<Field> fields = new ArrayList<>();
		for(Field field : get(type))
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
	
	public Collection<Field> get(Class<?> type,Class<? extends Annotation> annotationClass) {
		Collection<Class<? extends Annotation>> collection = new ArrayList<>();
		collection.add(annotationClass);
		return get(type,collection);
	}
	
	public Object readField(Object object,Field field,Boolean recursive,Boolean createIfNull,Boolean autoSet,Collection<Class<? extends Annotation>> annotationClasses){
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
	
	public Object readField(Object object,Field field,Boolean recursive,Boolean createIfNull,Collection<Class<? extends Annotation>> annotationClasses){
		return readField(object, field,recursive, createIfNull, Boolean.FALSE,annotationClasses);
	}
	
	public Object readField(Object object,Field field,Boolean createIfNull,Boolean autoSet){
		return readField(object, field, Boolean.FALSE, createIfNull,autoSet,null);
	}
	
	public Object readField(Object object,Field field,Boolean createIfNull){
		return readField(object, field, Boolean.FALSE, createIfNull,null);
	}
	
	private Object __readField__(Object object,Field field,Boolean recursive,Collection<Class<? extends Annotation>> annotationClasses){
		Object value = null;
		
		if(object==null)
			;
		else
			try {
				if(Boolean.TRUE.equals(recursive)){
					Collection<Field> fields = get(object.getClass(),annotationClasses);
					if(fields.contains(field))
						value = FieldUtils.readField(field, object,Boolean.TRUE);
					else{
						for(Field f : fields)
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
	
	public void writeField(Field field, Object target, Object value){
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

		return fieldOverride;
	}
	
	public Object getFieldValueContainer(Object object,Field field){
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
		for(Field f : object.getClass().getDeclaredFields()){
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
	
	/*public Collection<String> getNamesWhereIdentifierStartsByFieldPrefix(Class<?> aClass){
		Collection<Field> fields = new FieldHelper().get(aClass, Constant.FIELD_, Location.START);
		Collection<String> names = new InstanceHelper().callGetMethod(fields, String.class, METHOD_GET_NAME_SUFFIX);
		return new StringHelper().removeBlank(names);
	}*/
	
	public Collection<String> getNamesWhereReferencedByStaticField(Class<?> aClass){
		Collection<Field> fields = new FieldHelper().get(aClass, Constant.FIELD_, Location.START);
		Collection<String> names = new ArrayList<>();
		for(Field field : fields)
			if(Modifier.isStatic(field.getModifiers()))
				try {
					names.add((String)field.get(null));
				} catch (Exception e) {
					e.printStackTrace();
				}
		return new StringHelper().removeBlank(names);
	}
	
	/**/
	
	//private static final String METHOD_GET_NAME_SUFFIX = "name";
}
