package org.cyk.utility.__kernel__.map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface CollectionOfMapsStringStringBuilder {
	
	<T> Collection<Map<String,String>> build(Class<T> klass,Arguments<T> arguments);
	
	public static abstract class AbstractImpl extends AbstractObject implements CollectionOfMapsStringStringBuilder,Serializable {
		
		@Override
		public <T> Collection<Map<String, String>> build(Class<T> klass, Arguments<T> arguments) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("klass", klass);
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("arguments", arguments);
			if(CollectionHelper.isEmpty(arguments.collection))
				return arguments.emptyValue;
			if(MapHelper.isEmpty(arguments.fieldsNames))
				return arguments.emptyValue;
			return __build__(klass, arguments.collection,arguments.fieldsNames);
		}
		
		protected <T> Collection<Map<String, String>> __build__(Class<T> klass, Collection<T> collection,Map<String,String> fieldsNames) {			
			return collection.stream()
					.filter(value -> value != null)
					.map(value -> __build__(klass, value,fieldsNames))
					.filter(map -> map != null)
					.collect(Collectors.toList());
		}
		
		protected <T> Map<String, String> __build__(Class<T> klass, T value,Map<String,String> fieldsNames) {
			Map<String,String> map = new LinkedHashMap<>();
			fieldsNames.entrySet().forEach(fieldName -> {
				add(klass, value, map, fieldName);
			});
			return map;
		}
		
		protected <T> void add(Class<T> klass, T value,Map<String, String> map,Map.Entry<String,String> fieldName) {
			Object fieldValue = FieldHelper.read(value, fieldName.getKey());
			if(fieldValue == null)
				return;
			map.put(fieldName.getValue(), fieldValue.toString());
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments<T> implements Serializable {
		
		private Collection<T> collection;
		private Map<String,String> fieldsNames;
		private Collection<Map<String,String>> emptyValue;
		
		public Map<String,String> getFieldsNames(Boolean injectIfNull) {
			if(fieldsNames == null && Boolean.TRUE.equals(injectIfNull))
				fieldsNames = new HashMap<>();
			return fieldsNames;
		}
		
		public Arguments<T> addFieldsNames(String...fieldsNames) {
			if(ArrayHelper.isEmpty(fieldsNames))
				return this;
			getFieldsNames(Boolean.TRUE).putAll(MapHelper.instantiateStringString(fieldsNames));
			return this;
		}
		
		public Arguments<T> setEmptyValueAsArrayList() {
			setEmptyValue(EMPTY_AS_ARRAY_LIST);
			return this;
		}
	}
	
	/**/
	
	static CollectionOfMapsStringStringBuilder getInstance() {
		return Helper.getInstance(CollectionOfMapsStringStringBuilder.class, INSTANCE);
	}
	Value INSTANCE = new Value();
	
	Collection<Map<String,String>> EMPTY_AS_ARRAY_LIST = new ArrayList<Map<String,String>>();
}