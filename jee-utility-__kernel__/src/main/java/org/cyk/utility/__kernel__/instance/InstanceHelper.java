package org.cyk.utility.__kernel__.instance;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.value.Identifier;
import org.cyk.utility.__kernel__.value.ValueUsageType;

public interface InstanceHelper {

	static void setInstanceGetter(InstanceGetter instanceGetter) {
		if(CONSTANTS.isEmpty())
			CONSTANTS.add(instanceGetter);
		else
			CONSTANTS.set(CONSTANT_INSTANCE_GETTER_INDEX, instanceGetter);
	}
	
	static InstanceGetter getInstanceGetter() {
		InstanceGetter instanceGetter = null;
		if(!CONSTANTS.isEmpty())
			instanceGetter = (InstanceGetter) CONSTANTS.get(CONSTANT_INSTANCE_GETTER_INDEX);
		if(instanceGetter == null)
			instanceGetter = InstanceGetter.INSTANCE;
		return instanceGetter;
	}
	
	static <INSTANCE> INSTANCE getByIdentifier(Class<INSTANCE> klass,Object identifier,ValueUsageType valueUsageType,InstanceGetter getter) {
		if(klass == null || identifier == null)
			return null;
		if(getter == null)
			getter = getInstanceGetter();
		return getter.getByIdentifier(klass, identifier,valueUsageType);
	}
	
	static <INSTANCE> INSTANCE getBySystemIdentifier(Class<INSTANCE> klass,Object identifier,InstanceGetter getter) {
		if(klass == null || identifier == null)
			return null;
		if(getter == null)
			getter = getInstanceGetter();
		return getter.getBySystemIdentifier(klass, identifier);
	}
	
	static <INSTANCE> INSTANCE getBySystemIdentifier(Class<INSTANCE> klass,Object identifier) {
		if(klass == null || identifier == null)
			return null;
		return getInstanceGetter().getBySystemIdentifier(klass, identifier);
	}
	
	static <INSTANCE> INSTANCE getByBusinessIdentifier(Class<INSTANCE> klass,Object identifier,InstanceGetter getter) {
		if(klass == null || identifier == null)
			return null;
		if(getter == null)
			getter = getInstanceGetter();
		return getter.getByBusinessIdentifier(klass, identifier);
	}
	
	static <INSTANCE> INSTANCE getByBusinessIdentifier(Class<INSTANCE> klass,Object identifier) {
		if(klass == null || identifier == null)
			return null;
		return getInstanceGetter().getByBusinessIdentifier(klass, identifier);
	}
	
	static <INSTANCE> Collection<INSTANCE> getByIdentifiers(Class<INSTANCE> klass,Collection<Identifier> identifiers) {
		if(klass == null || CollectionHelper.isEmpty(identifiers))
			return null;		
		Collection<INSTANCE> instances = null;
		for(Identifier index : identifiers) {
			if(index == null)
				continue;
			INSTANCE instance = null;
			if(ValueUsageType.SYSTEM.equals(index.getUsageType()))
				instance = getBySystemIdentifier(klass, index.getValue());
			else if(ValueUsageType.BUSINESS.equals(index.getUsageType()))
				instance = getByBusinessIdentifier(klass, index.getValue());
			
			if(instance == null)
				continue;
			if(instances ==  null)
				instances = new ArrayList<>();
			instances.add(instance);
		}
		return instances;
	}
	
	static <INSTANCE> Collection<INSTANCE> getByIdentifiers(Class<INSTANCE> klass,Identifier...identifiers) {
		if(klass == null || ArrayHelper.isEmpty(identifiers))
			return null;
		return getByIdentifiers(klass,CollectionHelper.listOf(identifiers));
	}
	
	static <INSTANCE> INSTANCE getByIdentifier(Class<INSTANCE> klass,Identifier identifier) {
		if(klass == null || identifier == null)
			return null;
		return CollectionHelper.getFirst(getByIdentifiers(klass,identifier));
	}
	
