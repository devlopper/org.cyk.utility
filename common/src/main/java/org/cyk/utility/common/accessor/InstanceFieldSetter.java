package org.cyk.utility.common.accessor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Action;
import org.cyk.utility.common.LogMessage;

import lombok.Getter;

@Deprecated
public interface InstanceFieldSetter<INPUT, OUTPUT> extends Action<INPUT, OUTPUT> {

	OUTPUT getInstance();
	InstanceFieldSetter<INPUT, OUTPUT> setInstance(OUTPUT instance);
	
	Set<String> getFieldNames();
	InstanceFieldSetter<INPUT, OUTPUT> setFieldNames(Set<String> fieldNames);
	
	/**/
	
	@Getter
	public static class Adapter<INPUT, OUTPUT> extends Action.Adapter.Default<INPUT, OUTPUT> implements InstanceFieldSetter<INPUT, OUTPUT>,Serializable {
		private static final long serialVersionUID = 3887225153998606760L;

		protected OUTPUT instance;
		protected Set<String> fieldNames = new LinkedHashSet<>();
		
		public Adapter(Class<INPUT> inputClass, INPUT input, Class<OUTPUT> outputClass) {
			super("Set", inputClass, input, outputClass, new LogMessage.Builder());
		}
		
		@Override
		public InstanceFieldSetter<INPUT, OUTPUT> setInstance(OUTPUT instance) {
			this.instance = instance;
			return this;
		}
		
		@Override
		public InstanceFieldSetter<INPUT, OUTPUT> setFieldNames(Set<String> fieldNames) {
			this.fieldNames = fieldNames;
			return this;
		}
		
		/**/
		
		public static class Default<INPUT, OUTPUT> extends InstanceFieldSetter.Adapter<INPUT, OUTPUT> implements Serializable {
			private static final long serialVersionUID = 3887225153998606760L;

			public Default(Class<INPUT> inputClass, INPUT input, Class<OUTPUT> outputClass) {
				super(inputClass, input, outputClass);
			}
			
		}
		
	}
	
	/**/
	
	@Deprecated
	public static interface OneDimensionObjectArray<OUTPUT> extends InstanceFieldSetter<Object[], OUTPUT> {
		
		Map<String,Integer> getFieldNamesIndexes();
		OneDimensionObjectArray<OUTPUT> setFieldNamesIndexes(Map<String,Integer> fieldNamesIndexes);
		
		Boolean isArrayValueAtProcessable(Integer index);
		
		Object getValue(Class<?> fieldType,Object value);
		
		OneDimensionObjectArray<OUTPUT> addFieldName(String name,Integer index);
		OneDimensionObjectArray<OUTPUT> addFieldName(String name);
		OneDimensionObjectArray<OUTPUT> addFieldNames(String...names);
		
		Object getKeyType(Object[] values);
		
		Object getKeyType();
		OneDimensionObjectArray<OUTPUT> setKeyType(Object keyType);
		
		Integer getKeyIndex(Object[] values);
		Integer getKeyIndex();
		OneDimensionObjectArray<OUTPUT> setKeyIndex(Integer keyIndex);
		
		Object getKeyValue(Object[] values);
		
		/**/
		
		@Getter
		public static class Adapter<OUTPUT> extends InstanceFieldSetter.Adapter.Default<Object[], OUTPUT> implements OneDimensionObjectArray<OUTPUT>,Serializable {
			private static final long serialVersionUID = 3887225153998606760L;

			protected Map<String,Integer> fieldNamesIndexes = new HashMap<>();
			protected Object keyType;
			protected Integer keyIndex;
			
			public Adapter(Object[] input, Class<OUTPUT> outputClass) {
				super(Object[].class, input, outputClass);
			}
			
			@Override
			public OneDimensionObjectArray<OUTPUT> setKeyIndex(Integer keyIndex) {
				return null;
			}
			
			@Override
			public OneDimensionObjectArray<OUTPUT> setKeyType(Object keyType) {
				return null;
			}
			
			@Override
			public Object getKeyType(Object[] values) {
				return null;
			}
			
			@Override
			public Integer getKeyIndex(Object[] values) {
				return null;
			}
			
			@Override
			public Object getKeyValue(Object[] values) {
				return null;
			}
			
			@Override
			public OneDimensionObjectArray<OUTPUT> setFieldNamesIndexes(Map<String, Integer> fieldNamesIndexes) {
				return null;
			}
			
			@Override
			public Boolean isArrayValueAtProcessable(Integer index) {
				return null;
			}
			
			@Override
			public Object getValue(Class<?> fieldType,Object value) {
				return null;
			}
			
			@Override
			public OneDimensionObjectArray<OUTPUT> addFieldName(String name, Integer index) {
				return null;
			}
			
			@Override
			public OneDimensionObjectArray<OUTPUT> addFieldName(String name) {
				return null;
			}
			
