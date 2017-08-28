package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.inject.Singleton;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.cyk.utility.common.Action;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.StringHelper.CaseType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Singleton
public class MethodHelper extends AbstractHelper implements Serializable  {

	private static final long serialVersionUID = 1L;

	private static MethodHelper INSTANCE;
	private static final String METHOD_NOT_FOUND_FORMAT = "Method %s.%s(%s) not found";
	
	public static MethodHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new MethodHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public String getName(String prefix,String suffix){
		return prefix+ StringHelper.getInstance().applyCaseType(suffix, CaseType.FU);
		//tringUtils.substring(suffix, 0,1).toUpperCase()+StringUtils.substring(suffix, 1)/*.toLowerCase()*/;
	}
	
	public String getName(Method.Type type,String suffix){
		return getName(type.getPrefix(), suffix);
	}
	
	public java.lang.reflect.Method get(Boolean throwIfNull,Class<?> aClass,String name,Class<?>...parameterClasses){
		java.lang.reflect.Method method = aClass == null || StringHelper.getInstance().isBlank(name) ? null 
				: MethodUtils.getMatchingAccessibleMethod(aClass, name,parameterClasses);
		if(method==null && Boolean.TRUE.equals(throwIfNull))
			throw new RuntimeException(String.format(METHOD_NOT_FOUND_FORMAT,aClass,name,StringHelper.getInstance().get(parameterClasses, ",")));
		return method;
	}
	
	public java.lang.reflect.Method get(Class<?> aClass,String name,Class<?>...parameterClasses){
		return get(Boolean.FALSE, aClass, name, parameterClasses);
	}
	
	public java.lang.reflect.Method get(Boolean throwIfNull,Object instance,String name,Class<?>...parameterClasses){
		return get(throwIfNull,instance.getClass(), name, parameterClasses);
	}
	
	public java.lang.reflect.Method get(Object instance,String name,Class<?>...parameterClasses){
		return get(Boolean.FALSE, instance, name, parameterClasses);
	}
	
	public Boolean isExist(Class<?> aClass,String name,Class<?>...parameterClasses){
		return get(aClass, name,parameterClasses)!=null;
	}
	
	public String getFirstExist(Class<?> aClass,String[] names,Class<?>...parameterClasses){
		for(String name : names)
			if(!StringHelper.getInstance().isBlank(name) && Boolean.TRUE.equals(isExist(aClass, name, parameterClasses)))
				return name;
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T call(Object instance,Class<T> resultClass,String name,Method.CallBack callBack,MethodHelper.Method.Parameter...parameters){
		try {
			Class<?>[] classes = ArrayHelper.getInstance().get(Class.class, callGet(parameters, Class.class, MethodHelper.Method.Parameter.FIELD_CLAZZ));
			Object[] values = ArrayHelper.getInstance().get(Object.class, callGet(parameters, Object.class, MethodHelper.Method.Parameter.FIELD_VALUE));
			T result = (T) get(Boolean.TRUE,instance, name, classes).invoke(instance, values);
			if(callBack!=null && Boolean.TRUE.equals(callBack.isExecutable(instance, resultClass, name, parameters, result))){
				result = callBack.execute(instance, resultClass, name, parameters, result);
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e); 
		}
	}
	
	public <T> T call(Object instance,Class<T> resultClass,String name,MethodHelper.Method.Parameter...parameters){
		return call(instance, resultClass, name, null, parameters);
	}
	
	public Object callSet(Object instance,String name,Class<?> valueClass,Object value){
		call(instance, Void.class, getName(Method.Type.SET, name), new MethodHelper.Method.Parameter(valueClass,value));
		return instance;
	}
	
	public <T> T callGet(Object instance,Class<T> resultClass,String name){
		return call(instance, resultClass, getName(Method.Type.GET, name));
	}
	
	public <T> Collection<T> callGet(Collection<?> instances,Class<T> resultClass,String name){
		if(CollectionHelper.getInstance().isEmpty(instances))
			return null;
		Collection<T> result = new ArrayList<>();
		for(Object instance : instances){
			T value = (T) callGet(instance, resultClass, name);
			result.add(value);
		}
		return result;
	}
	
	public <T> Collection<T> callGet(Object[] instances,Class<T> resultClass,String name){
		if(ArrayHelper.getInstance().isEmpty(instances))
			return null;
		return callGet(Arrays.asList(instances), resultClass, name);
	}
	
	public String getNamesFromStackTrace(String separator){
		return commonUtils.convertToString(Thread.currentThread().getStackTrace(), separator);
	}
	
	public String getNamesStackTrace(){
		return getNamesFromStackTrace(Constant.LINE_DELIMITER);
	}
	
	public String getNameFromStackTraceAt(Integer index){
		return Thread.currentThread().getStackTrace()[index].getMethodName();
	}
	
	public String getCurrentNameFromStackTrace(){
		return getNameFromStackTraceAt(3);
	}

	/**/
	
	public interface Method<INPUT,OUTPUT> extends Action<INPUT, OUTPUT>{
		
		OUTPUT getNullValue();
		Method<INPUT,OUTPUT> setNullValue(OUTPUT nullValue);
		
		@Getter
		public static class Adapter<INPUT,OUTPUT> extends Action.Adapter.Default<INPUT,OUTPUT> implements Method<INPUT,OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;
			
			protected OUTPUT nullValue;
			
			public Adapter(String name, Class<INPUT> inputClass, INPUT input, Class<OUTPUT> outputClass) {
				super(name, inputClass, input, outputClass);
			}
			
			@Override
			public Method<INPUT, OUTPUT> setNullValue(OUTPUT output) {
				return null;
			}
			
			public static class Default<INPUT, OUTPUT> extends Method.Adapter<INPUT, OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public Default(String name, Class<INPUT> inputClass, INPUT input, Class<OUTPUT> outputClass) {
					super(name, inputClass, input, outputClass);
				}
				
				@Override
				public Method<INPUT, OUTPUT> setNullValue(OUTPUT nullValue) {
					this.nullValue = nullValue;
					return this;
				}
				
			}
		}
	
