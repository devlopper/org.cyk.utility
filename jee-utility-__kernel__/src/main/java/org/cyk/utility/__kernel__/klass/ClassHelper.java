package org.cyk.utility.__kernel__.klass;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;

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
	
	static Collection<Class<?>> filter(Collection<Package> packages,String classNameRegularExpression,Collection<Class<?>> basesClasses,Collection<Integer> modifiersIncluded,Collection<Integer> modifiersExcluded) {
		if(packages == null || packages.isEmpty())
			return null;
		if(basesClasses == null || basesClasses.isEmpty())
			return null;
		if(classNameRegularExpression!= null && classNameRegularExpression.isBlank())
			classNameRegularExpression = null;
		Collection<Class<?>> classes = null;	
		Pattern pattern = null;
		if(classNameRegularExpression!=null)
			pattern = Pattern.compile(classNameRegularExpression);
		if(modifiersIncluded != null && modifiersIncluded.isEmpty())
			modifiersIncluded = null;
		if(modifiersExcluded != null && modifiersExcluded.isEmpty())
			modifiersIncluded = null;
		SubTypesScanner subTypesScanner = new SubTypesScanner(false);
		for(@SuppressWarnings("rawtypes") Class indexBaseClass : basesClasses) {			
			ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
			configurationBuilder.setScanners(subTypesScanner);
			configurationBuilder.forPackages(packages.stream().map(Package::getName).collect(Collectors.toList()).toArray(new String[] {}));
			@SuppressWarnings({ "unchecked" })
			Collection<Class<?>> result = new Reflections(configurationBuilder).getSubTypesOf(indexBaseClass);
			if(CollectionHelper.isNotEmpty(result)) {
				for(@SuppressWarnings("rawtypes") Class index : result) {
					if(pattern != null && !pattern.matcher(index.getName()).find())
						continue;
					if(modifiersIncluded != null && !Helper.isHaveAllModifiers(index.getModifiers(), modifiersIncluded))
						continue;	
					if(modifiersExcluded != null && Helper.isHaveAllModifiers(index.getModifiers(), modifiersExcluded))
						continue;
					if(classes == null)
						classes = new ArrayList<>();
					classes.add(index);
				}
			}
		}		
		return classes;
	}
	
	static Collection<Class<?>> filter(Collection<Package> packages,String classNameRegularExpression,Collection<Class<?>> basesClasses,Collection<Integer> modifiers) {
		return filter(packages,classNameRegularExpression, basesClasses, modifiers,null);
	}
	
	static Collection<Class<?>> filter(Collection<Package> packages,String classNameRegularExpression,Collection<Class<?>> basesClasses) {
		return filter(packages,classNameRegularExpression, basesClasses, null,null);
	}
	
	static Collection<Class<?>> filter(Collection<Package> packages,Collection<Class<?>> basesClasses) {
		return filter(packages,null, basesClasses, null,null);
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