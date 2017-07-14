package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.Properties;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.cyk.utility.common.Action;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.helper.ArrayHelper.Element;

import lombok.Getter;

@Singleton
public class InstanceHelper extends AbstractHelper implements Serializable  {

	private static final long serialVersionUID = 1L;

	public Object getIdentifier(final Object instance){
		return listenerUtils.getObject(Listener.COLLECTION, new ListenerUtils.ObjectMethod<Listener>() {
			@Override
			public Object execute(Listener listener) {
				return listener.getIdentifier(instance);
			}
			
			@Override
			public Object getNullValue() {
				if(instance!=null)
					return instance.toString();
				return super.getNullValue();
			}
		});
	}
	
	public <T> T getIfNotNullElseDefault(Class<T> valueClass,T value,T defaultValue){
		return value == null ? defaultValue : value;
	}
	
	public <T> T getIfNotNullElseDefault(T value,T defaultValue){
		return value == null ? defaultValue : value;
	}

	public String getMethodName(MethodType type,String suffix){
		return type.name().toLowerCase()+StringUtils.substring(suffix, 0,1).toUpperCase()+StringUtils.substring(suffix, 1).toLowerCase();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T callGetMethod(Object instance,Class<T> resultClass,String name){
		try {
			return (T) MethodUtils.invokeMethod(instance, getMethodName(MethodType.GET, name));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public <T> Collection<T> callGetMethod(Collection<?> instances,Class<T> resultClass,String name){
		Collection<T> result = new ArrayList<>();
		for(Object instance : instances){
			T value = (T) callGetMethod(instance, resultClass, name);
			result.add(value);
		}
		return result;
	}

	@Deprecated
	public <T> T instanciateOne(Class<T> aClass){
		try {
			return aClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**/
	
	public static interface Setter<INSTANCE> extends Action<INSTANCE, INSTANCE> {
		
		ListenerHelper.Executor.Function.Adapter.Default.Object<ProcessValue> getProcessValuesExecutor();
		Setter<INSTANCE> setProcessValuesExecutor(ListenerHelper.Executor.Function.Adapter.Default.Object<ProcessValue> processValuesExecutor);
		
		@Getter
		public static class Adapter<INSTANCE> extends Action.Adapter.Default<INSTANCE, INSTANCE> implements Setter<INSTANCE>,Serializable {
			private static final long serialVersionUID = 1L;
			
			protected ListenerHelper.Executor.Function.Adapter.Default.Object<ProcessValue> processValuesExecutor;
			
			public Adapter(Class<INSTANCE> instanceClass, INSTANCE instance) {
				super("set", instanceClass, instance, instanceClass);
			}
			
			@Override
			public Setter<INSTANCE> setProcessValuesExecutor(ListenerHelper.Executor.Function.Adapter.Default.Object<ProcessValue> processValuesExecutor) {
				return null;
			}
			
			/**/
			
			public static class Default<INSTANCE> extends Setter.Adapter<INSTANCE> implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public Default(Class<INSTANCE> instanceClass, INSTANCE instance) {
					super(instanceClass, instance);
				}
				
				public Default(Class<INSTANCE> instanceClass) {
					super(instanceClass, null);
				}
				
				@Override
				public Setter<INSTANCE> setProcessValuesExecutor(ListenerHelper.Executor.Function.Adapter.Default.Object<ProcessValue> processValuesExecutor) {
					this.processValuesExecutor = processValuesExecutor;
					return this;
				}
				
				@Override
				protected INSTANCE __execute__() {
					INSTANCE instance = getInput();
					Properties properties = getProperties();
					if(properties!=null){
						FieldHelper fieldHelper = new FieldHelper();
						CollectionHelper collectionHelper = new CollectionHelper();
						
						ListenerHelper.Executor.Function.Adapter.Default.Object<ProcessValue> valueProcessorExecutor = getProcessValuesExecutor();
						if(valueProcessorExecutor==null){
							valueProcessorExecutor = new ListenerHelper.Executor.Function.Adapter.Default.Object<ProcessValue>();
							valueProcessorExecutor.setResultMethod(ProcessValue.Adapter.Default.RESULT_METHOD);
							valueProcessorExecutor.setInput((Collection<ProcessValue>) new ClassHelper().instanciateMany(ProcessValue.class
									,collectionHelper.isEmpty(ProcessValue.CLASSES) ? Arrays.asList(ProcessValue.Adapter.Default.class) : ProcessValue.CLASSES));
						}
						
						for(Entry<Object, Object> entry : properties.entrySet()){
							valueProcessorExecutor.setManyProperties(ProcessValue.PROPERTY_INSTANCE,instance,ProcessValue.PROPERTY_FIELD_NAME,(String)entry.getKey()
									,ProcessValue.PROPERTY_VALUE,entry.getValue());
							setFieldValue(fieldHelper, instance, (String)entry.getKey(), collectionHelper.isEmpty(valueProcessorExecutor.getInput()) ? entry.getValue() : valueProcessorExecutor.execute());
						}
					}
					return instance;
				}
				
				protected void setFieldValue(FieldHelper fieldHelper,INSTANCE instance,String fieldName,Object value){
					fieldHelper.set(instance, value,fieldName);
				}
			}
		}
		
		/**/
		
		public static interface ProcessValue extends Action<Object, Object> {
			
			String PROPERTY_INSTANCE = "instance";
			String PROPERTY_FIELD_NAME = "fieldName";
			String PROPERTY_VALUE = "value";
			
			Collection<Class<? extends ProcessValue>> CLASSES = new ArrayList<>();
			
			public static class Adapter extends Action.Adapter.Default<Object, Object> implements ProcessValue,Serializable {
				private static final long serialVersionUID = 1L;
				
				public Adapter(Object value) {
					super("process", Object.class, value, Object.class);
				}
				
				public static class Default extends ProcessValue.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
					public static ListenerHelper.Executor.ResultMethod<Object, ProcessValue> RESULT_METHOD = new ResultMethod();
					
					public Default(Object value) {
						super(value);
					}
					
					public Default() {
						this(null);
					}
					
					
				}
			}
			
			public static class ResultMethod extends ListenerHelper.Executor.ResultMethod.Adapter.Default.Object<ProcessValue> {
				private static final long serialVersionUID = 1L;

				@Override
				protected java.lang.Object __execute__() {
					java.lang.Object value = getProperty(ProcessValue.PROPERTY_VALUE);
					if(value!=null){
						if(value instanceof java.lang.String){
							ClassHelper classHelper = new ClassHelper();
							Field field = new FieldHelper().get(getProperty(PROPERTY_INSTANCE).getClass(), (java.lang.String)getProperty(PROPERTY_FIELD_NAME));
							Class<?> wrapperClass = classHelper.getWrapper(field.getType());
							if(!wrapperClass.isAssignableFrom(value.getClass())){
								if(new ClassHelper().isNumber(wrapperClass)){
									value = new NumberHelper().get(wrapperClass,(java.lang.String)value);
								}
							}	
						}
					}
					return value;
				}
			}
		}
		
		/**/
		
		
	}
	
	public static interface Builder<INPUT,INSTANCE> extends org.cyk.utility.common.Builder<INPUT,INSTANCE> {
		
		Setter<INSTANCE> getSetter();
		Builder<INPUT,INSTANCE> setSetter(Setter<INSTANCE> setter);
		
		@Getter
		public static class Adapter<INPUT, INSTANCE> extends org.cyk.utility.common.Builder.Adapter.Default<INPUT, INSTANCE> implements Builder<INPUT, INSTANCE>,Serializable {
			private static final long serialVersionUID = 1L;
			
			protected Setter<INSTANCE> setter;
			
			public Adapter(Class<INPUT> inputClass, INPUT input, Class<INSTANCE> outputClass) {
				super(inputClass, input, outputClass);
			}
			
			@Override
			public Builder<INPUT, INSTANCE> setSetter(Setter<INSTANCE> setter) {
				return null;
			}
			
			public static class Default<INPUT,INSTANCE> extends Builder.Adapter<INPUT, INSTANCE> implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public Default(Class<INPUT> inputClass, INPUT input, Class<INSTANCE> outputClass) {
					super(inputClass, input, outputClass);
				}
				
				@Override
				public Builder<INPUT, INSTANCE> setSetter(Setter<INSTANCE> setter) {
					this.setter = setter;
					return this;
				}
				
			}
		}
		
		/**/
		
		public static interface OneDimensionArray<INSTANCE> extends Builder<Object[],INSTANCE> {
			/*
			Boolean isArrayValueAtProcessable(Integer index);
		
			Object getKeyType(Object[] values);
			
			Object getKeyType();
			OneDimensionArray<INSTANCE> setKeyType(Object keyType);
			
			Integer getKeyIndex(Object[] values);
			Integer getKeyIndex();
			OneDimensionArray<INSTANCE> setKeyIndex(Integer keyIndex);
			
			Object getKeyValue(Object[] values);
			*/
			public static class Adapter<INSTANCE> extends Builder.Adapter.Default<Object[], INSTANCE> implements OneDimensionArray<INSTANCE>,Serializable {
				private static final long serialVersionUID = 1L;
				
				public Adapter(Object[] array, Class<INSTANCE> outputClass) {
					super(Object[].class, array, outputClass);
				}
				
				public static class Default<INSTANCE> extends OneDimensionArray.Adapter<INSTANCE> implements Serializable {
					private static final long serialVersionUID = 1L;
					
					public Default(Object[] array, Class<INSTANCE> outputClass) {
						super(array, outputClass);
					}
					
					public Default(Class<INSTANCE> outputClass) {
						super(null, outputClass);
					}
					
					@Override
					protected INSTANCE __execute__() {
						INSTANCE instance = new ClassHelper().instanciateOne(getOutputClass());
						Object[] parametersArray = new CollectionHelper().getArray(getParameters());
						if(parametersArray!=null){
							Setter<INSTANCE> setter = getSetter();
							if(setter==null){
								setter = new Setter.Adapter.Default<INSTANCE>(getOutputClass());
							}
							setter.setInput(instance);
							Object[] values = getInput();
							Collection<Object> objects = new ArrayList<>();
							for(int i = 0 ; i < parametersArray.length ; i++){
								if(parametersArray[i] instanceof ArrayHelper.Element<?>){
									ArrayHelper.Element<?> element = (Element<?>) parametersArray[i];
									objects.add(element.getInstance());
									objects.add(values[element.getIndex()]);	
								}else
									throw new RuntimeException("parameter class should be a sub type of ArrayHelper.Element");
								
							}
							setter.setManyProperties(objects.toArray()).execute();
						}
						return instance;
					}
					
					@Override
					public OneDimensionArray<INSTANCE> addManyParameters(Object... parameters) {
						return (OneDimensionArray<INSTANCE>) super.addManyParameters(parameters);
					}
					
					@Override
					public OneDimensionArray<INSTANCE> addParameterArrayElementString(String... strings) {
						return (OneDimensionArray<INSTANCE>) super.addParameterArrayElementString(strings);
					}
				}
			}
		}
		
		public static interface TwoDimensionArray<INSTANCE> extends Builder<Object[][],Collection<INSTANCE>> {
			
			OneDimensionArray<INSTANCE> getOneDimensionArray();
			TwoDimensionArray<INSTANCE> setOneDimensionArray(OneDimensionArray<INSTANCE> oneDimensionArray);
			
			@Getter
			public static class Adapter<INSTANCE> extends Builder.Adapter.Default<Object[][],Collection<INSTANCE>> implements TwoDimensionArray<INSTANCE>,Serializable {
				private static final long serialVersionUID = 1L;
				
				protected OneDimensionArray<INSTANCE> oneDimensionArray;
				
				@SuppressWarnings("unchecked")
				public Adapter(Object[][] array) {
					super(Object[][].class, array, (Class<Collection<INSTANCE>>) new ClassHelper().getByName(Collection.class.getName()));
				}
				
				@Override
				public TwoDimensionArray<INSTANCE> setOneDimensionArray(OneDimensionArray<INSTANCE> oneDimensionArray) {
					return null;
				}
				
				public static class Default<INSTANCE> extends TwoDimensionArray.Adapter<INSTANCE> implements Serializable {
					private static final long serialVersionUID = 1L;
					
					public Default(Object[][] array) {
						super(array);
					}
					
					@Override
					public TwoDimensionArray<INSTANCE> setOneDimensionArray(OneDimensionArray<INSTANCE> oneDimensionArray) {
						this.oneDimensionArray = oneDimensionArray;
						return this;
					}
					
					@Override
					protected Collection<INSTANCE> __execute__() {
						Collection<INSTANCE> instances = new ArrayList<>();
						Object[][] arrays = getInput();
						if(arrays!=null){
							OneDimensionArray<INSTANCE> oneDimensionArray = getOneDimensionArray();
							//CollectionHelper collectionHelper = new CollectionHelper();
							for(Object[] array : arrays){
								INSTANCE instance = oneDimensionArray.setInput(array).execute();
								if(instance!=null)
									instances.add(instance);
							}	
						}
						
						return instances;
					}
					
				}
			}
		
		}
	}
	
	/**/
	
	public static interface Listener extends AbstractHelper.Listener {
		
		java.util.Collection<Listener> COLLECTION = new ArrayList<>();
		
		Object getIdentifier(Object instance);
		
		/**/
		
		public static class Adapter extends AbstractHelper.Listener.Adapter.Default implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Object getIdentifier(Object instance) {
				return null;
			}
			
			/**/
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
			}
			
		}
	}

	/**/
	
	public static enum MethodType {
		GET
		,SET
	}

	/**/
	
	
}
