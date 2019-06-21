package org.cyk.utility.__kernel__;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
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
		return __isInstanceOf__(aClass, baseClass);
	}
	
	@Override
	public <T> T instanciate(Class<T> aClass, Object[] constructorParameters) {
		return __instanciate__(aClass, constructorParameters);
	}
	
	@Override
	public <T> T instanciateOne(Class<T> aClass) {
		return __instanciateOne__(aClass);
	}
	
	@Override
	public <T> Collection<T> instanciate(Class<T> aClass, Integer count) {
		return __instanciate__(aClass, count);
	}

	@Override
	public <T> T instanciate(Class<T> aClass) {
		return __instanciate__(aClass);
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
		if(source!=null && destination!=null && keys!=null && keys.length>0)
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
	
	@Override
	public <TYPE> Class<TYPE> getParameterAt(Class<?> aClass, Integer index, Class<TYPE> typeClass) {
		return __getParameterAt__(aClass, index, typeClass);
	}
	
	@Override
	public Collection<String> splitByCharacterTypeCamelCase(String string) {
		return __splitByCharacterTypeCamelCase__(string);
	}
	
	/**/

	public static Boolean __isInstanceOf__(Class<?> aClass, Class<?> baseClass) {
		if (aClass == null || baseClass == null)
			return Boolean.FALSE;
		return baseClass.isAssignableFrom(aClass);
	}
	
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
	
	public static <T> T __instanciate__(Class<T> aClass, Object[] constructorParameters) {
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
	public static <T> T __instanciateOne__(Class<T> aClass) {
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
	
	public static <T> Collection<T> __instanciate__(Class<T> aClass, Integer count) {
		Collection<T> collection = null;
		if(count!=null && count>0){
			collection = new ArrayList<T>();
			for(Integer index = 0; index < count; index++)
				collection.add(__instanciateOne__(aClass));
		}
		return collection;
	}

	public static <T> T __instanciate__(Class<T> aClass) {
		return __instanciateOne__(aClass);
	}
	
	public static <CLASS> Constructor<CLASS> __getConstructor__(Class<CLASS> aClass, Class<?>[] parameters) {
		try {
			return ConstructorUtils.getMatchingAccessibleConstructor(aClass, parameters);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Class<?> __getClassByName__(String name,Boolean returnNullIfNotFound) {
		Class<?> clazz;
		try {
			clazz = Class.forName(name);
		} catch (ClassNotFoundException e) {
			if(Boolean.TRUE.equals(returnNullIfNotFound))
				clazz = null;
			else
				throw new RuntimeException(e);
		}
		return clazz;
	}
	
	@SuppressWarnings("unchecked")
	public static <TYPE> Class<TYPE> __getParameterAt__(Class<?> aClass, Integer index, Class<TYPE> typeClass) {
		Class<TYPE> parameter = null;
		if(aClass != null && index != null && typeClass != null && aClass.getGenericSuperclass() instanceof ParameterizedType){
			parameter = (Class<TYPE>) ((ParameterizedType) aClass.getGenericSuperclass()).getActualTypeArguments()[index];
		}
		return parameter;
	}
	
	public static Collection<String> __splitByCharacterTypeCamelCase__(String string) {
		String[] strings =	StringUtils.splitByCharacterTypeCamelCase(string);
		return strings == null || strings.length == 0 ? null : Arrays.asList(strings);
	}
	
	private static final String IMPL = "Impl";
	private static final String EMPTY_STRING = "";
	private static final String UTF_8 = "UTF-8";
	private static final String METHOD_GETTER_PREFIX = "get";
	private static final String METHOD_SETTER_PREFIX = "set";

}
