package org.cyk.utility.common.formatter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.LogMessage.Builder;
import org.cyk.utility.common.helper.StringHelper;

import lombok.Getter;
import lombok.Setter;

public interface NumberFormatter<OUTPUT> extends Formatter<Number, OUTPUT> {
	
	Boolean isRank();
	NumberFormatter<OUTPUT> setIsRank(Boolean isRank);
	
	Boolean isPercentage();
	NumberFormatter<OUTPUT> setIsPercentage(Boolean isPercentage);
	
	java.lang.String getNumberSuffix(Long number);
	
	/**/
	
	@Getter @Setter
	public static class Adapter<OUTPUT> extends Formatter.Adapter.Default<Number, OUTPUT> implements NumberFormatter<OUTPUT>,Serializable {
		private static final long serialVersionUID = 1L;

		protected Boolean isRank = Boolean.FALSE;
		protected Boolean isPercentage = Boolean.FALSE;
		
		public Adapter(Number number, Class<OUTPUT> outputClass,Builder logMessageBuilder) {
			super(Number.class, number, outputClass, logMessageBuilder);
		}
		
		@Override
		public Boolean isPercentage() {
			return Boolean.TRUE.equals(isPercentage);
		}
		
		@Override
		public Boolean isRank() {
			return Boolean.TRUE.equals(isRank);
		}
		
		@Override
		public NumberFormatter<OUTPUT> setIsRank(Boolean isRank) {
			this.isRank = isRank;
			return this;
		}
		
		@Override
		public NumberFormatter<OUTPUT> setIsPercentage(Boolean isPercentage) {
			this.isPercentage = isPercentage;
			return this;
		}
		
		@Override
		public java.lang.String getNumberSuffix(Long number) {
			return null;
		}
		
		/**/
		
		public static class Default<OUTPUT> extends NumberFormatter.Adapter<OUTPUT> implements Serializable {
			private static final long serialVersionUID = 1L;

			public Default(Number number, Class<OUTPUT> outputClass,Builder logMessageBuilder) {
				super(number, outputClass, logMessageBuilder);
			}
			
		}
		
	}
	
	public static interface String extends NumberFormatter<java.lang.String> {
		
		@Getter @Setter
		public static class Adapter extends NumberFormatter.Adapter.Default<java.lang.String> implements String,Serializable {
			private static final long serialVersionUID = 1L;

			
			
			public Adapter(Number number,Builder logMessageBuilder) {
				super(number, java.lang.String.class, logMessageBuilder);
			}
			
			/**/
			
			public static class Default extends String.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Number number,Builder logMessageBuilder) {
					super(number,logMessageBuilder);
				}
				
				@Override
				public java.lang.String getNumberSuffix(Long number) {
					return StringHelper.getInstance().getNumberSuffix(getLocale(), number);
				}
				
				@Override
				protected java.lang.String __execute__() {
					addLogMessageBuilderParameters(logMessageBuilder, "character set",getCharacterSet());
					StringBuilder stringBuilder = new StringBuilder();
					BigDecimal number = new BigDecimal(getInput().toString());
					if(CharacterSet.DIGIT.equals(getCharacterSet())){
						NumberFormat numberFormatter = NumberFormat.getNumberInstance(getLocale()==null?Locale.FRENCH:getLocale());
						if(Boolean.TRUE.equals(getIsPercentage())){
							number = new BigDecimal(number.toString()).multiply(Constant.BIGDECIMAL_100);
						}
						if(Boolean.TRUE.equals(getIsRank())){
							
						}
						stringBuilder.append(numberFormatter.format(number));
						if(Boolean.TRUE.equals(getIsPercentage())){
							if(StringUtils.isNotBlank(getPercentageSymbol()))
								stringBuilder.append(Constant.CHARACTER_SPACE+getPercentageSymbol());
						}
						if(Boolean.TRUE.equals(getIsRank())){
							java.lang.String suffix = getNumberSuffix(number.longValue());
							if(StringUtils.isNotBlank(suffix))
								stringBuilder.append(suffix);
						}
						if(getWidth()!=null){
							stringBuilder = new StringBuilder(StringUtils.leftPad(stringBuilder.toString(), getWidth(), getLeftPadding()));
						}
					}else if(CharacterSet.LETTER.equals(getCharacterSet())){
						if(Boolean.TRUE.equals(getIsRank())){
							return getText("rank."+number+".letter");
						}else{
							throw new RuntimeException("Not yet implemented");
						}
					}
					return stringBuilder.toString();
				}
				
			}
			
		}
		
	}
	
}
