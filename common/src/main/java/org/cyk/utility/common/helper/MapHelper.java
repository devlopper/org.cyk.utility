package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Action;

import lombok.Getter;

@Singleton
public class MapHelper extends AbstractHelper implements Serializable  {

	private static final long serialVersionUID = 1L;

	private static MapHelper INSTANCE;
	
	public static MapHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new MapHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	@SuppressWarnings("unchecked")
	public <KEY,VALUE> void addKeyValue(Map<KEY, VALUE> map,Object...objects){
		if(map == null || objects == null)
			return;
		for(int i = 0 ; i < objects.length ; i = i + 2){
			map.put((KEY)objects[i], (VALUE)objects[i+1]);
		}
	}
	
	public <KEY,VALUE> void add(Map<KEY, VALUE> map,KEY key,Collection<VALUE> values){
		if(map == null || key == null || values == null)
			return;
		for(VALUE value : values)
			map.put(key, value);
	}
		
	public <KEY,VALUE> void add(Map<KEY, VALUE> map,KEY key,@SuppressWarnings("unchecked") VALUE...values){
		if(map == null || key == null || values == null)
			return;
		add(map,key,Arrays.asList(values));
	}
	
	public <VALUE> VALUE get(Map<?, VALUE> map,Object key,Class<VALUE> valueClass){
		if(map == null || key == null || valueClass == null)
			return null;
		return map.get(key);
	}
	
	@SuppressWarnings("unchecked")
	public <VALUE> VALUE getStringValueAs(Class<VALUE> valueClass,Map<?, java.lang.String> map,Object key,String nullValue){
		if(map == null || key == null)
			return null;
		String value = new MapHelper.GetValue.String.Adapter.Default<java.lang.String>((Map<java.lang.String,java.lang.String>)map,java.lang.String.class)
				.setProperty(MapHelper.GetValue.PROPERTY_NAME_KEY, key)
				.setProperty(MapHelper.ContainsKey.PROPERTY_NAME_CASE_SENSITIVE, Boolean.FALSE)
				.setProperty(MapHelper.ContainsKey.PROPERTY_NAME_NULL_VALUE, nullValue)
				.execute();
		if(StringUtils.isNotBlank(value)){
			if(ClassHelper.getInstance().isNumber(valueClass))
				return NumberHelper.getInstance().get(valueClass, value);
		}
		return null;
	}
	
	/**/
	

	public static interface ContainsKey<KEY> extends Action<KEY, Boolean> {
		
		public static class Adapter<KEY> extends Action.Adapter.Default<KEY, Boolean> implements ContainsKey<KEY>,Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<KEY> inputClass, KEY input) {
				super("contains key", inputClass, input, Boolean.class);
			}
			
			public static class Default<KEY> extends ContainsKey.Adapter<KEY> implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public Default(Class<KEY> inputClass, KEY input) {
					super(inputClass, input);
				}
				
				@Override
				protected Boolean __execute__() {
					@SuppressWarnings("unchecked")
					Map<KEY,?> map = (Map<KEY, ?>) getProperty(PROPERTY_NAME_MAP);
					KEY key = getInput();
					if(Boolean.TRUE.equals(contains(map,key))){
						found(map, key);
						return Boolean.TRUE;
					}
					return Boolean.FALSE;
				}
				
				protected Boolean contains(Map<KEY,?> map,KEY key){
					return map.containsKey(key);
				}
				
