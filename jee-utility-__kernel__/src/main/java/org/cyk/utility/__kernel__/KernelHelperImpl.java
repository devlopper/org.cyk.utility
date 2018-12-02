package org.cyk.utility.__kernel__;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Singleton;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

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
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T instanciateOne(Class<T> aClass) {
		try {
			if(Map.class.equals(aClass))
				aClass = (Class<T>) HashMap.class;
			if(Collection.class.equals(aClass))
				aClass = (Class<T>) List.class;
			if(List.class.equals(aClass))
				aClass = (Class<T>) ArrayList.class;
			if(Set.class.equals(aClass))
				aClass = (Class<T>) LinkedHashSet.class;
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void copyMapNonNullKeys(@SuppressWarnings("rawtypes") Map source, @SuppressWarnings("rawtypes") Map destination) {
		if(source!=null && destination!=null) {
			for(Object index : source.entrySet()) {
				@SuppressWarnings("rawtypes")
				Map.Entry entry = (Entry) index;
				if(entry.getKey()!=null)
					destination.put(entry.getKey(), entry.getValue());
			}
		}
	}
	
	@Override
	public Boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}
	
	@Override
	public Boolean isNotEmpty(Collection<?> collection) {
		return !Boolean.TRUE.equals(isEmpty(collection));
	}
	
	@Override
	public <T> KernelHelper addToCollection(Collection<T> collection, Collection<T> elements) {
		if(collection!=null && elements!=null) {
			collection.addAll(elements);
		}
		return this;
	}
	
	@Override
	public <T> KernelHelper addToCollection(Collection<T> collection, @SuppressWarnings("unchecked") T... elements) {
		if(collection!=null && elements!=null) {
			addToCollection(collection, Arrays.asList(elements));
		}
		return this;
	}
	
	@Override
	public <T> T getElementAt(Collection<T> collection, Integer index) {
		T object = null;
		if(index!=null && index >=0 &&  collection!=null && index < collection.size()) {
			if(collection instanceof List)
				object = ((List<T>)collection).get(index.intValue());
			else {
				Iterator<T> iterator = collection.iterator();
				object = iterator.next();
				Integer count = 0;
				while(count++<index)
					object = iterator.next();
			}
		}
		return object;
	}
	
	@Override
	public <T> T getElementAtEnd(Collection<T> collection) {
		return collection == null || collection.isEmpty() ? null : getElementAt(collection,collection.size()-1);
	}

	protected String __getMethodNameFromFieldName__(String fieldName,String prefix) {
		String methodName = null;
		if(fieldName!=null && fieldName.length()>0) {
			methodName = prefix+StringUtils.substring(fieldName, 0,1).toUpperCase()+StringUtils.substring(fieldName, 1);
		}
		return methodName;
	}
	
	@Override
	public String getMethodGetterNameFromFieldName(String fieldName) {
		String methodName = null;
		if(fieldName!=null && fieldName.length()>0) {
			methodName = StringUtils.substring(fieldName, 0,1).toUpperCase()+StringUtils.substring(fieldName, 1);
		}
		return methodName;
	}
	
	@Override
	public String getMethodSetterNameFromFieldName(String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object executeMethodGetter(Object object, String fieldName) {
		Object value = null;
		if(object != null && fieldName!=null && fieldName.length()>0) {
			String methodName = __getMethodNameFromFieldName__(fieldName, METHOD_GETTER_PREFIX);
			try {
				value = MethodUtils.invokeMethod(object, methodName);
			} catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}
		return value;
	}

	@Override
	public void executeMethodSetter(Object object, String fieldName, Object value) {
		if(object != null  && fieldName!=null) {
			String methodName = __getMethodNameFromFieldName__(fieldName, METHOD_SETTER_PREFIX);
			try {
				value = MethodUtils.invokeMethod(object, methodName,value);
			} catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}
	}
	
	@Override
	public Class<?> getFieldType(Class<?> aClass, String fieldName) {
		Class<?> clazz = null;
		if(fieldName!=null && fieldName.length()>0) {
			Field field = FieldUtils.getField(aClass, fieldName, Boolean.TRUE);
			if(field!=null)
				clazz = field.getType();
		}
		return clazz;
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
			System.err.println("File "+filePath+" does not exist");
		}
		return strings;
	}
	
	private static final String IMPL = "Impl";
	private static final String EMPTY_STRING = "Impl";
	private static final String UTF_8 = "UTF-8";
	private static final String METHOD_GETTER_PREFIX = "get";
	private static final String METHOD_SETTER_PREFIX = "set";

}
