package org.cyk.utility.__kernel__;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Map;

public interface KernelHelper {

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
	@SuppressWarnings("rawtypes")
	void copyMapNonNullKeys(Map source, Map destination);
	
	Boolean isEmpty(Collection<?> collection);
	Boolean isNotEmpty(Collection<?> collection);
	
	<T> KernelHelper addToCollection(Collection<T> collection,Collection<T> elements);
	<T> KernelHelper addToCollection(Collection<T> collection,@SuppressWarnings("unchecked") T...elements);
	
	<T> T getElementAt(Collection<T> collection,Integer index);
	<T> T getElementAtEnd(Collection<T> collection);
	
	String getMethodGetterNameFromFieldName(String fieldName);
	String getMethodSetterNameFromFieldName(String fieldName);
	Object executeMethodGetter(Object object,String fieldName);
	void executeMethodSetter(Object object,String fieldName,Object value);

	Class<?> getFieldType(Class<?> aClass, String fieldName);
}
