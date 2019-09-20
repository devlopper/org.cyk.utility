package org.cyk.utility.__kernel__;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

public interface ClassHelper {

	@SuppressWarnings("unchecked")
	static <T> T instanciate(Class<T> klass, Object[] constructorParameters) {
		if(klass == null)
			return null;
		if(Map.class.equals(klass))
			klass = (Class<T>) HashMap.class;
		if(Collection.class.equals(klass))
			klass = (Class<T>) List.class;
		if(List.class.equals(klass))
			klass = (Class<T>) ArrayList.class;
		if(Set.class.equals(klass))
			klass = (Class<T>) LinkedHashSet.class;
		
		Class<?>[] classes = null;
		Object[] arguments = null;
		
		if(constructorParameters!=null && constructorParameters.length>0) {
			classes = new Class[constructorParameters.length / 2];
			arguments = new Object[constructorParameters.length / 2];
			int j = 0;
			for (int i = 0; i < constructorParameters.length; i = i + 2) {
				classes[j] = (Class<?>) constructorParameters[i];
				arguments[j++] = constructorParameters[i + 1];
			}	
		}
		try {
			return klass.getDeclaredConstructor(classes).newInstance(arguments);
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new RuntimeException(exception);
		}
	}
	
	
	static <T> T instanciate(Class<T> klass) {
		return instanciate(klass, null);
	}

	static <T> Collection<T> instanciateMany(Class<T> klass, Integer count) {
		if(klass == null || count == null || count < 0)
			return null;
		Collection<T> collection = new ArrayList<T>();
		for(Integer index = 0 ; index < count ; index = index + 1)
			collection.add(instanciate(klass));
		return collection;
	}
	
	static Boolean isInstanceOf(Class<?> klass, Class<?> baseClass) {
		if (klass == null || baseClass == null)
			return Boolean.FALSE;
		return baseClass.isAssignableFrom(klass);
	}
	
	static Class<?> getByName(String name,Boolean returnNullIfNotFound) {
		Class<?> klass;
		try {
			klass = Class.forName(name);
		} catch (ClassNotFoundException exception) {
			if(Boolean.TRUE.equals(returnNullIfNotFound))
				klass = null;
			else
				throw new RuntimeException(exception);
		}
		return klass;
	}
	
	@SuppressWarnings("unchecked")
	static <TYPE> Class<TYPE> getParameterAt(Class<?> klass, Integer index, Class<TYPE> typeClass) {
		if(klass == null || index == null || typeClass == null)
			return null;
		Class<TYPE> parameter = null;
		if(klass.getGenericSuperclass() instanceof ParameterizedType)
			parameter = (Class<TYPE>) ((ParameterizedType) klass.getGenericSuperclass()).getActualTypeArguments()[index];
		else
			throw new RuntimeException("get generic parameter of type "+klass.getGenericSuperclass()+" not yet handled");
		return parameter;
	}
	
	static Boolean isNumber(Class<?> aClass) {
		return isInstanceOf(ClassUtils.primitiveToWrapper(aClass), Number.class);
	}
	
	static String getInterfaceImplementationNameSuffix() {
		return IMPL;
	}
	
	static String getInterfaceSimpleName(Class<?> aClass) {
		return aClass == null ? null : StringUtils.substringBefore(aClass.getSimpleName(),getInterfaceImplementationNameSuffix());
	}
	
	String IMPL = "Impl";
}
