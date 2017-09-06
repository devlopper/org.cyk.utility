package org.cyk.utility.common.formatter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.LogMessage.Builder;

import lombok.Getter;
import lombok.Setter;
@Deprecated
public interface DateFormatter<OUTPUT> extends Formatter<Date, OUTPUT> {
	
	public static enum Token{DATE_ONLY,TIME_ONLY,DATE_AND_TIME}
	
	Constant.Date.Part getPart();
	DateFormatter<OUTPUT> setPart(Constant.Date.Part part);
	
	Constant.Date.Length getLength();
	DateFormatter<OUTPUT> setLength(Constant.Date.Length length);
	
	/**/
	
	@Getter @Setter @Deprecated
	public static class Adapter<OUTPUT> extends Formatter.Adapter.Default<Date, OUTPUT> implements DateFormatter<OUTPUT>,Serializable {
		private static final long serialVersionUID = 1L;

		protected Constant.Date.Part part = Constant.Date.Part.DATE_ONLY;
		protected Constant.Date.Length length = Constant.Date.Length.SHORT;
		
		public Adapter(Date date, Class<OUTPUT> outputClass,Builder logMessageBuilder) {
			super(Date.class, date, outputClass, logMessageBuilder);
		}
		
		@Override
		public DateFormatter<OUTPUT> setPart(Constant.Date.Part part) {
			this.part = part;
			return this;
		}
		
		@Override
		public DateFormatter<OUTPUT> setLength(Constant.Date.Length length) {
			this.length = length;
			return this;
		}
		
		/**/
		@Deprecated
		public static class Default<OUTPUT> extends DateFormatter.Adapter<OUTPUT> implements Serializable {
			private static final long serialVersionUID = 1L;

			public Default(Date date, Class<OUTPUT> outputClass,Builder logMessageBuilder) {
				super(date, outputClass, logMessageBuilder);
			}
			
		}
		
	}
	@Deprecated
	public static interface String extends DateFormatter<java.lang.String> {
		
		@Getter @Setter @Deprecated
		public static class Adapter extends DateFormatter.Adapter.Default<java.lang.String> implements String,Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Date date,Builder logMessageBuilder) {
				super(date, java.lang.String.class, logMessageBuilder);
			}
			
			/**/
			@Deprecated
			public static class Default extends String.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Date date,Builder logMessageBuilder) {
					super(date,logMessageBuilder);
				}
				
				@Override
				protected java.lang.String __execute__() {
					StringBuilder stringBuilder = new StringBuilder();
					stringBuilder.append(new SimpleDateFormat(Constant.Date.getPattern(getLocale(),getPart(), getLength()).getValue(),getLocale()).format(getInput()));
					return stringBuilder.toString();
				}
				
			}
			
		}
		
	}
	
}
