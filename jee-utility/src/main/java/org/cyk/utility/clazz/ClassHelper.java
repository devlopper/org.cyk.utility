package org.cyk.utility.clazz;

import java.util.Collection;

import org.cyk.utility.helper.Helper;

public interface ClassHelper extends Helper {

	Boolean isInstanceOf(Class<?> aClass, Class<?> baseClass);

	<T> T instanciate(Class<T> aClass, Object[] constructorParameters);
	<T> T instanciateOne(Class<T> aClass);
	<T> Collection<T> instanciate(Class<T> aClass,Integer count);
	<T> T instanciate(Class<T> aClass);

	Boolean areEqual(Class<?> class1, Class<?> class2,Class<?>...classes);

	Boolean isNumber(Class<?> aClass);

	Class<?> getWrapper(Class<?> aClass);

	Boolean isString(Class<?> aClass);

	Boolean isDate(Class<?> aClass);

	Boolean isBoolean(Class<?> aClass);

	Collection<Class<?>> getInterfaces(Class<?> aClass);

	Class<?> getInterfaceByClassSimpleName(Class<?> aClass);
	
	String getImplementationClassSimpleName(Class<?> interfaceClass);
	
	String getInterfaceSimpleName(Class<?> aClass);
	
	<TYPE> Class<TYPE> getParameterAt(Class<?> aClass, Integer index, Class<TYPE> typeClass);
	
	Class<?> getByName(String name,Boolean isReturnNullIfNotFound);
	Class<?> getByName(String name);
	
	String getSimpleName(Class<?> aClass);
	String getSimpleName(String string);
	
	Boolean isInstanceOfOne(Class<?> aClass,Collection<Class<?>> classes);
	Boolean isInstanceOfOne(Class<?> aClass,Class<?>...classes);
	
	/**/

}
