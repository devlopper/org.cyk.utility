package org.cyk.utility.common.converter;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.common.LogMessage;
import org.cyk.utility.common.converter.OneConverter.OneConverterToArray;

import lombok.Getter;

public interface ManyConverter<T,R> extends Converter<Collection<T>, R> {

	OneConverter<T, R> getOneConverter();
	ManyConverter<T,R> setOneConverter(OneConverter<T, R> oneConverter);
	
	/**/
	
	@Getter
	public static class Adapter<T,R> extends Converter.Adapter.Default<Collection<T>, R> implements ManyConverter<T,R>,Serializable {
		private static final long serialVersionUID = 1L;
		
		protected OneConverter<T, R> oneConverter;
		
		public Adapter(Collection<T> instances,Class<R> resultClass,LogMessage.Builder logMessageBuilder) {
			super(null,instances, resultClass, logMessageBuilder);
		}
		
		@Override
		public ManyConverter<T, R> setOneConverter(OneConverter<T, R> oneConverter) {
			this.oneConverter = oneConverter;
			return this;
		}
		
		/**/
		
		public static class Default<T,R> extends ManyConverter.Adapter<T,R> implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public Default(Collection<T> instances, Class<R> resultClass, LogMessage.Builder logMessageBuilder) {
				super(instances, resultClass, logMessageBuilder);
			}
			
			@Override
			public R execute() {
				if(getInstance() == null)
					return null;
				if(getLogMessageBuilder()==null)
					setLogMessageBuilder(new LogMessage.Builder("Convert collection of",getOneConverter().getInstanceClass().getSimpleName()));
				addLogMessageBuilderParameters(getLogMessageBuilder(),"result class",getResultClass().getSimpleName());
				R result = __execute__();
				addLogMessageBuilderParameters(getLogMessageBuilder(),"result",getResultLogMessage(result));
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

	/**/
	
	public static interface ManyConverterToArray<T,R> extends ManyConverter<T, R>{
		
		@Getter
		public static class Adapter<T,R> extends ManyConverter.Adapter.Default<T, R> implements ManyConverterToArray<T,R>,Serializable {
			private static final long serialVersionUID = 1L;
			
			public Adapter(Collection<T> instances, Class<R> resultClass, LogMessage.Builder logMessageBuilder) {
				super(instances, resultClass, logMessageBuilder);
			}
			
			/**/
			
			public static class Default<T,R> extends ManyConverterToArray.Adapter<T,R> implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public Default(Collection<T> instances,Class<R> resultClass,LogMessage.Builder logMessageBuilder) {
					super(instances,resultClass,logMessageBuilder);
				}
				
				@SuppressWarnings("unchecked")
				@Override
				public R __execute__() {
					R result = null;
					if(String[][].class.equals(resultClass)){
						OneConverterToArray<T, R> oneConverterToArray = (OneConverterToArray<T, R>) getOneConverter();
						result = (R) new String[instance.size()][oneConverterToArray.getLength().intValue()];
						Integer i = 0;
						for(T index : instance){
							getOneConverter().setInstance(index);
							String[] array = (String[]) oneConverterToArray.execute(); //convert(identifiable, String[].class, arguments);
							if(array==null)
								;
							else
								((String[][])result)[i++] = array;
						}
					}
					return result;
				}
				
				
			}
		}
	}		
	
}