			@Override
			public OneDimensionObjectArray<OUTPUT> addFieldNames(String... names) {
				return null;
			}
			
			/**/
			
			public static class Default<OUTPUT> extends OneDimensionObjectArray.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 3887225153998606760L;

				public Default(Object[] input, Class<OUTPUT> outputClass) {
					super(input, outputClass);
				}
				
				public Default(Class<OUTPUT> outputClass) {
					super(new Object[]{}, outputClass);
				}
				
				@Override
				public OneDimensionObjectArray<OUTPUT> setKeyIndex(Integer keyIndex) {
					this.keyIndex = keyIndex;
					return this;
				}
				
				@Override
				public OneDimensionObjectArray<OUTPUT> setKeyType(Object keyType) {
					this.keyType = keyType;
					return this;
				}
				
				@Override
				public OneDimensionObjectArray<OUTPUT> setFieldNamesIndexes(Map<String, Integer> fieldNamesIndexes) {
					this.fieldNamesIndexes = fieldNamesIndexes;
					return this;
				}
				
				@Override
				public Boolean isArrayValueAtProcessable(Integer index) {
					return getInput()[index] != null && StringUtils.isNotBlank(getInput()[index].toString());
				}
				
				@Override
				public Object getValue(Class<?> fieldType,Object value) {
					return commonUtils.convertString(value.toString(), fieldType);
				}
				
				@Override
				public OneDimensionObjectArray<OUTPUT> addFieldName(String name, Integer index) {
					getFieldNames().add(name);
					getFieldNamesIndexes().put(name, index);
					return this;
				}
				
				@Override
				public OneDimensionObjectArray<OUTPUT> addFieldName(String name) {
					addFieldName(name, getFieldNames().size());
					return this;
				}
				
				@Override
				public OneDimensionObjectArray<OUTPUT> addFieldNames(String... names) {
					for(String name : names)
						addFieldName(name);
					return this;
				}
				
				@Override
				public Object getKeyValue(Object[] values) {
					return commonUtils.getValueAt(values, getKeyIndex(values));
				}
				
				@Override
				protected OUTPUT __execute__() {
					if(getInstance()==null){
						addLogMessageBuilderParameters(getLogMessageBuilder(),"instance","is null");
					}else{
						addLogMessageBuilderParameters(getLogMessageBuilder(),"length",getInput().length);
						for(String fieldName : getFieldNames()){
							try{
								Class<?> fieldType = PropertyUtils.getPropertyType(instance, fieldName);
								Integer index = getFieldNamesIndexes().get(fieldName);
								Boolean isArrayValueAtProcessable = isArrayValueAtProcessable(index);
								Object value = getInput()[getFieldNamesIndexes().get(fieldName)];
								addLogMessageBuilderParameters(getLogMessageBuilder(), "field name",fieldName);
								addLogMessageBuilderParameters(getLogMessageBuilder(),"index",index);
								addLogMessageBuilderParameters(getLogMessageBuilder(),"value",value);
								addLogMessageBuilderParameters(getLogMessageBuilder(),"processable",isArrayValueAtProcessable);
								if(Boolean.TRUE.equals(isArrayValueAtProcessable)){
									value = getValue(fieldType, value);
									if(Boolean.TRUE.equals(isShowFieldValueLogMessage(value)))
										addLogMessageBuilderParameters(getLogMessageBuilder(),"field value",value);
									PropertyUtils.setProperty(instance, fieldName, value);
								}
							}catch(Exception exception){
								logThrowable(exception);
							}
						}				
					}					
					return getInstance();
				}
			}
			
			protected Boolean isShowFieldValueLogMessage(Object value) {
				return Boolean.FALSE;
			}
			
			@Override
			protected Boolean isShowInputLogMessage(Object[] input) {
				return Boolean.FALSE;
			}
			
			@Override
			protected Boolean isShowOutputLogMessage(OUTPUT output) {
				return Boolean.FALSE;
			}
						
		}
		
		/**/
		