	static <INSTANCE> Collection<INSTANCE> getIdentifiedFromIdentifiers(Class<INSTANCE> klass,Collection<?> collection) {
		if(CollectionHelper.isEmpty(collection))
			return null;
		Collection<Identifier> identifiers = FieldHelper.readIdentifiers(collection);
		if(CollectionHelper.isEmpty(identifiers))
			return null;
		Collection<INSTANCE> instances = getByIdentifiers(klass, identifiers);
		if(CollectionHelper.isEmpty(instances))
			return null;
		return instances;
	}
	
	static String __getMethodNameFromFieldName__(String fieldName,String prefix) {
		String methodName = null;
		if(fieldName!=null && fieldName.length()>0) {
			methodName = prefix+StringUtils.substring(fieldName, 0,1).toUpperCase()+StringUtils.substring(fieldName, 1);
		}
		return methodName;
	}
	
	static String getMethodGetterNameFromFieldName(String fieldName) {
		String methodName = null;
		if(fieldName!=null && fieldName.length()>0) {
			methodName = StringUtils.substring(fieldName, 0,1).toUpperCase()+StringUtils.substring(fieldName, 1);
		}
		return methodName;
	}
	
	static String getMethodSetterNameFromFieldName(String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	static Object executeMethodGetter(Object object, String fieldName) {
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

	static void executeMethodSetter(Object object, String fieldName, Object value) {
		if(object != null  && fieldName!=null) {
			String methodName = __getMethodNameFromFieldName__(fieldName, METHOD_SETTER_PREFIX);
			try {
				value = MethodUtils.invokeMethod(object, methodName,value);
			} catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}
	}
	
	static void copy(Object source,Object destination,Map<String,String> fieldsNames) {
		if(source == null || destination == null || MapHelper.isEmpty(fieldsNames))
			return;	
		for(Map.Entry<String, String> entry : fieldsNames.entrySet()) {
			Field sourceField = FieldHelper.getByName(source.getClass(), entry.getKey());
			if(sourceField == null)
				throw new RuntimeException("field "+source.getClass()+"."+entry.getKey()+" not found");
			Field destinationField = FieldHelper.getByName(destination.getClass(), entry.getValue());
			if(destinationField == null)
				throw new RuntimeException("field "+destination.getClass()+"."+entry.getValue()+" not found");			
			Object sourceFieldValue = FieldHelper.read(source, sourceField);
			FieldHelper.copy(source,sourceField,sourceFieldValue,destination,destinationField);
		}
	}
	
	static void copy(Object source,Object destination,Collection<String> fieldsNames) {
		if(source == null || destination == null || CollectionHelper.isEmpty(fieldsNames))
			return;	
		Map<String,String> map = new LinkedHashMap<>();
		for(String index : fieldsNames)
			map.put(index, index);
		copy(source, destination, map);
	}
	
	static <T> T build(Class<T> klass,Object object,Map<String,String> fieldsNames) {
		if(klass == null || object == null)
			return null;
		T instance = ClassHelper.instanciate(klass);
		copy(object, instance, fieldsNames);
		return instance;
	}
	
	static <T> T build(Class<T> klass,Object object,Collection<String> fieldsNames) {
		if(klass == null || object == null)
			return null;
		T instance = ClassHelper.instanciate(klass);
		copy(object, instance, fieldsNames);
		return instance;
	}
	
	static Boolean isPersistable(Object instance) {
		if(instance == null)
			return Boolean.FALSE;
		return ClassHelper.isPersistable(instance.getClass());
	}
	
	/**/
	
	String METHOD_GETTER_PREFIX = "get";
	String METHOD_SETTER_PREFIX = "set";
	
	List<Object> CONSTANTS = new ArrayList<>(1);
	Integer CONSTANT_INSTANCE_GETTER_INDEX = 0;
}
