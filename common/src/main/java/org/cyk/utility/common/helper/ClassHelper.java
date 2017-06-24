package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import javax.inject.Singleton;

import org.apache.commons.lang3.ClassUtils;
import org.cyk.utility.common.annotation.FieldOverride;
import org.reflections.Reflections;

@Singleton
public class ClassHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	public Boolean isNumber(Class<?> aClass){
		return Number.class.isAssignableFrom(ClassUtils.primitiveToWrapper(aClass));
	}	
	
	public Class<?> get(Class<?> aClass, String fieldName,Class<?> fieldType) {
		FieldOverride fieldOverride = inject(FieldHelper.class).getOverride(aClass,fieldName);
		Class<?> clazz;
		if(fieldOverride==null)
			clazz = fieldType;
		else
			clazz = fieldOverride.type();
		return clazz;
	}
	
	public Class<?> get(Class<?> aClass, String fieldName) {
		Field field = inject(FieldHelper.class).get(aClass, fieldName);
		return get(aClass, field);
	}
	
	public Class<?> get(Class<?> aClass, Field field) {
		return get(aClass, field.getName(), field.getType());
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Class<?>> get(String packageName,Class<?> baseClass){
		Reflections reflections = new Reflections(packageName);
		@SuppressWarnings("rawtypes")
		Collection classes = reflections.getSubTypesOf(baseClass);
	    return classes;
	}
	
	@SuppressWarnings("unchecked")
	public <TYPE> Class<TYPE> getParameterAt(Class<?> aClass,Integer index,Class<TYPE> typeClass){
		return (Class<TYPE>) ((ParameterizedType) aClass.getGenericSuperclass()).getActualTypeArguments()[index];
	}
	
	public Class<?> getByName(String name){
		try {
			return Class.forName(name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
