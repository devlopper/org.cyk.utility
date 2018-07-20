package org.cyk.utility.__kernel__.helper;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Map;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface KernelHelper extends Objectable {

	String getInterfaceImplementationNameSuffix();
	
	String getInterfaceSimpleName(Class<?> aClass);

	Boolean isInstanceOf(Class<?> aClass, Class<?> baseClass);
	<T> T instanciate(Class<T> aClass, Object[] constructorParameters);
	<T> T instanciateOne(Class<T> aClass);
	<T> Collection<T> instanciate(Class<T> aClass,Integer count);
	<T> T instanciate(Class<T> aClass);
	
	<CLASS> Constructor<CLASS> getConstructor(Class<CLASS> aClass, Class<?>[] parameters);
	
	String getEmptyString();
	
	Number addNumbers(Number...numbers);
	Number subtractNumbers(Number...numbers);
	Boolean isNumber(Class<?> aClass);
	
	@SuppressWarnings("rawtypes")
	void copyMap(Map source, Map destination, Object[] keys, Boolean removeNullValue);
	@SuppressWarnings("rawtypes")
	void copyMap(Map source, Map destination, Object[] keys);
}
