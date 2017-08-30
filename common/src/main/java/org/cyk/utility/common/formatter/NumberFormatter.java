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

@Deprecated
public interface NumberFormatter<OUTPUT> extends Formatter<Number, OUTPUT> {
	
	Boolean isOrdinal();
	NumberFormatter<OUTPUT> setIsOrdinal(Boolean isOrdinal);
	
	Boolean isPercentage();
	NumberFormatter<OUTPUT> setIsPercentage(Boolean isPercentage);
	
	Boolean isAppendOrdinalSuffix();
	NumberFormatter<OUTPUT> setIsAppendOrdinalSuffix(Boolean isAppendOrdinalSuffix);
	
	Boolean isAppendExaequo();
	NumberFormatter<OUTPUT> setIsAppendExaequo(Boolean isAppendExaequo);
	
	/**/
	
	@Getter @Setter @Deprecated
	public static class Adapter<OUTPUT> extends Formatter.Adapter.Default<Number, OUTPUT> implements NumberFormatter<OUTPUT>,Serializable {
		private static final long serialVersionUID = 1L;

		protected Boolean isOrdinal = Boolean.FALSE,isAppendOrdinalSuffix=Boolean.FALSE,isAppendExaequo=Boolean.FALSE;
		protected Boolean isPercentage = Boolean.FALSE;
		
		public Adapter(Number number, Class<OUTPUT> outputClass,Builder logMessageBuilder) {
			super(Number.class, number, outputClass, logMessageBuilder);
		}
		
		@Override
		public Boolean isPercentage() {
			return Boolean.TRUE.equals(isPercentage);
		}
		
		@Override
		public Boolean isOrdinal() {
			return Boolean.TRUE.equals(isOrdinal);
		}
		
		@Override
		public Boolean isAppendOrdinalSuffix() {
			return Boolean.TRUE.equals(isAppendOrdinalSuffix);
		}
		
		@Override
		public Boolean isAppendExaequo() {
			return Boolean.TRUE.equals(isAppendExaequo);
		}
		
		@Override
		public NumberFormatter<OUTPUT> setIsOrdinal(Boolean isOrdinal) {
			this.isOrdinal = isOrdinal;
			return this;
		}
		
		@Override
		public NumberFormatter<OUTPUT> setIsPercentage(Boolean isPercentage) {
			this.isPercentage = isPercentage;
			return this;
		}
		
		@Override
		public NumberFormatter<OUTPUT> setIsAppendOrdinalSuffix(Boolean isAppendOrdinalSuffix) {
			this.isAppendOrdinalSuffix = isAppendOrdinalSuffix;
			return this;
		}
		
		@Override
		public NumberFormatter<OUTPUT> setIsAppendExaequo(Boolean isAppendExaequo) {
			this.isAppendExaequo = isAppendExaequo;
			return this;
		}
		
		/**/
		@Deprecated
		public static class Default<OUTPUT> extends NumberFormatter.Adapter<OUTPUT> implements Serializable {
			private static final long serialVersionUID = 1L;

			public Default(Number number, Class<OUTPUT> outputClass,Builder logMessageBuilder) {
				super(number, outputClass, logMessageBuilder);
			}
			
		}
		
	}
	@Deprecated
	public static interface String extends NumberFormatter<java.lang.String> {
		
		@Getter @Setter @Deprecated
		public static class Adapter extends NumberFormatter.Adapter.Default<java.lang.String> implements String,Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Number number,Builder logMessageBuilder) {
				super(number, java.lang.String.class, logMessageBuilder);
			}
			
			/**/
			@Deprecated
			public static class Default extends String.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Number number,Builder logMessageBuilder) {
					super(number,logMessageBuilder);
				}
				
				@Override
				protected java.lang.String __execute__() {
					addLogMessageBuilderParameters(logMessageBuilder,"locale",getLocale(), "character set",getCharacterSet(),"append exaequo",getIsAppendExaequo(),"percentage",getIsPercentage(),"ordinal",getIsOrdinal());
					if(Boolean.TRUE.equals(getIsOrdinal())){
						addLogMessageBuilderParameters(logMessageBuilder, "append ordinal suffix",getIsAppendOrdinalSuffix());
					}
					StringBuilder stringBuilder = new StringBuilder();
					BigDecimal number = new BigDecimal(getInput().toString());
					if(CharacterSet.DIGIT.equals(getCharacterSet())){
						NumberFormat numberFormatter = NumberFormat.getNumberInstance(getLocale()==null?Locale.FRENCH:getLocale());
						if(Boolean.TRUE.equals(getIsPercentage())){
							number = new BigDecimal(number.toString()).multiply(Constant.BIGDECIMAL_100);
						}
						if(Boolean.TRUE.equals(getIsOrdinal())){
							
						}
						stringBuilder.append(numberFormatter.format(number));
						if(Boolean.TRUE.equals(getIsPercentage())){
							if(StringUtils.isNotBlank(getPercentageSymbol()))
								stringBuilder.append(Constant.CHARACTER_SPACE+getPercentageSymbol());
						}
						if(Boolean.TRUE.equals(getIsOrdinal())){
							
						}
						if(Boolean.TRUE.equals(getIsAppendOrdinalSuffix()))
							stringBuilder.append(StringUtils.defaultIfBlank(StringHelper.getInstance().getOrdinalNumberSuffix(getLocale(),number),Constant.EMPTY_STRING));
						
					}else if(CharacterSet.LETTER.equals(getCharacterSet())){
						if(Boolean.TRUE.equals(getIsOrdinal())){
							stringBuilder.append(StringHelper.getInstance().getOrdinalNumber(getLocale(), number));
							
						}else{
							if(Locale.ENGLISH.equals(getLocale()))
								stringBuilder.append(EnglishNumberToWords.convert(number.longValue()));
							else if(Locale.FRENCH.equals(getLocale()))
								stringBuilder.append(FrenchNumberToWords.convert(number.longValue()));
							else
								throw new RuntimeException("Number to "+getLocale()+" words not yet implemented");
							
						}
					}
					
					if(Boolean.TRUE.equals(getIsAppendExaequo()))
						stringBuilder.append(StringUtils.defaultIfBlank(Constant.CHARACTER_SPACE+StringHelper.getInstance().getText(getLocale(),"exaequo",null),Constant.EMPTY_STRING));
					
					
					if(getWidth()!=null){
						stringBuilder = new StringBuilder(StringUtils.leftPad(stringBuilder.toString(), getWidth(), getLeftPadding()));
					}
					
					return stringBuilder.toString();
				}
				
			}
			
		}
		
	}
	
}