		public static interface Call<OUTPUT> extends Action<java.lang.reflect.Method, OUTPUT> {
			
			public static class Adapter<OUTPUT> extends Action.Adapter.Default<java.lang.reflect.Method, OUTPUT> implements Call<OUTPUT>,Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter(java.lang.reflect.Method input,Class<OUTPUT> outputClass) {
					super("call", java.lang.reflect.Method.class, input, outputClass);
				}
				
				public static class Default<OUTPUT> extends Call.Adapter<OUTPUT> implements Serializable {
					private static final long serialVersionUID = 1L;

					public Default(java.lang.reflect.Method input,Class<OUTPUT> outputClass) {
						super(input, outputClass);
					}
					
					@Override
					protected OUTPUT __execute__() {
						Object instance = getProperty(Method.PROPERTY_NAME_INSTANCE);
						String name = getPropertyAsString(PROPERTY_NAME);
						//Class<OUTPUT> outputClass = getOutputClass();
						Object[] parameters = null;
						try {
							Class<?>[] classes = getArgumentClasses(parameters);
							Object[] values = getArgumentValues(parameters);
							@SuppressWarnings("unchecked")
							OUTPUT result = (OUTPUT) MethodHelper.getInstance().get(instance, name, classes).invoke(instance, values);
							/*if(callBack!=null && Boolean.TRUE.equals(callBack.isExecutable(instance, outputClass, name, parameters, result))){
								result = callBack.execute(instance, outputClass, name, parameters, result);
							}*/
							return result;
						} catch (Exception e) {
							throw new RuntimeException(e); 
						}
					}
					
					protected Class<?>[] getArgumentClasses(Object[] parameters){
						//Get<Class<?>> get = new Get.Adapter.Default<Class<?>>(null,null).execute();
						return null;//ArrayHelper.getInstance().get(Class.class, callGet(parameters, Class.class, MethodHelper.Method.Parameter.FIELD_CLAZZ));
					}
					
					protected Object[] getArgumentValues(Object[] parameters){
						return null;//ArrayHelper.getInstance().get(Class.class, callGet(parameters, Class.class, MethodHelper.Method.Parameter.FIELD_VALUE));
					}
				}
			}
			
			public static interface Get<OUTPUT> extends Call<OUTPUT> {
				
				public static class Adapter<OUTPUT> extends Call.Adapter.Default<OUTPUT> implements Get<OUTPUT>,Serializable{
					private static final long serialVersionUID = 1L;

					public Adapter(java.lang.reflect.Method input, Class<OUTPUT> outputClass) {
						super(input, outputClass);
					}
					
					public static class Default<OUTPUT> extends Get.Adapter<OUTPUT> implements Serializable{
						private static final long serialVersionUID = 1L;

						public Default(java.lang.reflect.Method input, Class<OUTPUT> outputClass) {
							super(input, outputClass);
						}	
						
						@Override
						protected OUTPUT __execute__() {
							// TODO Auto-generated method stub
							return super.__execute__();
						}
					}
				}
				
			}
			
		}
		
		public static interface CallBack {
			<T> Boolean isExecutable(Object instance,Class<T> resultClass,String name,MethodHelper.Method.Parameter[] parameters,T result);
			<T> T execute(Object instance,Class<T> resultClass,String name,MethodHelper.Method.Parameter[] parameters,T result);
		}
		
		/**/
	
		@Getter @AllArgsConstructor
		public static enum Type {
			GET("get")
			,SET("set")
			,READ_BY("readBy")
			,FIND_BY("findBy")
			
			;
			
			private String prefix;
		}
		
		@Getter @Setter @Accessors(chain=true) @NoArgsConstructor @ToString
		public static class Parameter implements Serializable {
			private static final long serialVersionUID = 1L;
			
			private Class<?> clazz;
			private Object value;
		
			public Parameter(Class<?> clazz, Object value) {
				super();
				this.clazz = clazz;
				this.value = value;
			}
			
			public static Parameter[] buildArray(Object...objects){
				Parameter[] parameters = new Parameter[objects.length / 2];
				int j = 0;
				for(int i = 0 ; i < objects.length ; i = i + 2){
					parameters[j] = new Parameter((Class<?>)objects[i], objects[i+1]);
				}
				return parameters;
			}
			
			public static final String FIELD_CLAZZ = "clazz";
			public static final String FIELD_VALUE = "value";
			
		}
	}
	
	
	
}
