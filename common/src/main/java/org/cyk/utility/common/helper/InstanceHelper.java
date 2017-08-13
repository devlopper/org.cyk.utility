package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.inject.Singleton;

import org.cyk.utility.common.Action;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ArrayHelper.Element;

import javassist.Modifier;
import lombok.Getter;

@Singleton
public class InstanceHelper extends AbstractHelper implements Serializable  {

	private static final long serialVersionUID = 1L;

	private static InstanceHelper INSTANCE;
	
	public static InstanceHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new InstanceHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
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
						ListenerHelper.Executor.Function.Adapter.Default.Object<ProcessValue> valueProcessorExecutor = getProcessValuesExecutor();
						if(valueProcessorExecutor==null){
							valueProcessorExecutor = new ListenerHelper.Executor.Function.Adapter.Default.Object<ProcessValue>();
							valueProcessorExecutor.setResultMethod(ClassHelper.getInstance().instanciateOne(ProcessValue.Adapter.Default.RESULT_METHOD_CLASS));
							valueProcessorExecutor.setInput((Collection<ProcessValue>) ClassHelper.getInstance().instanciateMany(ProcessValue.class
									,CollectionHelper.getInstance().isEmpty(ProcessValue.CLASSES) ? Arrays.asList(ProcessValue.Adapter.Default.class) : ProcessValue.CLASSES));
						}
						
