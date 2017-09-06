package org.cyk.utility.common.converter;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.LogMessage;

import lombok.Getter;
@Deprecated
public interface OneConverter<T,R> extends Converter<T, R> {
	
	/**/
	
	@Getter @Deprecated
	public static class Adapter<T,R> extends Converter.Adapter.Default<T, R> implements OneConverter<T,R>,Serializable {
		private static final long serialVersionUID = 1L;
		
		public Adapter(Class<T> instanceClass,T instance,Class<R> resultClass,LogMessage.Builder logMessageBuilder) {
			super(instanceClass,instance, resultClass, logMessageBuilder);
		}
		
		/**/
		@Deprecated
		public static class Default<T,R> extends OneConverter.Adapter<T,R> implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public Default(Class<T> instanceClass,T instance, Class<R> resultClass, LogMessage.Builder logMessageBuilder) {
				super(instanceClass,instance, resultClass, logMessageBuilder);
			}
		
		}
	}

	/**/
	@Deprecated
	public static interface OneConverterToArray<T,R> extends OneConverter<T, R>{
		
		Integer getLength();
		OneConverterToArray<T,R> setLength(Integer length);
		
		Object getValueAt(Integer index);
		Boolean isBlankValue(Object value);
		Object getBlankValueOf(Integer index);
		
		@Getter @Deprecated
		public static class Adapter<T,R> extends OneConverter.Adapter.Default<T, R> implements OneConverterToArray<T,R>,Serializable {
			private static final long serialVersionUID = 1L;
			
			protected Integer length;
			
			public Adapter(Class<T> instanceClass,T instance, Class<R> resultClass,Integer length, LogMessage.Builder logMessageBuilder) {
				super(instanceClass,instance, resultClass, logMessageBuilder);
				setLength(length);
			}
			
			@Override
			public OneConverterToArray<T, R> setLength(Integer length) {
				this.length = length;
				return this;
			}
			
			@Override
			public Object getValueAt(Integer index) {
				return null;
			}
			
			@Override
			public Boolean isBlankValue(Object value) {
				return null;
			}
			
			@Override
			public Object getBlankValueOf(Integer index) {
				return null;
			}
			
			/**/
			@Deprecated
			public static class Default<T,R> extends OneConverterToArray.Adapter<T,R> implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public Default(Class<T> instanceClass,T instance,Class<R> resultClass,LogMessage.Builder logMessageBuilder) {
					super(instanceClass,instance,resultClass,2,logMessageBuilder);
				}
				
				@Override
				public Boolean isBlankValue(Object value) {
					return value == null || (value instanceof String && StringUtils.isBlank(((String)value)));
				}
				
				@Override
				public Object getBlankValueOf(Integer index) {
					return Constant.EMPTY_STRING;
				}
					
				@Override
				public Object getValueAt(Integer index) {
					return getInstance().toString();
				}
				
				@SuppressWarnings("unchecked")
				@Override
				public R __execute__() {
					R result = null;
					if(String[].class.equals(getResultClass())){
						result = (R) new String[getLength().intValue()];
						for(Integer i = 0 ; i < getLength() ; i++){
							Object value = getValueAt(i);
							if(Boolean.TRUE.equals(isBlankValue(value)))
								value = getBlankValueOf(i);
							((String[])result)[i] = String.valueOf(value);
						}
					}
					return result;
				}
				
				@Override
				protected Object getResultLogMessage(R result) {
					return StringUtils.join((String[])result,",");
				}
			}
		}
	}	
	
}