		public static final String KEY_TYPE_LOCAL = "local";
		public static final String KEY_TYPE_GLOBAL = "global";
	}
	
	public static interface TwoDimensionObjectArray<OUTPUT> extends InstanceFieldSetter<Object[][], Collection<OUTPUT>> {
		
		OneDimensionObjectArray<OUTPUT> getOneDimension();
		TwoDimensionObjectArray<OUTPUT> setOneDimension(OneDimensionObjectArray<OUTPUT> oneDimension);
	
		Set<Object> getExistingKeys();
		TwoDimensionObjectArray<OUTPUT> setExistingKeys(Set<Object> existingKeys);
		
		Boolean getIgnoreExistingKey(Object[] values,Object key,Object keyType);
		Boolean isInstanceKeyExist(Object[] values,Object key,Object keyType);
		OUTPUT getInstanceByKey(Object[] values,Object key,Object keyType);
		
		OUTPUT instanciate(Object[] values);
		
		OUTPUT getInstance(Object[] values);
		
		/**/
		
		@Getter
		public static class Adapter<OUTPUT> extends InstanceFieldSetter.Adapter.Default<Object[][], Collection<OUTPUT>> implements TwoDimensionObjectArray<OUTPUT>,Serializable {
			private static final long serialVersionUID = 3887225153998606760L;

			protected OneDimensionObjectArray<OUTPUT> oneDimension;
			protected Collection<OUTPUT> instances = new ArrayList<>();
			protected Set<Object> existingKeys = new HashSet<>();
			
			@SuppressWarnings("unchecked")
			public Adapter(Object[][] input,OneDimensionObjectArray<OUTPUT> oneDimension) {
				super(Object[][].class, input, null);
				try {
					setOutputClass((Class<Collection<OUTPUT>>) Class.forName(Collection.class.getName()));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				setOneDimension(oneDimension);
			}
			
			@Override
			public TwoDimensionObjectArray<OUTPUT> setOneDimension(OneDimensionObjectArray<OUTPUT> oneDimension) {
				this.oneDimension = oneDimension;
				return this;
			}
			
			@Override
			public TwoDimensionObjectArray<OUTPUT> setExistingKeys(Set<Object> exisitingKeys) {
				return null;
			}
			
			@Override
			public Boolean isInstanceKeyExist(Object[] values, Object key, Object keyType) {
				return null;
			}
			
			@Override
			public Boolean getIgnoreExistingKey(Object[] values, Object key, Object keyType) {
				return null;
			}
			
			@Override
			public OUTPUT getInstanceByKey(Object[] values, Object key, Object keyType) {
				return null;
			}
			
			@Override
			public OUTPUT getInstance(Object[] values) {
				return null;
			}
			
			@Override
			public OUTPUT instanciate(Object[] values) {
				return null;
			}
			
			/**/
			
			public static class Default<OUTPUT> extends TwoDimensionObjectArray.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 3887225153998606760L;

				protected Integer numberOfNullInstance=0,numberOfExisitingInstanceKey=0,numberOfIgnoredExistingKey=0;
				
				public Default(Object[][] input,OneDimensionObjectArray<OUTPUT> oneDimension) {
					super(input,oneDimension);
				}
				
				public Default(OneDimensionObjectArray<OUTPUT> oneDimension) {
					this(null,oneDimension);
				}
				
				@Override
				public Boolean isInstanceKeyExist(Object[] values, Object key, Object keyType) {
					if(key==null)
						return getInstanceByKey(values, key, keyType) != null;
					return getExistingKeys().contains(key);
				}
				
				@Override
				public Boolean getIgnoreExistingKey(Object[] values, Object key, Object keyType) {
					return getExistingKeys().contains(key);
				}
				
				@Override
				public OUTPUT instanciate(Object[] values) {
					return (OUTPUT) newInstance(getOneDimension().getOutputClass());
				}
				
				@Override
				public OUTPUT getInstance(Object[] values) {
					OUTPUT instance = null;
					Object key = getOneDimension().getKeyValue(values);
					Object type = getOneDimension().getKeyType(values);
					
					if(Boolean.TRUE.equals(isInstanceKeyExist(values, key, type))){
						numberOfExisitingInstanceKey++;
						if(Boolean.TRUE.equals(getIgnoreExistingKey(values, key, type))){
							numberOfIgnoredExistingKey++;
						}else
							instance = getInstanceByKey(values,key,type);
					}else
						instance = instanciate(values);
					return instance;
				}
					
				@Override
				protected Collection<OUTPUT> __execute__() {
					addLogMessageBuilderParameters(getLogMessageBuilder(), "existing keys",existingKeys.size());
					output = new ArrayList<>();
					for(Object[] values : getInput()){
						OUTPUT instance = getInstance(values);
						if(instance == null){
							
						}else{
							OUTPUT output = getOneDimension().setInstance(instance).setInput(values).execute();
							if(output!=null)
								getOutput().add(output);
						}
						
					}
					addLogMessageBuilderParameters(getLogMessageBuilder(), "numberOfExisitingInstanceKey",numberOfExisitingInstanceKey,"numberOfIgnoredExistingKey"
							,numberOfIgnoredExistingKey,"size",getOutput().size());
					return getOutput();
				}
				
				@Override
				protected Boolean isShowInputLogMessage(Object[][] input) {
					return Boolean.FALSE;
				}
				
				@Override
				protected Boolean isShowOuputClassLogMessage(Class<Collection<OUTPUT>> aClass) {
					return Boolean.FALSE;
				}
				
				@Override
				protected Boolean isShowOutputLogMessage(Collection<OUTPUT> output) {
					return Boolean.FALSE;
				}
			}
						
		}
		
	}
	
}
