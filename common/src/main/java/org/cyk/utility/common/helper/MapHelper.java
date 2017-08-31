package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Action;
import org.cyk.utility.common.Builder;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.InstanceHelper.Mapping;
import org.cyk.utility.common.helper.MapHelper.Stringifier.Entry.OutputStrategy;
import org.cyk.utility.common.helper.MapHelper.Stringifier.Entry.InputStrategy;
import org.hsqldb.lib.HashSet;

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
	
	public <KEY,VALUE> Map<KEY,VALUE> getByKeyValue(Object...objects){
		Map<KEY,VALUE> map = new LinkedHashMap<>();
		addKeyValue(map, objects);
		return map;
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
	
	/*public static interface Builder<KEY,VALUE> extends org.cyk.utility.common.Builder<INPUT, OUTPUT> {
		
	}*/
	
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

	public static interface Stringifier extends Builder.Stringifier<java.util.Map<?, ?>> {
		
		MapHelper.Stringifier.Entry getEntryStringifier();
		MapHelper.Stringifier setEntryStringifier(MapHelper.Stringifier.Entry entryStringifier);
		
		String getSeparator();
		MapHelper.Stringifier setSeparator(String separator);
		
		Set<Object> getEncodedKeys();
		MapHelper.Stringifier setEncodedKeys(Set<Object> encodedKeys);
		MapHelper.Stringifier addEncodedKeys(Object...encodedKeys);
		
		String getEncodedKeyName();
		MapHelper.Stringifier setEncodedKeyName(String encodedKeyName);
		
		@Getter
		public static class Adapter extends Builder.Stringifier.Adapter.Default<java.util.Map<?, ?>> implements MapHelper.Stringifier,Serializable {
			private static final long serialVersionUID = 1L;

			protected MapHelper.Stringifier.Entry entryStringifier;
			protected String separator,encodedKeyName;
			protected Set<Object> encodedKeys;
			
			@SuppressWarnings("unchecked")
			public Adapter(java.util.Map<?, ?> input) {
				super((Class<java.util.Map<?, ?>>) ClassHelper.getInstance().getByName(java.util.Map.class), input);
			}
			
			@Override
			public org.cyk.utility.common.helper.MapHelper.Stringifier setEncodedKeys(Set<Object> encodedKeys) {
				return null;
			}
			
			@Override
			public org.cyk.utility.common.helper.MapHelper.Stringifier addEncodedKeys(Object...encodedKeys) {
				return null;
			}
			
			@Override
			public org.cyk.utility.common.helper.MapHelper.Stringifier setEntryStringifier(Entry entryStringifier) {
				return null;
			}
			
			@Override
			public org.cyk.utility.common.helper.MapHelper.Stringifier setSeparator(java.lang.String separator) {
				return null;
			}
			
			@Override
			public org.cyk.utility.common.helper.MapHelper.Stringifier setEncodedKeyName(java.lang.String encodedKeyName) {
				return null;
			}
			
			/**/
			
			public static class Default extends MapHelper.Stringifier.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

				public static String DEFAULT_SEPARATOR = Constant.CHARACTER_COMA.toString();
				public static String DEFAULT_ENCODED_KEY_NAME = "encoded";
				
				public Default(java.util.Map<?, ?> input) {
					super(input);
				}
				
				@Override
				public org.cyk.utility.common.helper.MapHelper.Stringifier setEntryStringifier(Entry entryStringifier) {
					this.entryStringifier = entryStringifier;
					return this;
				}
				
				@Override
				public org.cyk.utility.common.helper.MapHelper.Stringifier setSeparator(java.lang.String separator) {
					this.separator = separator;
					return this;
				}
				
				@Override
				public org.cyk.utility.common.helper.MapHelper.Stringifier setEncodedKeys(Set<Object> encodedKeys) {
					this.encodedKeys = encodedKeys;
					return this;
				}
				
				@Override
				public org.cyk.utility.common.helper.MapHelper.Stringifier addEncodedKeys(Object...encodedKeys) {
					if(!ArrayHelper.getInstance().isEmpty(encodedKeys)){
						if(this.encodedKeys == null)
							this.encodedKeys = new java.util.LinkedHashSet<>();
						this.encodedKeys.addAll(Arrays.asList(encodedKeys));
					}
					return this;
				}
				
				@Override
				public org.cyk.utility.common.helper.MapHelper.Stringifier setEncodedKeyName(java.lang.String encodedKeyName) {
					this.encodedKeyName = encodedKeyName;
					return this;
				}
				
				@Override
				protected java.lang.String __execute__() {
					Collection<String> entries = new ArrayList<>();
					String separator = InstanceHelper.getInstance().getIfNotNullElseDefault(getSeparator(), DEFAULT_SEPARATOR);
					MapHelper.Stringifier.Entry entryStringifier = getEntryStringifier();
					if(entryStringifier==null)
						entryStringifier = new MapHelper.Stringifier.Entry.Adapter.Default();
					Set<Object> encodedKeys = getEncodedKeys();
					for(java.util.Map.Entry<?, ?> entry : getInput().entrySet()){
						entryStringifier.setInputStrategy(null).setOutputStrategy(null).setIsEncoded(CollectionHelper.getInstance().contains(encodedKeys,entry.getKey())).setInput(entry);
						String entryString = entryStringifier.execute();
						if(!StringHelper.getInstance().isBlank(entryString))
							entries.add(entryString);
					}
					if(!CollectionHelper.getInstance().isEmpty(encodedKeys)){
						String encodedKeyName = InstanceHelper.getInstance().getIfNotNullElseDefault(getEncodedKeyName(), DEFAULT_ENCODED_KEY_NAME);
						entries.add(entryStringifier.setOutputStrategy(OutputStrategy.KEY_MANY_VALUES).setInputStrategy(InputStrategy.MANY).setIsEncoded(Boolean.FALSE).set(encodedKeyName, encodedKeys).execute());
					}
					return StringHelper.getInstance().concatenate(entries,separator);
				}
			}
		}
		
		public static interface Entry extends Builder.Stringifier<java.util.Map.Entry<?, ?>> {
			
			public static enum OutputStrategy{KEY_ONE_VALUE,KEY_MANY_VALUES}
			public static enum InputStrategy{ONE,ONE_MANY,MANY}
			
			InstanceHelper.Mapping getValueMapping();
			MapHelper.Stringifier.Entry setValueMapping(InstanceHelper.Mapping mapping);
			
			MapHelper.Stringifier.Entry set(Object key,Object value);
			
			Object getKey();
			MapHelper.Stringifier.Entry setKey(Object key);
			
			Object getValue();
			MapHelper.Stringifier.Entry setValue(Object value);
			
			InputStrategy getInputStrategy();
			MapHelper.Stringifier.Entry setInputStrategy(InputStrategy inputStrategy);
			
			OutputStrategy getOutputStrategy();
			MapHelper.Stringifier.Entry setOutputStrategy(OutputStrategy outputStrategy);
			
			Boolean isNullKey(Object key);
			Boolean isNullValue(Object value);
			
			String getKey(Object key);
			String getValue(Object value);
			Collection<Object> getValues(Object value);
			/*Object getValueToProcessed(Object value);
			Collection<Object> getValuesToProcessed(Collection<Object> values);
			String getValueAsString(Object value);
			*/
			
			String getValuesSeparator();
			MapHelper.Stringifier.Entry setValuesSeparator(String valuesSeparator);
			
			String getSeparator();
			MapHelper.Stringifier.Entry setSeparator(String separator);
			
			String getKeyValuesSeparator();
			MapHelper.Stringifier.Entry setKeyValuesSeparator(String keyValuesSeparator);
			
			/*String encode(Collection<Object> values);
			*/
			String getEncodingCharacterSet();
			MapHelper.Stringifier.Entry setEncodingCharacterSet(String encodingCharacterSet);
			/*
			Boolean getIsEncoded(Object key,Collection<Object> values);
			*/
			
			Boolean getIsEncoded();
			MapHelper.Stringifier.Entry setIsEncoded(Boolean isEncoded);
			
			String encode(Collection<Long> values,String outputCharacters);
			
			@Getter
			public static class Adapter extends Builder.Stringifier.Adapter.Default<java.util.Map.Entry<?, ?>> implements MapHelper.Stringifier.Entry,Serializable {
				private static final long serialVersionUID = 1L;

				protected Object key,value;
				protected InputStrategy inputStrategy;
				protected OutputStrategy outputStrategy;
				protected String encodingCharacterSet,separator,valuesSeparator,keyValuesSeparator;
				protected InstanceHelper.Mapping valueMapping;
				protected Boolean isEncoded;
				
				@SuppressWarnings("unchecked")
				public Adapter(java.util.Map.Entry<?, ?> input) {
					super((Class<java.util.Map.Entry<?, ?>>) ClassHelper.getInstance().getByName(java.util.Map.Entry.class), input);
					setIsInputRequired(Boolean.FALSE);
				}
				
				@Override
				public Entry setEncodingCharacterSet(java.lang.String encodingCharacterSet) {
					return null;
				}
				
				@Override
				public Entry setIsEncoded(Boolean isEncoded) {
					return null;
				}
				
				@Override
				public Entry setValueMapping(Mapping mapping) {
					return null;
				}
				
				@Override
				public Entry set(Object key, Object value) {
					return null;
				}
				
				@Override
				public Entry setKey(Object key) {
					return null;
				}
				
				@Override
				public Entry setValue(Object value) {
					return null;
				}
				
				@Override
				public Entry setOutputStrategy(OutputStrategy outputStrategy) {
					return null;
				}
				
				@Override
				public Boolean isNullKey(Object key) {
					return null;
				}
				
				@Override
				public Boolean isNullValue(Object value) {
					return null;
				}
				
				@Override
				public java.lang.String getKey(Object key) {
					return null;
				}
				
				@Override
				public java.lang.String getValue(Object value) {
					return null;
				}
				
				@Override
				public Entry setSeparator(java.lang.String separator) {
					return null;
				}
				
				@Override
				public Collection<Object> getValues(Object value) {
					return null;
				}
				
				@Override
				public Entry setInputStrategy(InputStrategy inputStrategy) {
					return null;
				}
				
				@Override
				public Entry setValuesSeparator(java.lang.String valuesSeparator) {
					return null;
				}
				
				@Override
				public Entry setKeyValuesSeparator(java.lang.String keyValuesSeparator) {
					return null;
				}
				
				@Override
				public java.lang.String encode(Collection<Long> values,String outputCharacters) {
					return null;
				}
				
				/**/
				
				public static class Default extends MapHelper.Stringifier.Entry.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;

					public static Class<? extends InstanceHelper.Mapping> GET_VALUE_CLASS;
					public static String DEFAULT_SEPARATOR = Constant.CHARACTER_EQUAL.toString();
					public static String DEFAULT_VALUES_SEPARATOR = Constant.CHARACTER_COMA.toString();
					public static String DEFAULT_KEY_VALUES_SEPARATOR = Constant.CHARACTER_COMA.toString();
					
					public Default(java.util.Map.Entry<?, ?> input) {
						super(input);
					}
					
					public Default() {
						this(null);
					}
					
					public Default(Object key,Object value) {
						this(null);
						setKey(key);
						setValue(value);
					}
					
					@Override
					public Entry setIsEncoded(Boolean isEncoded) {
						this.isEncoded = isEncoded;
						return this;
					}
					
					@Override
					public Entry setInput(java.util.Map.Entry<?, ?> input) {
						super.setInput(input);
						if(input!=null){
							__setInputStrategy__(input.getValue());
						}
						return this;
					}
					
					protected void __setInputStrategy__(Object value){
						if(inputStrategy==null){
							if(value instanceof Collection<?> || value instanceof Object[])
								setInputStrategy(InputStrategy.MANY);	
							else
								setInputStrategy(InputStrategy.ONE);	
						}
					}
					
					@Override
					public Entry set(Object key, Object value) {
						setKey(key);
						setValue(value);
						return this;
					}
					
					@Override
					public Entry setOutputStrategy(OutputStrategy outputStrategy) {
						this.outputStrategy = outputStrategy;
						return this;
					}
					
					@Override
					public Boolean isNullKey(Object key) {
						return key == null || (key instanceof String && StringHelper.getInstance().isBlank((CharSequence) key));
					}
					
					@Override
					public Boolean isNullValue(Object value) {
						return value == null || (value instanceof String && StringHelper.getInstance().isBlank((CharSequence) value));
					}
					
					@Override
					public java.lang.String getKey(Object key) {
						return key == null ? null : key.toString();
					}
					
					@Override
					public java.lang.String getValue(Object value) {
						return value == null ? null : value.toString();
					}
					
					@Override
					public Entry setSeparator(java.lang.String separator) {
						this.separator = separator;
						return this;
					}
					
					@Override
					public Entry setInputStrategy(InputStrategy inputStrategy) {
						this.inputStrategy = inputStrategy;
						return this;
					}
					
					@Override
					public Entry setValuesSeparator(java.lang.String valuesSeparator) {
						this.valuesSeparator = valuesSeparator;
						return this;
					}
					
					@Override
					public Entry setKeyValuesSeparator(java.lang.String keyValuesSeparator) {
						this.keyValuesSeparator = keyValuesSeparator;
						return this;
					}
					
					@Override
					public Entry setKey(Object key) {
						this.key = key;
						return this;
					}
					
					@Override
					public Entry setValue(Object value) {
						this.value = value;
						__setInputStrategy__(this.value);
						return this;
					}
					
					@Override
					public Collection<Object> getValues(Object value) {
						Collection<Object> collection = new ArrayList<>();
						InputStrategy inputStrategy = InstanceHelper.getInstance().getIfNotNullElseDefault(getInputStrategy(), InputStrategy.ONE);
						if(InputStrategy.ONE.equals(inputStrategy)){
							collection.add(value);
						}else if(InputStrategy.MANY.equals(inputStrategy)){
							if(value instanceof Collection){
								for(Object object : ((Collection<?>)value))
									//if(object!=null)
										collection.add(object);
							}else if(value instanceof Object[]){
								for(Object object : ((Object[])value))
									if(object!=null)
										collection.add(object);
							}
						}else if(InputStrategy.ONE_MANY.equals(inputStrategy)){
							if(value instanceof String){
								String[] values = StringUtils.split((java.lang.String) value, InstanceHelper.getInstance().getIfNotNullElseDefault(getValuesSeparator(), DEFAULT_VALUES_SEPARATOR));
								for(String object : values)
									if(!Boolean.TRUE.equals(StringHelper.getInstance().isBlank(object)))
										collection.add(object);
							}
						}
						return collection;
					}

					@Override
					protected java.lang.String __execute__() {
						java.util.Map.Entry<?,?> entry = getInput();
						Object key = getKey();
						if(key==null && entry!=null)
							key = entry.getKey();
						key = getKey(key);
						if(Boolean.TRUE.equals(isNullKey(key)))
							return null;
						
						Object value = getValue();
						if(value==null && entry!=null)
							value = entry.getValue();
						if(Boolean.TRUE.equals(isNullValue(value)))
							return null;
						
						InputStrategy inputStrategy = InstanceHelper.getInstance().getIfNotNullElseDefault(getInputStrategy(), InputStrategy.ONE);
						OutputStrategy outputStrategy = InstanceHelper.getInstance().getIfNotNullElseDefault(getOutputStrategy(), OutputStrategy.KEY_ONE_VALUE);
						
						InstanceHelper.Mapping valueMapping = getValueMapping();
						if(valueMapping==null)
							valueMapping = ClassHelper.getInstance().instanciateOne(Entry.Adapter.Default.GET_VALUE_CLASS == null 
							? InstanceHelper.Mapping.Adapter.Default.class : Entry.Adapter.Default.GET_VALUE_CLASS );
						if(InputStrategy.MANY.equals(inputStrategy)){
							Collection<Object> objects = new ArrayList<>();
							for(Object object : value instanceof Collection<?> ? (Collection<?>)value : CollectionHelper.getInstance().get((Object[])value))
								objects.add(valueMapping.setInput(object).execute());							
							value = objects;	
						}else {
							value = valueMapping.setInput(value).execute();
						}
						
						
						Collection<String> keyValues = new ArrayList<>();
						
						String keyValueSeparator = InstanceHelper.getInstance().getIfNotNullElseDefault(getSeparator(), DEFAULT_SEPARATOR);
						
						if(OutputStrategy.KEY_ONE_VALUE.equals(outputStrategy)){
							if(InputStrategy.ONE.equals(inputStrategy)){
								if(Boolean.TRUE.equals(getIsEncoded())){
									Collection<Long> longs = new ArrayList<>();								
									longs.add(value instanceof Number ? ((Number)value).longValue() : Long.parseLong(value.toString()));	
									value = encode(longs,  InstanceHelper.getInstance().getIfNotNullElseDefault(getEncodingCharacterSet(),NumberHelper.BASE_62_CHARACTERS));
								}
							}else if(InputStrategy.MANY.equals(inputStrategy) || InputStrategy.ONE_MANY.equals(inputStrategy)){
								Collection<Object> objects = getValues(value);
								if(Boolean.TRUE.equals(getIsEncoded())){
									Collection<Long> longs = new ArrayList<>();								
									for(Object object : objects)
										if(object!=null)
											longs.add(object instanceof Number ? ((Number)object).longValue() : Long.parseLong(object.toString()));	
									value = encode(longs,  InstanceHelper.getInstance().getIfNotNullElseDefault(getEncodingCharacterSet(),NumberHelper.BASE_62_CHARACTERS));
								}else
									value = CollectionHelper.getInstance().concatenate(objects, InstanceHelper.getInstance().getIfNotNullElseDefault(getSeparator(), DEFAULT_KEY_VALUES_SEPARATOR));
							}else
								value = null;
							keyValues.add(key+keyValueSeparator+getValue(value));
						}else if(OutputStrategy.KEY_MANY_VALUES.equals(outputStrategy)){
							for(Object index : getValues(value)){
								if(Boolean.TRUE.equals(isNullValue(index)))
									continue;
								keyValues.add(key+keyValueSeparator+getValue(index));
							}
							
						}
						return StringHelper.getInstance().concatenate(keyValues, InstanceHelper.getInstance().getIfNotNullElseDefault(getKeyValuesSeparator(), DEFAULT_KEY_VALUES_SEPARATOR));
					}
					
					@Override
					public java.lang.String encode(Collection<Long> values,String outputCharacters) {
						return NumberHelper.getInstance().encode(values, outputCharacters);
					}
					
					@Override
					public Action<java.util.Map.Entry<?, ?>, java.lang.String> clear() {
						setInputStrategy(null);
						setOutputStrategy(null);
						setSeparator(null);
						return super.clear();
					}
					
				}
			}
			
			/**/
			
		}
		
	}
}
