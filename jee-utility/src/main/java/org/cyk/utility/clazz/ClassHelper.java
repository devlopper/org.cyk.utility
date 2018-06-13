package org.cyk.utility.clazz;

import java.util.Collection;

public interface ClassHelper {

	Boolean isInstanceOf(Class<?> aClass, Class<?> baseClass);

	<T> T instanciateOne(Class<T> aClass);
	<T> Collection<T> instanciate(Class<T> aClass,Integer count);
	<T> T instanciate(Class<T> aClass);

	Boolean areEqual(Class<?> class1, Class<?> class2,Class<?>...classes);

	Boolean isNumber(Class<?> aClass);

	Class<?> getWrapper(Class<?> aClass);

	Boolean isString(Class<?> aClass);

	Boolean isDate(Class<?> aClass);

	Boolean isBoolean(Class<?> aClass);

}