						for(Entry<Object, Object> entry : properties.entrySet()){
							valueProcessorExecutor.setManyProperties(ProcessValue.PROPERTY_INSTANCE,instance,ProcessValue.PROPERTY_FIELD_NAME,(String)entry.getKey()
									,ProcessValue.PROPERTY_VALUE,entry.getValue());
							setFieldValue(instance, (String)entry.getKey(), CollectionHelper.getInstance().isEmpty(valueProcessorExecutor.getInput()) ? entry.getValue() : valueProcessorExecutor.execute());
						}
					}
					return instance;
				}
				
				protected void setFieldValue(INSTANCE instance,String fieldName,Object value){
					FieldHelper.getInstance().set(instance, value,fieldName);
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
					
					@SuppressWarnings("unchecked")
					public static Class<ListenerHelper.Executor.ResultMethod<Object, ProcessValue>> RESULT_METHOD_CLASS = (Class<org.cyk.utility.common.helper.ListenerHelper.Executor.ResultMethod<Object, ProcessValue>>) ClassHelper.getInstance().getByName(ResultMethod.class);
					
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
							if( StringHelper.getInstance().isBlank((java.lang.String)value) )
								value = null;
							
							if(value!=null){
								Field field = FieldHelper.getInstance().get(getProperty(PROPERTY_INSTANCE).getClass(), (java.lang.String)getProperty(PROPERTY_FIELD_NAME));
								//if(field!=null){
								Class<?> fieldType = ClassHelper.getInstance().getWrapper(field.getType());
								if(ClassHelper.getInstance().isString(fieldType)){
									
								}else if(ClassHelper.getInstance().isNumber(fieldType)){
									value = NumberHelper.getInstance().get(fieldType,(java.lang.String)value);
								}else if(ClassHelper.getInstance().isBoolean(fieldType)){
									value = new BooleanHelper.Builder.String.Adapter.Default((java.lang.String)value).execute();
								}else if(ClassHelper.getInstance().isDate(fieldType)){
									value = new TimeHelper.Builder.String.Adapter.Default((java.lang.String)value).execute();
								}else
									value = Pool.getInstance().get(fieldType, value);
								/*
								if(!fieldType.isAssignableFrom(value.getClass())){
									if(ClassHelper.getInstance().isNumber(fieldType)){
										value = NumberHelper.getInstance().get(fieldType,(java.lang.String)value);
									}else {
										value = StringUtils.defaultIfBlank((java.lang.String)value, null);
									}
								}	
								*/
								//}	
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
			
			//ListenerHelper.Executor.Function.Adapter.Default<Datasource> getDatasourcesExecutor();
			//ToStringMapping setDatasourcesExecutor(ListenerHelper.Executor.Function.Adapter.Default.String<Datasource> datasourcesExecutor);
			
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
			
			ArrayHelper.Dimension.Key.Builder getKeyBuilder();
			OneDimensionArray<INSTANCE> setKeyBuilder(ArrayHelper.Dimension.Key.Builder keyBuilder);
			
			@Getter
			public static class Adapter<INSTANCE> extends Builder.Adapter.Default<Object[], INSTANCE> implements OneDimensionArray<INSTANCE>,Serializable {
				private static final long serialVersionUID = 1L;
				
				protected ArrayHelper.Dimension.Key.Builder keyBuilder;
				
				public Adapter(Object[] array, Class<INSTANCE> outputClass) {
					super(Object[].class, array, outputClass);
				}
				
				@Override
				public Builder.OneDimensionArray<INSTANCE> setKeyBuilder(ArrayHelper.Dimension.Key.Builder keyBuilder) {
					return null;
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
					protected Boolean isShowInputLogMessage(Object[] input) {
						return Boolean.FALSE;
					}
					
					@Override
					public Builder.OneDimensionArray<INSTANCE> setKeyBuilder(ArrayHelper.Dimension.Key.Builder keyBuilder) {
						this.keyBuilder = keyBuilder;
						return this;
					}
					
					@Override
					protected INSTANCE __execute__() {
						Class<INSTANCE> instanceClass = getOutputClass();
						INSTANCE instance = null;
						Object[] values = getInput();
						ArrayHelper.Dimension.Key.Builder keyBuilder = getKeyBuilder();
						ArrayHelper.Dimension.Key key = null;
						if(keyBuilder!=null){
							key = keyBuilder.setInput(values).execute();
							addLoggingMessageBuilderNamedParameters("key",key.getValue());
						}
						if(key!=null){
							Boolean pooled = Boolean.TRUE.equals(Pool.getInstance().contains(instanceClass));
							if(pooled){
								instance = Pool.getInstance().get(instanceClass, key.getValue());
							}
							addLoggingMessageBuilderNamedParameters(instanceClass.getSimpleName()+" pooled",pooled,"found",instance!=null);
							if(instance==null)
								instance = new InstanceHelper.Lookup.Adapter.Default<>(Object.class, key.getValue(), instanceClass).execute();
							addLoggingMessageBuilderNamedParameters("looked up instance is not null",instance!=null);
						}
						if(instance==null)
							instance = ClassHelper.getInstance().instanciateOne(instanceClass);
						Object[] parametersArray = CollectionHelper.getInstance().getArray(getParameters());
						if(parametersArray!=null){
							Setter<INSTANCE> setter = getSetter();
							if(setter==null){
								setter = new Setter.Adapter.Default<INSTANCE>(instanceClass);
							}
							setter.setInput(instance);
							
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
					super(Object[][].class, array, (Class<Collection<INSTANCE>>) ClassHelper.getInstance().getByName(Collection.class.getName()));
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
					
					public Default() {
						super(null);
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
	
	public static interface Lookup<IDENTIFIER,INSTANCE> extends Action<IDENTIFIER, INSTANCE> {
		
		ListenerHelper.Executor.Function.Adapter.Default.Object<Source<?,?>> getSourcesExecutor();
		Setter<INSTANCE> setSourcesExecutor(ListenerHelper.Executor.Function.Adapter.Default.Object<Source<?,?>> sourcesExecutor);
		
		@Getter
		public static class Adapter<IDENTIFIER,INSTANCE> extends Action.Adapter.Default<IDENTIFIER, INSTANCE> implements Lookup<IDENTIFIER,INSTANCE> , Serializable {
			private static final long serialVersionUID = 1L;
			
			protected ListenerHelper.Executor.Function.Adapter.Default.Object<Source<?,?>> sourcesExecutor;
			
			public Adapter(Class<IDENTIFIER> inputClass, IDENTIFIER input, Class<INSTANCE> outputClass) {
				super("lookup", inputClass, input, outputClass);
			}
			
			@Override
			public Setter<INSTANCE> setSourcesExecutor(ListenerHelper.Executor.Function.Adapter.Default.Object<Lookup.Source<?, ?>> sourcesExecutor) {
				return null;
			}
		
			public static class Default<IDENTIFIER,INSTANCE> extends Lookup.Adapter<IDENTIFIER, INSTANCE> implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public Default(Class<IDENTIFIER> inputClass, IDENTIFIER input, Class<INSTANCE> outputClass) {
					super(inputClass, input, outputClass);
				}
				
				@SuppressWarnings({ "rawtypes", "unchecked" })
				@Override
				protected INSTANCE __execute__() {
					ListenerHelper.Executor.Function.Adapter.Default.Object<Source<?,?>> sourcesExecutor = getSourcesExecutor();
					if(sourcesExecutor==null){
						sourcesExecutor = new ListenerHelper.Executor.Function.Adapter.Default.Object<Source<?,?>>();
						sourcesExecutor.setResultMethod(ClassHelper.getInstance().instanciateOne(Source.Adapter.Default.RESULT_METHOD_CLASS));
						sourcesExecutor.setInput(new ArrayList());
						for(Source<?,?> source : ClassHelper.getInstance().instanciateMany(Source.class
								,CollectionHelper.getInstance().isEmpty(Source.CLASSES) ? Arrays.asList(Source.Adapter.Default.class) : Source.CLASSES))
							sourcesExecutor.getInput().add(source);
					}
					sourcesExecutor.getResultMethod().setInputClass((Class<Object>) getInputClass());
					sourcesExecutor.getResultMethod().setInput(getInput());
					sourcesExecutor.getResultMethod().setOutputClass((Class<Object>) getOutputClass());
					return (INSTANCE) sourcesExecutor.execute();
				}
			}
		}
		
		/**/
		
		public static interface Source<IDENTIFIER,INSTANCE> extends Action<IDENTIFIER,INSTANCE>{
			
			Collection<Class<? extends Source<?,?>>> CLASSES = new ArrayList<>();
			
			public static class Adapter<IDENTIFIER,INSTANCE> extends Action.Adapter.Default<IDENTIFIER,INSTANCE> implements Source<IDENTIFIER,INSTANCE>,Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter(Class<IDENTIFIER> inputClass, IDENTIFIER input,Class<INSTANCE> outputClass) {
					super("get", inputClass, input, outputClass);
				}
				
				/**/
				
				public static class Default<IDENTIFIER,INSTANCE> extends Source.Adapter<IDENTIFIER,INSTANCE> implements Serializable {
					private static final long serialVersionUID = 1L;
					
					@SuppressWarnings("unchecked")
					public static Class<ListenerHelper.Executor.ResultMethod<Object, Source<?,?>>> RESULT_METHOD_CLASS = (Class<org.cyk.utility.common.helper.ListenerHelper.Executor.ResultMethod<Object, Source<?, ?>>>) ClassHelper.getInstance().getByName(ResultMethod.class);
					
					public Default(Class<IDENTIFIER> inputClass, IDENTIFIER input,Class<INSTANCE> outputClass) {
						super(inputClass, input, outputClass);
					}
					
					public Default() {
						super(null, null, null);
					}
					
					public static class ResultMethod extends ListenerHelper.Executor.ResultMethod.Adapter.Default.Object<Source<?,?>> {
						private static final long serialVersionUID = 1L;

						@Override
						protected java.lang.Object __execute__() {
							return new ClassHelper.Instanciation.Adapter.Default<java.lang.Object>(getOutputClass()).execute(); //ClassHelper.getInstance().instanciateOne((Class<?>)getOutputClass());
						}
					}
					
				}
				
			}
			
			/**/
			
			public static interface UserDefined extends Source<Object,Object> {
				public static final java.util.Map<Object,Object> REPOSITORY = new HashMap<>();
				public static class Adapter extends Source.Adapter.Default<Object,Object> implements UserDefined,Serializable {
					private static final long serialVersionUID = 1L;
					public Adapter(Object identifier) {
						super(Object.class, identifier, Object.class);
					}
					public static class Default extends UserDefined.Adapter implements Serializable {
						private static final long serialVersionUID = 1L;
						public Default(Object identifier) {
							super(identifier);
						}
						@Override
						protected Object __execute__() {
							return REPOSITORY.get(getInput());
						}
					}
				}
			}
			
			public static interface Database extends Source<Object,Object> {
				
				public static class Adapter extends Source.Adapter.Default<Object,Object> implements Database,Serializable {
					private static final long serialVersionUID = 1L;
					public Adapter(Object identifier) {
						super(Object.class, identifier, Object.class);
					}
					public static class Default extends Database.Adapter implements Serializable {
						private static final long serialVersionUID = 1L;
						
						public Default() {
							super(null);
						}
						
					}
				}
			}
			
		}
	
	}
	
	/**/
	
	public static interface Copy<INSTANCE> extends org.cyk.utility.common.Builder<INSTANCE, INSTANCE> {
		
		Collection<Class<?>> IS_FIELD_CLASSES = new ArrayList<>();
		
		ListenerHelper.Executor.Function.Adapter.Default.Object<Action<?,Boolean>> getIsFieldExecutor();
		Copy<INSTANCE> setIsFieldExecutor(ListenerHelper.Executor.Function.Adapter.Default.Object<Action<?,Boolean>> isFieldExecutor);
		
		Collection<String> getIgnoredFieldNames();
		Copy<INSTANCE> setIgnoredFieldNames(Collection<String> ignoredFieldNames);
		Copy<INSTANCE> addIgnoredFieldNames(Collection<String> ignoredFieldNames);
		Copy<INSTANCE> addIgnoredFieldNames(String...ignoredFieldNames);
		
		Collection<Class<?>> getIgnoredFieldAnnotationClasses();
		Copy<INSTANCE> setIgnoredFieldAnnotationClasses(Collection<Class<?>> ignoredFieldAnnotationClasses);
		Copy<INSTANCE> addIgnoredFieldAnnotationClasses(Collection<Class<?>> ignoredFieldAnnotationClasses);
		Copy<INSTANCE> addIgnoredFieldAnnotationClasses(Class<?>...ignoredFieldAnnotationClasses);
		
		@Getter
		public static class Adapter<INSTANCE> extends org.cyk.utility.common.Builder.Adapter.Default<INSTANCE, INSTANCE> implements Copy<INSTANCE>,Serializable {
			private static final long serialVersionUID = 1L;
			
			protected Collection<String> ignoredFieldNames;
			protected Collection<Class<?>> ignoredFieldAnnotationClasses;
			protected ListenerHelper.Executor.Function.Adapter.Default.Object<Action<?,Boolean>> isFieldExecutor;
			
			public Adapter(Class<INSTANCE> inputClass, INSTANCE input) {
				super(inputClass, input, inputClass);
			}
			
			@Override
			public Copy<INSTANCE> setIsFieldExecutor(ListenerHelper.Executor.Function.Adapter.Default.Object<Action<?, Boolean>> isFieldExecutor) {
				return null;
			}
			
			@Override
			public Copy<INSTANCE> setIgnoredFieldNames(Collection<String> ignoredFieldNames) {
				return null;
			}
			
			@Override
			public Copy<INSTANCE> addIgnoredFieldNames(Collection<String> ignoredFieldNames) {
				return null;
			}
			
			@Override
			public Copy<INSTANCE> addIgnoredFieldNames(String... ignoredFieldNames) {
				return null;
			}
			
			@Override
			public Copy<INSTANCE> setIgnoredFieldAnnotationClasses(Collection<Class<?>> ignoredFieldAnnotationClasses) {
				return null;
			}
			
			@Override
			public Copy<INSTANCE> addIgnoredFieldAnnotationClasses(Class<?>... ignoredFieldAnnotationClasses) {
				return null;
			}
			
			@Override
			public Copy<INSTANCE> addIgnoredFieldAnnotationClasses(Collection<Class<?>> ignoredFieldAnnotationClasses) {
				return null;
			}
			
			public static class Default<INSTANCE> extends Copy.Adapter<INSTANCE> implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@SuppressWarnings("unchecked")
				public static Class<ListenerHelper.Executor.ResultMethod<Object, Action<?,Boolean>>> RESULT_METHOD_CLASS =
						(Class<ListenerHelper.Executor.ResultMethod<Object, Action<?, Boolean>>>) ClassHelper.getInstance()
						.getByName(ListenerHelper.Executor.ResultMethod.Adapter.Default.Boolean.class);
				
				@SuppressWarnings("unchecked")
				public Default(INSTANCE input) {
					super(input == null ? null :  (Class<INSTANCE>) input.getClass(), input);
				}
				
				@Override
				public Copy<INSTANCE> setIsFieldExecutor(ListenerHelper.Executor.Function.Adapter.Default.Object<Action<?, Boolean>> isFieldExecutor) {
					this.isFieldExecutor = isFieldExecutor;
					return this;
				}
				
				@Override
				public Copy<INSTANCE> setIgnoredFieldNames(Collection<String> ignoredFieldNames) {
					this.ignoredFieldNames = ignoredFieldNames;
					return this;
				}
				
				@Override
				public Copy<INSTANCE> addIgnoredFieldNames(Collection<String> ignoredFieldNames) {
					if(!CollectionHelper.getInstance().isEmpty(ignoredFieldNames)){
						if(this.ignoredFieldNames == null)
							this.ignoredFieldNames = new ArrayList<>();
						this.ignoredFieldNames.addAll(ignoredFieldNames);
					}
					return this;
				}
				
				@Override
				public Copy<INSTANCE> addIgnoredFieldNames(String... ignoredFieldNames) {
					if(ignoredFieldNames!=null && ignoredFieldNames.length>0)
						addIgnoredFieldNames(Arrays.asList(ignoredFieldNames));
					return this;
				}
				
				@Override
				public Copy<INSTANCE> setIgnoredFieldAnnotationClasses(Collection<Class<?>> ignoredFieldAnnotationClasses) {
					this.ignoredFieldAnnotationClasses = ignoredFieldAnnotationClasses;
					return this;
				}
				
				@Override
				public Copy<INSTANCE> addIgnoredFieldAnnotationClasses(Class<?>... ignoredFieldAnnotationClasses) {
					if(ignoredFieldAnnotationClasses!=null && ignoredFieldAnnotationClasses.length>0)
						addIgnoredFieldAnnotationClasses(Arrays.asList(ignoredFieldAnnotationClasses));
					return this;
				}
				
				@Override
				public Copy<INSTANCE> addIgnoredFieldAnnotationClasses(Collection<Class<?>> ignoredFieldAnnotationClasses) {
					if(!CollectionHelper.getInstance().isEmpty(ignoredFieldAnnotationClasses)){
						if(this.ignoredFieldAnnotationClasses == null)
							this.ignoredFieldAnnotationClasses = new ArrayList<>();
						this.ignoredFieldAnnotationClasses.addAll(ignoredFieldAnnotationClasses);
					}
					return this;
				}
				
				@SuppressWarnings("unchecked")
				@Override
				protected INSTANCE __execute__() {
					INSTANCE instance = ClassHelper.getInstance().instanciateOne(getOutputClass());
					Collection<String> ignoredFieldNames = getIgnoredFieldNames();
					Collection<Class<?>> ignoredFieldAnnotationClasses = getIgnoredFieldAnnotationClasses();
					ListenerHelper.Executor.Function.Adapter.Default.Object<Action<?,Boolean>> isFieldExecutor = getIsFieldExecutor();
					if(isFieldExecutor==null){
						isFieldExecutor = new ListenerHelper.Executor.Function.Adapter.Default.Object<Action<?,Boolean>>();
						isFieldExecutor.setResultMethod(ClassHelper.getInstance().instanciateOne(RESULT_METHOD_CLASS));
						isFieldExecutor.setInput(new ArrayList<Action<?,Boolean>>());
						for(Action<?,Boolean> source : ClassHelper.getInstance().instanciateMany(ListenerHelper.Executor.ResultMethod.class
								,CollectionHelper.getInstance().isEmpty(IS_FIELD_CLASSES) ? 
										Arrays.asList(ListenerHelper.Executor.ResultMethod.Adapter.Default.Boolean.class) : IS_FIELD_CLASSES))
							isFieldExecutor.getInput().add(source);
					}
					
					for(Field field : FieldHelper.getInstance().get(instance.getClass())){
						if(Modifier.isStatic(field.getModifiers()))//TODO should be avoid , use FieldHelper to get desired fields
							continue;
						Boolean b = null;//(Boolean) isFieldExecutor.getResultMethod().setInput(field).execute();
						
						//sourcesExecutor.getResultMethod().setInputClass((Class<Object>) getInputClass());
						//sourcesExecutor.getResultMethod().setInput(getInput());
						//sourcesExecutor.getResultMethod().setOutputClass((Class<Object>) getOutputClass());
						
						//final Field finalField = field;
						
						if(b==null){
							Boolean ignored = ignoredFieldNames!=null && ignoredFieldNames.contains(field.getName());
							if(Boolean.FALSE.equals(ignored)){
								if(ignoredFieldAnnotationClasses!=null)
									for(@SuppressWarnings("rawtypes") Class aClass : ignoredFieldAnnotationClasses){
										if(field.getAnnotation(aClass)!=null){
											ignored = Boolean.TRUE;
											break;
										}
									}
							}
							b = Boolean.FALSE.equals(ignored) ;
						}
						
						if(Boolean.TRUE.equals(b)) {
							FieldHelper.getInstance().set(instance, FieldHelper.getInstance().read(getInput(), field), field);
						}		
					}
					return instance;
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

	/**/
	
	@Singleton
	public static class Pool extends AbstractBean implements Serializable {
		private static final long serialVersionUID = 1L;
		private static final Map<Class<?>,Collection<?>> MAP = new HashMap<>();
		
		private static Pool INSTANCE;
		
		public static Pool getInstance() {
			if(INSTANCE == null)
				INSTANCE = new Pool();
			return INSTANCE;
		}
		
		@Override
		protected void initialisation() {
			INSTANCE = this;
			super.initialisation();
		}
		
		public <T> Pool load(Class<T> aClass){
			Collection<T> result = null,c;
			for(Class<?> listenerClass : Listener.CLASSES){
				Listener listener = (Listener) ClassHelper.getInstance().instanciateOne(listenerClass);
				c = listener.load(aClass);
				if(c!=null)
					result = c;
			}
			MAP.remove(aClass);
			add(aClass, result);
			return this;
		}
		
		public <T> Pool add(Class<T> aClass,Collection<T> collection){
			if(collection!=null){
				Collection<T> c = get(aClass);
				if(c==null)
					MAP.put(aClass, c = new ArrayList<>());
				c.addAll(collection);
			}
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public <T> Collection<T> get(Class<T> aClass){
			return (Collection<T>) MAP.get(aClass);
		}
		
		public Boolean contains(Class<?> aClass){
			return MAP.containsKey(aClass);
		}
		
		public <T> T get(Class<T> aClass,Object identifier){
			if(aClass==null || identifier==null)
				return null;
			Collection<T> collection = get(aClass);
			if(collection==null)
				return null;
			for(T instance : collection)
				if( identifier.equals(InstanceHelper.getInstance().getIdentifier(instance)) )
					return instance;
			
			T instance = null,c;
			for(Class<?> listenerClass : Listener.CLASSES){
				Listener listener = (Listener) ClassHelper.getInstance().instanciateOne(listenerClass);
				c = listener.get(aClass,identifier);
				if(c!=null)
					instance = c;
			}
			return instance;
		}
		
		public Pool clear(){
			MAP.clear();
			return this;
		}
		
		/**/
		
		public static interface Listener {
			
			Collection<Class<?>> CLASSES = new ArrayList<>();
			
			<T> Collection<T> load(Class<T> aClass);
			<T> T get(Class<T> aClass,Object identifier);
			
			public static class Adapter implements Listener , Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public <T> Collection<T> load(Class<T> aClass) {
					return null;
				}
				
				@Override
				public <T> T get(Class<T> aClass, Object identifier) {
					return null;
				}
				
				public static class Default extends Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
					
				}
				
			}
			
		}
	}
	
}
