package org.cyk.utility.__kernel__.klass;

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
import org.cyk.utility.__kernel__.DependencyInjection;

public interface ClassHelper {

	/* get name*/
	
	static String getInterfaceSimpleNameFromImplementationClass(Class<?> klass) {
		if(klass == null || klass.isInterface() || klass.isEnum() || klass.isAnonymousClass() || klass.isArray())
			return null;
		return StringUtils.substringBeforeLast(klass.getSimpleName(),IMPL);
	}
	
	static String getImplementationClassSimpleNameFromInterfaceClass(Class<?> klass) {
		if(klass == null || klass.isEnum() || klass.isArray())
			return null;
		return klass.getSimpleName()+IMPL;
	}
	
	static String getSimpleName(String name) {
		if(name == null || name.isBlank())
			return null;
		return name.contains(".") ? StringUtils.substringBeforeLast(name,".") : name;
	}
	
	/* instantiate */
	
	@SuppressWarnings("unchecked")
	static <T> T instanciate(Class<T> klass, Object[] constructorParameters) {
		if(klass == null)
			return null;
		if(klass.isInterface()) {
			if(isBelongsToJavaPackages(klass)) {
				if(Map.class.equals(klass))
					klass = (Class<T>) HashMap.class;
				if(Collection.class.equals(klass))
					klass = (Class<T>) List.class;
				if(List.class.equals(klass))
					klass = (Class<T>) ArrayList.class;
				if(Set.class.equals(klass))
					klass = (Class<T>) LinkedHashSet.class;		
			}else
				klass = (Class<T>) getImplementationClass(klass);
		}	
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
	
	/* is instance of*/
	
	static Boolean isInstanceOf(Class<?> klass, Class<?> baseClass) {
		if (klass == null || baseClass == null)
			return Boolean.FALSE;
		return baseClass.isAssignableFrom(klass);
	}
	
	static Boolean isInstanceOfOne(Class<?> klass,Collection<Class<?>> classes) {
		if(klass == null || classes == null || classes.isEmpty())
			return Boolean.FALSE;
		for(Class<?> index : classes)
			if(isInstanceOf(klass, index))
				return Boolean.TRUE;
		return Boolean.FALSE;
	}
	
	static Boolean isInstanceOfOne(Class<?> klass, Class<?>... classes) {
		if(klass == null || classes == null || classes.length == 0)
			return Boolean.FALSE;
		return isInstanceOfOne(klass, List.of(classes));
	}
	
	static Boolean isInstanceOfCollection(Class<?> klass) {
		if(klass == null)
			return Boolean.FALSE;
		return isInstanceOf(klass, Collection.class);
	}
	
	static Boolean isInstanceOfNumber(Class<?> klass) {
		if(klass == null)
			return Boolean.FALSE;
		return isInstanceOf(getWrapper(klass),Number.class);
	}
	
	static Boolean isInstanceOfCharSequence(Class<?> klass) {
		if(klass == null)
			return Boolean.FALSE;
		return isInstanceOf(klass,CharSequence.class);
	}

	static Boolean isNumberOrCharSequenceOrEnum(Class<?> klass) {
		if(klass == null)
			return Boolean.FALSE;
		return isInstanceOfNumber(klass) || isInstanceOfCharSequence(klass) || klass.isEnum();
	}
	
	static Boolean isBelongsToJavaPackages(Class<?> klass) {
		if(klass == null)
			return Boolean.FALSE;
		return klass.isArray() || StringUtils.startsWithAny(klass.getName(), "java.","javax.");
	}
	
	static Class<?> getWrapper(Class<?> klass) {
		if(klass == null)
			return null;
		return ClassUtils.primitiveToWrapper(klass);
	}
	
	/* get */
	
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
	
	static Class<?> getByName(String name) {
		return getByName(name, Boolean.TRUE);
	}
	
	static Collection<Class<?>> getInterfaces(Class<?> klass) {
		if(klass == null)
			return null;
		List<Class<?>> interfaces = ClassUtils.getAllInterfaces(klass);
		if(interfaces == null || interfaces.isEmpty())
			return null;
		return interfaces;
	}
	
	static Class<?> getImplementationClass(Class<?> interfaceClass) {
		if(interfaceClass.isInterface()) {
			Class<?> implementationClass = IMPLEMENTATIONS.get(interfaceClass);
			if(implementationClass != null)
				return implementationClass;
			implementationClass = DependencyInjection.inject(interfaceClass).getClass();
			IMPLEMENTATIONS.put(interfaceClass, implementationClass);
			return implementationClass;
		}else
			return interfaceClass;
	}
	
	/* get parameter */
	
	static Class<?> getParameterAt(Class<?> klass, Integer index) {
		if(klass == null || index == null)
			return null;
		if(!(klass.getGenericSuperclass() instanceof ParameterizedType))
			return null;
		ParameterizedType parameterizedType = (ParameterizedType) klass.getGenericSuperclass();
		if(index > parameterizedType.getActualTypeArguments().length - 1)
			return null;
		return (Class<?>) parameterizedType.getActualTypeArguments()[index];
	}
	
	Map<Class<?>,Class<?>> IMPLEMENTATIONS = new HashMap<>();
	String IMPL = "Impl";
}