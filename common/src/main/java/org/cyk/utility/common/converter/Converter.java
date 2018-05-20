package org.cyk.utility.common.converter;

import java.io.Serializable;

import org.cyk.utility.common.LogMessage;
import org.cyk.utility.common.cdi.BeanAdapter;

import lombok.Getter;
@Deprecated
public interface Converter<T,R> {

	R execute();
	
	Class<T> getInstanceClass();
	Converter<T,R> setInstanceClass(Class<T> instanceClass);
	
	T getInstance();
	Converter<T,R> setInstance(T instance);
	
	Class<R> getResultClass();
	Converter<T,R> setResultClass(Class<R> resultClass);
	
	LogMessage.Builder getLogMessageBuilder();
	Converter<T,R> setLogMessageBuilder(LogMessage.Builder logMessageBuilder);
	
	/**/
	
	@Getter @Deprecated
	public static class Adapter<T,R> extends BeanAdapter implements Converter<T,R>,Serializable {
		private static final long serialVersionUID = 1L;
		
		protected Class<T> instanceClass;
		protected T instance;
		protected Class<R> resultClass;
		protected LogMessage.Builder logMessageBuilder;
		
		public Adapter(Class<T> instanceClass,T instance,Class<R> resultClass,LogMessage.Builder logMessageBuilder) {
			setInstanceClass(instanceClass);
			setInstance(instance);
			setResultClass(resultClass);
			setLogMessageBuilder(logMessageBuilder);
		}
		
		public R execute(){
			return null;
		}
		
		@Override
		public Converter<T, R> setInstanceClass(Class<T> instanceClass) {
			this.instanceClass = instanceClass;
			return this;
		}
		
		@Override
		public Converter<T, R> setInstance(T instance) {
			this.instance = instance;
			return this;
		}
		
		@Override
		public Converter<T, R> setResultClass(Class<R> resultClass) {
			this.resultClass = resultClass;
			return this;
		}
	
		@Override
		public Converter<T, R> setLogMessageBuilder(LogMessage.Builder logMessageBuilder) {
			this.logMessageBuilder = logMessageBuilder;
			return this;
		}
		
		/**/
		@Deprecated
		public static class Default<T,R> extends Converter.Adapter<T,R> implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public Default(Class<T> instanceClass,T instance, Class<R> resultClass, LogMessage.Builder logMessageBuilder) {
				super(instanceClass,instance, resultClass, logMessageBuilder);
			}
						
			@Override
			public R execute() {
				if(getInstance() == null)
					return null;
				if(getLogMessageBuilder()==null)
					setLogMessageBuilder(new LogMessage.Builder("Convert", getInstance().getClass().getSimpleName()));
				//addLogMessageBuilderParameters(getLogMessageBuilder(),"result class",getResultClass().getSimpleName());
				R result = __execute__();
				//addLogMessageBuilderParameters(getLogMessageBuilder(),"result",getResultLogMessage(result));
				return result;
			}
			
			protected R __execute__(){
				throw new RuntimeException("Conversion of "+getInstance().getClass()+" to "+getResultClass()+" not yet implemented.");
			}
			
			protected Object getResultLogMessage(R result){
				return result;
			}
		}
	}
}
