package org.cyk.utility.common.helper;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.common.Action;
import org.cyk.utility.common.Constant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Singleton
public class MethodHelper extends AbstractHelper implements Serializable  {

	private static final long serialVersionUID = 1L;

	private static MethodHelper INSTANCE;
	
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
	
		/**/
		
		@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
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
