package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import javax.inject.Singleton;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.formatter.EnglishNumberToWords;
import org.cyk.utility.common.formatter.FrenchNumberToWords;
import org.cyk.utility.common.helper.StringHelper.CaseType;

@Singleton
public class NumberHelper extends AbstractHelper implements Serializable  {

	private static final long serialVersionUID = 1L;

	public static final String TOKEN_SEPARATOR = Constant.CHARACTER_UNDESCORE.toString();
	public static final String BASE_10_CHARACTERS = "0123456789";
	public static final String BASE_16_CHARACTERS = BASE_10_CHARACTERS + "ABCDEF";
	public static final String BASE_36_CHARACTERS = BASE_10_CHARACTERS + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String BASE_62_CHARACTERS = BASE_10_CHARACTERS + "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	private static NumberHelper INSTANCE;
	
	public static NumberHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new NumberHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public Boolean isNumber(Object object){
		return object!=null && (object instanceof Number || NumberUtils.isCreatable(object.toString()));
	}
	
	public Boolean isNotNumber(Object object){
		return !Boolean.TRUE.equals(isNumber(object));
	}
	
	public Number get(Object object){
		if(object==null)
			return null;
		String string = object.toString();
		if(StringHelper.getInstance().isBlank(string))
			return null;
		if(StringUtils.contains(string, Constant.CHARACTER_DOT.toString()))
			return new BigDecimal(string);
		return NumberUtils.toLong(string);
	}
	
	public String stringify(Number number){
		NumberHelper.Stringifier stringifier = new NumberHelper.Stringifier.Adapter.Default(number);
		return stringifier.execute();
	}
	
	/*public <T extends Number> T cast(Number number,Class<T> aClass){
		if(number==null)
			return null;
		if(aClass.equals(Short.class) && !(number instanceof Short))
			return number.shortValue();
	}*/
	
	public String concatenate(Collection<? extends Number> numbers,Integer elementLenght) {
		StringBuilder stringBuilder = new StringBuilder();
		for(Number number : numbers)
			stringBuilder.append(StringUtils.leftPad(String.valueOf(number), elementLenght,Constant.CHARACTER_ZERO));
		logTrace("concatenation of {} with lenght {} is {}", numbers,elementLenght,stringBuilder);
		return stringBuilder.toString();
	}
	
	@SuppressWarnings("unchecked")
	public<NUMBER extends Number> NUMBER getHighest(Collection<NUMBER> numbers) {
		NUMBER highest = null;
		for(Number number : numbers)
			if(number instanceof Long){
				if(highest==null || (Long)number > (Long)highest)
					highest = (NUMBER) number;
			}else if(number instanceof Integer){
				if(highest==null || (Integer)number > (Integer)highest)
					highest = (NUMBER) number;
			}else
				throw new RuntimeException("Not yet handled "+number.getClass());
		logTrace("highest value between {} is {}", numbers,highest);
		return highest;
	}
	
	public String encode(String number, String inputCharacters, String outputCharacters) {
		BigInteger integer = new BigInteger(number);
		if (integer.compareTo(BigInteger.ZERO) == -1) {
			throw new IllegalArgumentException(number+" must be nonnegative");
		}
		BigInteger size = new BigInteger(String.valueOf(outputCharacters.length()));
		String result = "";
		while (integer.compareTo(BigInteger.ZERO) == 1) {
			result = outputCharacters.charAt(integer.mod(size).intValue()) + result;
			integer = integer.divide(size);
		}
		logTrace("encoding {} in base {} is {}", number,outputCharacters.length(),result);
		return result;
	}
	
	public String encode(String number, String outputCharacters) {
		return encode(number, BASE_10_CHARACTERS, outputCharacters);
	}
	
	public String encodeToBase16(String number) {
		return encode(number, BASE_16_CHARACTERS);
	}
	
	public String encodeToBase36(String number) {
		return encode(number, BASE_36_CHARACTERS);
	}
	
	public String encodeToBase62(String number) {
		return encode(number, BASE_62_CHARACTERS);
	}
	
