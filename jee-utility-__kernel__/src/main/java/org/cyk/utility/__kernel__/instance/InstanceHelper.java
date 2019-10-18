package org.cyk.utility.__kernel__.instance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.value.Identifier;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueUsageType;

public interface InstanceHelper {

	static <INSTANCE> INSTANCE getByIdentifier(Class<INSTANCE> klass,Object identifier,ValueUsageType valueUsageType,InstanceGetter getter) {
		if(klass == null || identifier == null)
			return null;
		if(getter == null)
			getter = InstanceGetter.getInstance();
		return getter.getByIdentifier(klass, identifier,valueUsageType);
	}
	
	static <INSTANCE> INSTANCE getBySystemIdentifier(Class<INSTANCE> klass,Object identifier,InstanceGetter getter) {
		if(klass == null || identifier == null)
			return null;
		if(getter == null)
			getter = InstanceGetter.getInstance();
		return getter.getBySystemIdentifier(klass, identifier);
	}
	
	static <INSTANCE> INSTANCE getBySystemIdentifier(Class<INSTANCE> klass,Object identifier) {
		if(klass == null || identifier == null)
			return null;
		return getBySystemIdentifier(klass, identifier,InstanceGetter.getInstance());
	}
	
	static <INSTANCE> INSTANCE getByBusinessIdentifier(Class<INSTANCE> klass,Object identifier,InstanceGetter getter) {
		if(klass == null || identifier == null)
			return null;
		if(getter == null)
			getter = InstanceGetter.getInstance();
		return getter.getByBusinessIdentifier(klass, identifier);
	}
	
	static <INSTANCE> INSTANCE getByBusinessIdentifier(Class<INSTANCE> klass,Object identifier) {
		if(klass == null || identifier == null)
			return null;
		return getByBusinessIdentifier(klass, identifier,InstanceGetter.getInstance());
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
	
	static void copy(Object source,Object destination,Map<String,String> fieldsNames,InstanceCopier copier) {
		if(source == null || destination == null || MapHelper.isEmpty(fieldsNames))
			return;
		if(copier == null)
			copier = InstanceCopier.getInstance();
		copier.copy(source, destination, fieldsNames);
	}
	
	static void copy(Object source,Object destination,Map<String,String> fieldsNames) {
		if(source == null || destination == null || MapHelper.isEmpty(fieldsNames))
			return;
		copy(source, destination, fieldsNames,InstanceCopier.getInstance());
	}
	
	static void copy(Object source,Object destination,Collection<String> fieldsNames,InstanceCopier copier) {
		if(source == null || destination == null || CollectionHelper.isEmpty(fieldsNames))
			return;	
		Map<String,String> map = new LinkedHashMap<>();
		for(String index : fieldsNames)
			map.put(index, index);
		copy(source, destination, map,copier);
	}
	
	static void copy(Object source,Object destination,Collection<String> fieldsNames) {
		if(source == null || destination == null || CollectionHelper.isEmpty(fieldsNames))
			return;	
		copy(source, destination, fieldsNames,InstanceCopier.getInstance());
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
		Object identifier = FieldHelper.readSystemIdentifier(instance);
		if(identifier != null)
			return Boolean.FALSE;
		return ClassHelper.isPersistable(instance.getClass());
	}
	
	static Boolean isPersisted(Object instance) {
		if(instance == null)
			return Boolean.FALSE;
		return !isPersistable(instance);
	}
	
	/**/
	
	String METHOD_GETTER_PREFIX = "get";
	String METHOD_SETTER_PREFIX = "set";
	
	Value INSTANCE_GETTER = DependencyInjection.inject(Value.class);
}
