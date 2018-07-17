package org.cyk.utility.clazz;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Singleton;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.log.Log;
import org.cyk.utility.method.MethodHelper;
import org.cyk.utility.number.NumberHelper;
import org.cyk.utility.string.StringConstant;

@Singleton
public class ClassHelperImpl extends AbstractHelper implements ClassHelper , Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Boolean isInstanceOf(Class<?> aClass, Class<?> baseClass) {
		if (aClass == null || baseClass == null)
			return Boolean.FALSE;
		return baseClass.isAssignableFrom(aClass);
	}

	@Override
	public <T> T instanciate(Class<T> aClass, Object[] constructorParameters) {
		Class<?>[] classes = new Class[constructorParameters.length / 2];
		Object[] arguments = new Object[constructorParameters.length / 2];
		int j = 0;
		for (int i = 0; i < constructorParameters.length; i = i + 2) {
			classes[j] = (Class<?>) constructorParameters[i];
			arguments[j++] = constructorParameters[i + 1];
		}
		try {
			Constructor<T> constructor = __inject__(MethodHelper.class).getConstructor(aClass, classes);
			if (constructor == null) {
				//TODO log error
				//logError("no constructor found in class % with parameters %", aClass, StringUtils.join(classes, ","));
				return null;
			}
			return constructor.newInstance(arguments);
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	@Override
	public <T> T instanciateOne(Class<T> aClass) {
		try {
			return aClass.newInstance();
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Override
	public <T> Collection<T> instanciate(Class<T> aClass, Integer count) {
		Collection<T> collection = null;
		if(DependencyInjection.inject(NumberHelper.class).isGreaterThanZero(count)){
			collection = new ArrayList<T>();
			for(Integer index = 0; index < count; index++)
				collection.add(instanciateOne(aClass));
		}
		return collection;
	}

	@Override
	public <T> T instanciate(Class<T> aClass) {
		return instanciateOne(aClass);
	}
	
	@Override
	public Boolean areEqual(Class<?> class1, Class<?> class2,Class<?>...classes) {
		if(class1 == null || class2 == null)
			return null;
		return class1.equals(class2);
	}
	
	@Override
	public Class<?> getWrapper(Class<?> aClass) {
		return ClassUtils.primitiveToWrapper(aClass);
	}

	@Override
	public Boolean isNumber(Class<?> aClass) {
		return isInstanceOf(Number.class, getWrapper(aClass));
	}

	@Override
	public Boolean isString(Class<?> aClass) {
		return areEqual(java.lang.String.class, aClass);
	}

	@Override
	public Boolean isDate(Class<?> aClass) {
		return areEqual(java.util.Date.class, aClass);
	}

	@Override
	public Boolean isBoolean(Class<?> aClass) {
		return areEqual(Boolean.class, getWrapper(aClass));
	}
	
	@Override
	public Collection<Class<?>> getInterfaces(Class<?> aClass) {
		return ClassUtils.getAllInterfaces(aClass);
	}
	
	@Override
	public Class<?> getInterfaceByClassSimpleName(Class<?> aClass) {
		Class<?> result = null;
		if(aClass == null){
			
		}else {
			Collection<Class<?>> interfaces = getInterfaces(aClass);
			if(__inject__(CollectionHelper.class).isNotEmpty(interfaces))
				for(Class<?> index : interfaces)
					if(index!=null)
						if(getImplementationClassSimpleName(index).equals(aClass.getSimpleName())){
							result = index;	
							break;
						}
		}
		return result;
	}
	
	@Override
	public String getImplementationClassSimpleName(Class<?> interfaceClass) {
		return interfaceClass == null ? null : interfaceClass.getSimpleName()+StringConstant.IMPL;
	}
	
	@Override
	public String getInterfaceSimpleName(Class<?> aClass) {
		return aClass == null ? null : StringUtils.substringBefore(aClass.getSimpleName(),StringConstant.IMPL);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <TYPE> Class<TYPE> getParameterAt(Class<?> aClass, Integer index, Class<TYPE> typeClass) {
		return (Class<TYPE>) ((ParameterizedType) aClass.getGenericSuperclass()).getActualTypeArguments()[index];
	}
	
	@Override
	public Class<?> getByName(String name) {
		try {
			return Class.forName(name);
		} catch (Exception exception) {
			__inject__(Log.class).executeThrowable(exception);
			return null;
		}
	}
	
	@Override
	public String getSimpleName(Class<?> aClass) {
		return aClass == null ? null : aClass.getSimpleName();
	}
	
	@Override
	public String getSimpleName(String string) {
		return StringUtils.contains(string, DOT) ? StringUtils.substringAfterLast(string, DOT) : string;
	}
	
	/**/
	
	private static final String DOT = ".";
}
