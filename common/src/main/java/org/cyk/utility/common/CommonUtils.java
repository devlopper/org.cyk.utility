package org.cyk.utility.common;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;

import lombok.extern.java.Log;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

@Log
public class CommonUtils implements Serializable  {

	private static final long serialVersionUID = -6146661020703974108L;
	
	public Field getField(Object source,Object value){
		for(Field field : getAllFields(source.getClass()))
			try {
				if(value==FieldUtils.readField(field, source, true))
					return field;
			} catch (Exception e) {
				log.log(Level.SEVERE,e.toString(),e);
				return null;
			}
		return null;
	}
	
	public Collection<Field> getAllFields(Collection<Field> fields,Class<?> type) {
		for (Field field : type.getDeclaredFields()) {
			fields.add(field);
		}

		if (type.getSuperclass() != null) {
			fields = getAllFields(fields, type.getSuperclass());
		}

		return fields;
	}
	
	public Collection<Field> getAllFields(Class<?> type) {
		Collection<Field> fields = new ArrayList<>();
		return getAllFields(fields, type);
	}
	
	public Collection<Field> getAllFields(Class<?> type,Collection<Class<? extends Annotation>> annotationClasses) {
		Collection<Field> fields = new ArrayList<>();
		for(Field field : getAllFields(type))
			for(Class<? extends Annotation> annotationClass : annotationClasses)
				if(field.isAnnotationPresent(annotationClass))
					fields.add(field);
		return fields;
	}

	public Boolean isNumberClass(Class<?> aClass){
		return Number.class.isAssignableFrom(ClassUtils.primitiveToWrapper(aClass));
	}
	
	public Object readField(Object object,Field field,Boolean createIfNull){
		Object r = null;
		try {
			r = FieldUtils.readField(field, object, true);
			if(r==null && Boolean.TRUE.equals(createIfNull))
				r = field.getType().newInstance();
		} catch (Exception e) {
			log.log(Level.SEVERE,e.toString(),e);
		}
		return r;
	}
	
	public <T extends Annotation> T getAnnotation(Class<?> aClass,Class<T> anAnnotationClass){
		T annotation = aClass.getAnnotation(anAnnotationClass);
		if(annotation==null && aClass.getSuperclass()!=null)
			return getAnnotation(aClass.getSuperclass(), anAnnotationClass);
		return annotation;
	}
	
	public Throwable getThrowableInstanceOf(Throwable throwable,Class<?> aClass){
		Throwable index = throwable;
		while(index!=null){
			//System.out.println(aClass.getSimpleName()+" - "+index.getClass().getSimpleName()+" - "+aClass.isAssignableFrom(index.getClass()));
			if(aClass.isAssignableFrom(index.getClass())){
				return index;
			}else
				index = index.getCause();
		}
		return null;
	}
	
	/**/
	
	
	
	/**/
	
	private CommonUtils() {}
	
	private static final CommonUtils INSTANCE = new CommonUtils();
	
	public static CommonUtils getInstance() {
		return INSTANCE;
	}
}