	public String encode(Collection<Long> identifiers, String outputCharacters){
		Long highest = getHighest(identifiers);
		String number = concatenate(identifiers, highest.toString().length());
		Integer numberOfZero = 0;
		for(int i = 0;i<number.length();i++)
			if( number.charAt(i) == Constant.CHARACTER_ZERO )
				numberOfZero++;
			else
				break;
		number = encode(number,outputCharacters);
		
		String value = highest.toString().length()+TOKEN_SEPARATOR+number+TOKEN_SEPARATOR+numberOfZero;
		logTrace("identifiers {} converted to request parameter value is {}",identifiers, value);
		return value;
	}
	
	public String encodeToBase62(Collection<Long> identifiers){
		return encode(identifiers,BASE_62_CHARACTERS);
	}
	
	@SuppressWarnings("unchecked")
	public <NUMBER extends Number> Collection<NUMBER> deconcatenate(Class<NUMBER> numberClass, String number,Integer elementLenght) {
		Collection<NUMBER> numbers = new ArrayList<>();
		for (int i = 0; i < number.length(); i = i + elementLenght) {
			String p = StringUtils.substring(number, i, i + elementLenght);
			NUMBER n = null;
			if (Long.class.equals(numberClass))
				n = (NUMBER) new Long(p);
			else if (Integer.class.equals(numberClass))
				n = (NUMBER) new Integer(p);

			if (n == null)
				;
			else
				numbers.add(n);
		}
		return numbers;
	}
	
	public String decode(String number, String inputCharacters, String outputCharacters) {
		for (char character : number.toCharArray()) {
			if (!inputCharacters.contains(String.valueOf(character))) {
				throw new IllegalArgumentException("Invalid character(s) in string: " + character);
			}
		}
		BigInteger result = BigInteger.ZERO;
		number = new StringBuffer(number).reverse().toString();
		BigDecimal count = BigDecimal.ONE;
		BigDecimal inputCharactersLenght = new BigDecimal(inputCharacters.length());
		for (char character : number.toCharArray()) {
			result = result.add(new BigDecimal(inputCharacters.indexOf(character)).multiply(count).toBigInteger());
			count = count.multiply(inputCharactersLenght);
		}
		logTrace("decoding {} from base {} is {}", number, inputCharacters.length(), result);
		return result.toString();
	}

	public String decode(String number, String inputCharacters) {
		return decode(number, inputCharacters, BASE_10_CHARACTERS);
	}

	public String decodeBase16(String number) {
		return decode(number, BASE_16_CHARACTERS);
	}

	public String decodeBase36(String number) {
		return decode(number, BASE_36_CHARACTERS);
	}

	public String decodeBase62(String number) {
		return decode(number, BASE_62_CHARACTERS);
	}
	
	public <T> T get(Class<T> aClass,Object object,T nullValue){
		if(object==null || ( (object instanceof String) && StringHelper.getInstance().isBlank((String)object) ) )
			return nullValue;
		return ClassHelper.getInstance().instanciate(aClass, new Object[]{String.class,object.toString()});
	}
	
	public <T> T get(Class<T> aClass,Object object){
		return get(aClass,object,(T)null);
	}
	
	public  Integer getInteger(Object object,Integer nullValue){
		return get(Integer.class, object, nullValue);
	}
	/*
	public <T> T get(Class<T> aClass,Number number,T nullValue){
		if(number==null)
			return null;
		return get(aClass, number.toString(), nullValue);
	}
	
	public <T> T get(Class<T> aClass,Number number){
		return get(aClass, number, null);
	}
	*/
	
	public Boolean isGreaterThanZero(Number number){
		if(number == null)
			return Boolean.FALSE;
		return number.intValue() > 0 || number.floatValue() > 0;
	}
	