				protected void found(Map<KEY,?> map,KEY key){
					setProperty(PROPERTY_NAME_KEY, key);
				}
			}
			
		}
		
		/**/
		
		public static interface String extends ContainsKey<java.lang.String> {
			
			public static class Adapter extends ContainsKey.Adapter.Default<java.lang.String> implements String,Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter(java.lang.String input) {
					super(java.lang.String.class, input);
				}
				
				public static class Default extends String.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
					public Default(java.lang.String input) {
						super(input);
					}
					
					@Override
					protected Boolean __execute__() {
						@SuppressWarnings("unchecked")
						Map<java.lang.String,Object> map = (Map<java.lang.String, Object>) getProperty(PROPERTY_NAME_MAP);
						Boolean caseSensitive = (Boolean) getProperty(PROPERTY_NAME_CASE_SENSITIVE);
						if(caseSensitive==null || Boolean.TRUE.equals(caseSensitive)){
							found(map, getInput());
							return map.containsKey(getInput());
						}else for(Entry<java.lang.String, Object> entry : map.entrySet()){
							if(entry.getKey().equalsIgnoreCase(getInput())){
								found(map, entry.getKey());
								return Boolean.TRUE;
							}
						}
						return Boolean.FALSE;
					}
				}
			}
		}
	}
	
	public static interface GetValue<KEY,VALUE> extends Action<Map<KEY,VALUE>, VALUE> {
		
		ContainsKey<KEY> getContainsKey();
		GetValue<KEY,VALUE> setContainsKey(ContainsKey<KEY> containsKey);
		
		@Getter
		public static class Adapter<KEY,VALUE> extends Action.Adapter.Default<Map<KEY,VALUE>, VALUE> implements GetValue<KEY,VALUE>,Serializable {
			private static final long serialVersionUID = 1L;

			protected ContainsKey<KEY> containsKey;
			
			@SuppressWarnings("unchecked")
			public Adapter(Map<KEY, VALUE> input,Class<VALUE> outputClass) {
				super("get value", (Class<Map<KEY, VALUE>>) ClassHelper.getInstance().getByName(Map.class), input, outputClass);
			}
			
			@Override
			public GetValue<KEY, VALUE> setContainsKey(ContainsKey<KEY> containsKey) {
				return null;
			}
			
			public static class Default<KEY,VALUE> extends GetValue.Adapter<KEY,VALUE> implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public Default(Map<KEY, VALUE> input,Class<VALUE> outputClass) {
					super(input, outputClass);
				}
				
				@Override
				public GetValue<KEY, VALUE> setContainsKey(ContainsKey<KEY> containsKey) {
					this.containsKey = containsKey;
					return this;
				}
				
				@SuppressWarnings("unchecked")
				@Override
				protected VALUE __execute__() {
					Map<KEY,VALUE> map = getInput();
					KEY key = (KEY) getProperty(PROPERTY_NAME_KEY);
					ContainsKey<KEY> containsKey = getContainsKey();
					if(containsKey==null)
						containsKey = instanciateContainsKey(key);
					containsKey.setProperty(PROPERTY_NAME_MAP, map);
					if(containsKey.getProperty(PROPERTY_NAME_CASE_SENSITIVE)==null && getProperty(PROPERTY_NAME_CASE_SENSITIVE)!=null)
						containsKey.setProperty(PROPERTY_NAME_CASE_SENSITIVE, getProperty(PROPERTY_NAME_CASE_SENSITIVE));
					
					if(Boolean.TRUE.equals(containsKey.execute()))
						return map.get(containsKey.getProperty(PROPERTY_NAME_KEY));
					VALUE nullValue = (VALUE) getProperty(PROPERTY_NAME_NULL_VALUE);
					addLoggingMessageBuilderParameters("key <<"+key+">> not defined , null value <<"+nullValue+">> will be used");
					return nullValue;
				}
				
				@SuppressWarnings("unchecked")
				protected ContainsKey<KEY> instanciateContainsKey(KEY key){
					return new ContainsKey.Adapter.Default<KEY>((Class<KEY>) key.getClass(),key);
				}
				
			}
			
		}
		
		/**/
		
		public static interface String<VALUE> extends GetValue<java.lang.String,VALUE> {
			
			public static class Adapter<VALUE> extends GetValue.Adapter.Default<java.lang.String,VALUE> implements String<VALUE>,Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter(Map<java.lang.String, VALUE> input, Class<VALUE> outputClass) {
					super(input, outputClass);
				}
				
				public static class Default<VALUE> extends String.Adapter<VALUE> implements Serializable {
					private static final long serialVersionUID = 1L;
					
					public Default(Map<java.lang.String, VALUE> input, Class<VALUE> outputClass) {
						super(input, outputClass);
					}
					
					@Override
					protected ContainsKey<java.lang.String> instanciateContainsKey(java.lang.String key) {
						return new ContainsKey.String.Adapter.Default(key);
					}
					
				}
				
			}
			
		}
		
	}

}
