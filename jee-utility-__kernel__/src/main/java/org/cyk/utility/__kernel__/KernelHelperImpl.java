package org.cyk.utility.__kernel__;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.inject.Singleton;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;

@Singleton
public class KernelHelperImpl implements KernelHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String getInterfaceImplementationNameSuffix() {
		return IMPL;
	}
	
	@Override
	public String getInterfaceSimpleName(Class<?> aClass) {
		return aClass == null ? null : StringUtils.substringBefore(aClass.getSimpleName(),getInterfaceImplementationNameSuffix());
	}
	
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
			Constructor<T> constructor = DependencyInjection.inject(KernelHelper.class).getConstructor(aClass, classes);
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
		if(count!=null && count>0){
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
	public <CLASS> Constructor<CLASS> getConstructor(Class<CLASS> aClass, Class<?>[] parameters) {
		try {
			return ConstructorUtils.getMatchingAccessibleConstructor(aClass, parameters);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public String getEmptyString() {
		return EMPTY_STRING;
	}
	
	@Override
	public Number addNumbers(Number... numbers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Number subtractNumbers(Number... numbers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isNumber(Class<?> aClass) {
		return isInstanceOf(ClassUtils.primitiveToWrapper(aClass), Number.class);
	}

	@Override @SuppressWarnings({ "unchecked", "rawtypes" })
	public void copyMap(Map source, Map destination, Object[] keys, Boolean removeNullValue) {
		if(keys!=null && keys.length>0)
			for(Object key : keys){
				Object value = source.get(key);
				if(value == null && Boolean.TRUE.equals(removeNullValue))
					destination.remove(key);
				else
					destination.put(key, value);
			}
	}

	@Override @SuppressWarnings({ "rawtypes" })
	public void copyMap(Map source, Map destination, Object[] keys) {
		copyMap(source, destination, keys, Boolean.TRUE);
	}
	
	/**/

	public static String __getString__(InputStream inputStream) {
		try {
			return IOUtils.toString(inputStream,UTF_8);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public static String __getString__(File file) {
		try {
			return __getString__(new FileInputStream(file));
		} catch (FileNotFoundException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public static String __getStringFromFile__(String filePath) {
		String strings;
		File file = new File(filePath);
		if(file.exists()){
			strings = __getString__(file);
		}else{
			strings = null;
			//TODO log warning
		}
		return strings;
	}
	
	private static final String IMPL = "Impl";
	private static final String EMPTY_STRING = "Impl";
	private static final String UTF_8 = "UTF-8";

}