	public Boolean areGreaterThanZero(Number...numbers){
		if(!ArrayHelper.getInstance().isEmpty(numbers)){
			for(Number number : numbers)
				if(Boolean.FALSE.equals(isGreaterThanZero(number)))
					return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	public Boolean compare(Number number1,Number number2,Boolean greater,Boolean equal){
		if(number1==null || number2==null)
			return Boolean.FALSE;
		if(greater==null){
			
		}else{
			if(equal==null){
				
			}else{
				if(Boolean.TRUE.equals(greater))
					if(Boolean.TRUE.equals(equal))
						return number1.doubleValue() >= number2.doubleValue();
					else
						return number1.doubleValue() > number2.doubleValue();
				else
					if(Boolean.TRUE.equals(equal))
						return number1.doubleValue() <= number2.doubleValue();
					else
						return number1.doubleValue() < number2.doubleValue();
			}
		}
		return number1.doubleValue() == number2.doubleValue();
	}
	
	public Boolean lessThan(Number number1,Number number2){
		return compare(number1, number2, Boolean.FALSE, Boolean.FALSE);
	}
	public Boolean lessThanOrEqual(Number number1,Number number2){
		return compare(number1, number2, Boolean.FALSE, Boolean.TRUE);
	}
	
	public Boolean greatThan(Number number1,Number number2){
		return compare(number1, number2, Boolean.TRUE, Boolean.FALSE);
	}
	public Boolean greatThanOrEqual(Number number1,Number number2){
		return compare(number1, number2, Boolean.TRUE, Boolean.TRUE);
	}
	
	public Number operate(Number number1,Number number2,Operation operation){
		if(number1==null)
			if(number2==null)
				return null;
			else
				return number2;
		else
			if(number2==null)
				return number1;
			else{
				switch(operation){
				case ADD:return new BigDecimal(number1.doubleValue()).add(new BigDecimal(number2.doubleValue()));
				case SUBTRACT:return new BigDecimal(number1.doubleValue()).subtract(new BigDecimal(number2.doubleValue()));
				case MULTIPLY:return new BigDecimal(number1.doubleValue()).multiply(new BigDecimal(number2.doubleValue()));
				case DIVIDE:return new BigDecimal(number1.doubleValue()).divide(new BigDecimal(number2.doubleValue()));
				}
			}
		return null;
	}
	
	/*private Number __add__(Number number1,Number number2){
		if(number1)
	}*/
	
	public Number add(Number number1,Number number2){
		return operate(number1, number2, Operation.ADD);
	}
	
	public Number subtract(Number number1,Number number2){
		return operate(number1, number2, Operation.SUBTRACT);
	}
	
	public Collection<Long> getIntegers(Number from,Number to){
		Collection<Long> integers = new ArrayList<>();
		for(Long index = from.longValue() ; index <= to.longValue() ; index ++ )
			integers.add(index);
		return integers;
	}
	
	/**/
	
	public interface Stringifier extends org.cyk.utility.common.Builder.Stringifier<Number> {
		
		Boolean isOrdinal();
		NumberHelper.Stringifier setIsOrdinal(Boolean isOrdinal);
		
		Boolean isPercentage();
		NumberHelper.Stringifier setIsPercentage(Boolean isPercentage);
		
		Boolean isAppendOrdinalSuffix();
		NumberHelper.Stringifier setIsAppendOrdinalSuffix(Boolean isAppendOrdinalSuffix);
		
		Boolean isAppendExaequo();
		NumberHelper.Stringifier setIsAppendExaequo(Boolean isAppendExaequo);
		
		/**/
		
		@Getter @Setter
		public static class Adapter extends org.cyk.utility.common.Builder.Stringifier.Adapter.Default<Number> implements NumberHelper.Stringifier,Serializable {
			private static final long serialVersionUID = 1L;

			protected Boolean isOrdinal = Boolean.FALSE,isAppendOrdinalSuffix=Boolean.FALSE,isAppendExaequo=Boolean.FALSE;
			protected Boolean isPercentage = Boolean.FALSE;
			
			public Adapter(Number number) {
				super(Number.class, number);
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
			public NumberHelper.Stringifier setIsOrdinal(Boolean isOrdinal) {
				this.isOrdinal = isOrdinal;
				return this;
			}
			
			@Override
			public NumberHelper.Stringifier setIsPercentage(Boolean isPercentage) {
				this.isPercentage = isPercentage;
				return this;
			}
			
			@Override
			public NumberHelper.Stringifier setIsAppendOrdinalSuffix(Boolean isAppendOrdinalSuffix) {
				this.isAppendOrdinalSuffix = isAppendOrdinalSuffix;
				return this;
			}
			
			@Override
			public NumberHelper.Stringifier setIsAppendExaequo(Boolean isAppendExaequo) {
				this.isAppendExaequo = isAppendExaequo;
				return this;
			}
			
			/**/
			
			public static class Default extends NumberHelper.Stringifier.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Number number) {
					super(number);
				}
				
				public Default() {
					this(null);
				}
				
				@Override
				protected java.lang.String __execute__() {
					/*addLogMessageBuilderParameters(logMessageBuilder,"locale",locale, "character set",getCharacterSet(),"append exaequo",getIsAppendExaequo(),"percentage",getIsPercentage(),"ordinal",getIsOrdinal());
					if(Boolean.TRUE.equals(getIsOrdinal())){
						addLogMessageBuilderParameters(logMessageBuilder, "append ordinal suffix",getIsAppendOrdinalSuffix());
					}*/
					StringBuilder stringBuilder = new StringBuilder();
					BigDecimal number = new BigDecimal(getInput().toString());
					Locale locale = InstanceHelper.getInstance().getIfNotNullElseDefault(getLocale(),Locale.FRENCH);
					CharacterSet characterSet = InstanceHelper.getInstance().getIfNotNullElseDefault(getCharacterSet(), CharacterSet.DEFAULT);
					
					if(CharacterSet.DIGIT.equals(characterSet)){
						NumberFormat numberFormatter = NumberFormat.getNumberInstance(locale);
						if(Boolean.TRUE.equals(getIsPercentage())){
							number = new BigDecimal(number.toString()).multiply(Constant.BIGDECIMAL_100);
						}
						if(Boolean.TRUE.equals(getIsOrdinal())){
							
						}
						stringBuilder.append(numberFormatter.format(number));
						Boolean isPercentageSymbol = InstanceHelper.getInstance().getIfNotNullElseDefault(getIsPercentage(),Boolean.FALSE);
						if(Boolean.TRUE.equals(isPercentageSymbol)){
							String percentageSymbol = InstanceHelper.getInstance().getIfNotNullElseDefault(getPercentageSymbol(),Constant.CHARACTER_PERCENTAGE.toString());
							if(StringUtils.isNotBlank(percentageSymbol))
								stringBuilder.append(Constant.CHARACTER_SPACE+percentageSymbol);
						}
						if(Boolean.TRUE.equals(getIsOrdinal())){
							
						}
						if(Boolean.TRUE.equals(getIsAppendOrdinalSuffix()))
							stringBuilder.append(StringUtils.defaultIfBlank(StringHelper.getInstance().getOrdinalNumberSuffix(locale,number),Constant.EMPTY_STRING));
						
					}else if(CharacterSet.LETTER.equals(getCharacterSet())){
						if(Boolean.TRUE.equals(getIsOrdinal())){
							stringBuilder.append(StringHelper.getInstance().getOrdinalNumber(locale, number));
							
						}else{
							if(Locale.ENGLISH.equals(locale))
								stringBuilder.append(EnglishNumberToWords.convert(number.longValue()));
							else if(Locale.FRENCH.equals(locale))
								stringBuilder.append(FrenchNumberToWords.convert(number.longValue()));
							else
								throw new RuntimeException("Number to "+locale+" words not yet implemented");
							
						}
					}
					
					if(Boolean.TRUE.equals(getIsAppendExaequo()))
						stringBuilder.append(StringUtils.defaultIfBlank(Constant.CHARACTER_SPACE+StringHelper.getInstance().get("exaequo",CaseType.L,new Object[]{}),Constant.EMPTY_STRING));
					
					
					if(getWidth()!=null){
						stringBuilder = new StringBuilder(StringUtils.leftPad(stringBuilder.toString(), getWidth(), getLeftPadding()));
					}
					
					return stringBuilder.toString();
				}	
			}	
		}
				
	}

	
	/**/
	
	public static final Short SHORT_ZERO = new Short("0");
	
	public static final Byte BYTE_ZERO = new Byte("0");
	
	/**/
	
	public static enum Operation {ADD,SUBTRACT,MULTIPLY,DIVIDE}
	
}
