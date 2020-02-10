package org.cyk.utility.__kernel__.object;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.string.StringHelper;

public interface Configurator<OBJECT> {

	default Map<Object,Object> getDefaultArguments() {
		return null;
	}
	
	void configure(OBJECT object,Map<Object,Object> arguments);
	
	default void configure(OBJECT object) {
		if(object == null)
			return;
		Map<Object,Object> arguments = getDefaultArguments();
		configure(object, arguments);
	}
	
	/**/
	
	static <OBJECT> Configurator<OBJECT> get(Class<OBJECT> klass) {
		if(klass == null)
			return null;
		@SuppressWarnings("unchecked")
		Configurator<OBJECT> configurator = (Configurator<OBJECT>) MAP.get(klass);
		if(configurator == null) {
			LogHelper.logWarning(klass+" does not have a configurator yet.", Configurator.class);
		}
		return configurator;
	}
	
	static <OBJECT> void set(Class<OBJECT> klass,Configurator<OBJECT> configurator) {
		if(klass == null)
			return;
		MAP.put(klass, configurator);
		if(configurator == null) {
			LogHelper.logWarning(klass+" does have its configurator set to null.", Configurator.class);
		}		
	}
	
	static void clear() {
		MAP.clear();
	}
	
	Map<Class<?>,Configurator<?>> MAP = new HashMap<>();
	
	/**/
	
	public static abstract class AbstractImpl<OBJECT> implements Configurator<OBJECT> {
		
		protected Collection<String> __fieldsNames__;
		
		@Override
		public void configure(OBJECT object, Map<Object, Object> arguments) {
			Collection<String> fieldsNames = __getFieldsNames__();
			if(CollectionHelper.isNotEmpty(fieldsNames))
				for(String fieldName : fieldsNames)
					__set__(object, fieldName, arguments);
		}
		
		protected abstract Class<OBJECT> __getClass__();
		
		protected Collection<String> __getFieldsNames__() {
			if(__fieldsNames__ == null) {
				__fieldsNames__ = new ArrayList<>();
				Class<OBJECT> klass = __getClass__();
				if(klass == null)
					return null;
				Collection<Field> fields = FieldHelper.filter(klass, "^FIELD", null);
				if(CollectionHelper.isEmpty(fields))
					return null;				
				for(Field field : fields)
					__fieldsNames__.add((String) FieldHelper.readStatic(field));
			}
			return __fieldsNames__;
		}
		
		protected void __set__(OBJECT object,String fieldName, Map<Object, Object> arguments,String key) {
			if(StringHelper.isBlank(fieldName))
				return;
			if(StringHelper.isBlank(key))
				key = fieldName;
			FieldHelper.write(object, fieldName, MapHelper.readByKey(arguments, key));
		}
		
		protected void __set__(OBJECT object,String fieldName, Map<Object, Object> arguments) {
			if(StringHelper.isBlank(fieldName))
				return;
			__set__(object, fieldName, arguments, fieldName);
		}
	}
}
