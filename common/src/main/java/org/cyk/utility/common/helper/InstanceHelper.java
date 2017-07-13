package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.Properties;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.cyk.utility.common.Action;
import org.cyk.utility.common.ListenerUtils;

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
		
		public static class Adapter<INSTANCE> extends Action.Adapter.Default<INSTANCE, INSTANCE> implements Setter<INSTANCE>,Serializable {
			private static final long serialVersionUID = 1L;
			
			public Adapter(Class<INSTANCE> instanceClass, INSTANCE instance) {
				super("set", instanceClass, instance, instanceClass);
			}
			
			/**/
			
			public static class Default<INSTANCE> extends Setter.Adapter<INSTANCE> implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public Default(Class<INSTANCE> instanceClass, INSTANCE instance) {
					super(instanceClass, instance);
				}
				
				@Override
				protected INSTANCE __execute__() {
					INSTANCE instance = getInput();
					Properties properties = getProperties();
					if(properties!=null){
						FieldHelper fieldHelper = new FieldHelper();
						for(Entry<Object, Object> entry : properties.entrySet()){
							fieldHelper.set(instance, entry.getValue(),(String)entry.getKey());
						}
					}
					return instance;
				}
			}
		}
		
		/**/
		
		/*public static interface Collection<INSTANCE> extends Action<Collection<INSTANCE>, Collection<INSTANCE>> {
			
			public static class Adapter<INSTANCE> extends Action.Adapter.Default<Collection<INSTANCE>, Collection<INSTANCE>> implements Collection<INSTANCE>,Serializable {
				private static final long serialVersionUID = 1L;
			
				public Adapter(Collection<INSTANCE> input) {
					super("set", null, input, null);
				}
			
				public static class Default<INSTANCE> extends Collection.Adapter<INSTANCE> implements Serializable {
					private static final long serialVersionUID = 1L;
					
					public Default(Collection<INSTANCE> input) {
						super(input);
					}
					
				}
			}
		}*/
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
						Object[] attributes = new CollectionHelper().getArray(getParameters());
						
						if(attributes!=null){
							Setter<INSTANCE> setter = getSetter();
							if(setter==null){
								setter = new Setter.Adapter.Default<INSTANCE>(getOutputClass(), instance);
							}
							
							Object[] values = getInput();
							Object[] objects = new Object[values.length*2];
							for(int i = 0 , j = 0; j < attributes.length ; i = i + 2 , j++){
								objects[i] = attributes[j];
								objects[i+1] = values[j];
							}
							setter.setManyProperties(objects).execute();
						}
						return instance;
					}
					
					@Override
					public OneDimensionArray<INSTANCE> addManyParameters(Object... parameters) {
						return (org.cyk.utility.common.helper.InstanceHelper.Builder.OneDimensionArray<INSTANCE>) super.addManyParameters(parameters);
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
