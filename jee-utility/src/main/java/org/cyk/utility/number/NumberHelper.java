package org.cyk.utility.number;

import java.math.BigDecimal;
import java.util.Collection;

import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.helper.Helper;

public interface NumberHelper extends Helper {

	public static enum Operation {ADD,SUBTRACT,MULTIPLY,DIVIDE}
	
	Boolean isZero(Number number);
	Boolean isGreaterThanZero(Number number);
	
	Number operate(Operation operation,Collection<Number> numbers);
	Number operate(Operation operation,Number...numbers);
	Number add(Number...numbers);
	Number subtract(Number...numbers);
	
	static <NUMBER> NUMBER get(Class<NUMBER> klass,Object object,NUMBER valueWhenNullOrWhenNumberFormatException){
		if(object==null || ((object instanceof String) && ((String)object).isBlank()))
			return valueWhenNullOrWhenNumberFormatException;
		try {
			if(Byte.class.equals(klass))
				return (NUMBER) Byte.valueOf(object.toString());
			if(Short.class.equals(klass))
				return (NUMBER) Short.valueOf(object.toString());
			if(Integer.class.equals(klass))
				return (NUMBER) Integer.valueOf(object.toString());
			if(Long.class.equals(klass))
				return (NUMBER) Long.valueOf(object.toString());
			if(BigDecimal.class.equals(klass))
				return (NUMBER) new BigDecimal(object.toString());
		} catch (NumberFormatException exception) {
			return valueWhenNullOrWhenNumberFormatException;
		}
		throw new RuntimeException("conversion to number of type "+klass+" not yet handled");
	}
	
	static <NUMBER> NUMBER get(Class<NUMBER> aClass,Object object){
		return get(aClass,object,(NUMBER)null);
	}
	
	static  Integer getInteger(Object object,Integer nullValue){
		return get(Integer.class, object, nullValue);
	}
	
	static Integer getInteger(Object object) {
		return getInteger(object, null);
	}
	
	static  Long getLong(Object object,Long nullValue){
		return get(Long.class, object, nullValue);
	}

	static Long getLong(Object object) {
		return getLong(object, null);
	}
	
	static BigDecimal getBigDecimal(Object object, BigDecimal nullValue) {
		return get(BigDecimal.class, object, nullValue);
	}

	static BigDecimal getBigDecimal(Object object) {
		return getBigDecimal(object, null);
	}
	
	Boolean compare(Number number1,Number number2,ComparisonOperator operator);
}
