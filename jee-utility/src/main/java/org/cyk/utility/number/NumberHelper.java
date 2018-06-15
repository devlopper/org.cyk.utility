package org.cyk.utility.number;

import java.math.BigDecimal;
import java.util.Collection;

import org.cyk.utility.helper.Helper;

public interface NumberHelper extends Helper {

	public static enum Operation {ADD,SUBTRACT,MULTIPLY,DIVIDE}
	
	Boolean isZero(Number number);
	Boolean isGreaterThanZero(Number number);
	
	Number operate(Operation operation,Collection<Number> numbers);
	Number operate(Operation operation,Number...numbers);
	Number add(Number...numbers);
	Number subtract(Number...numbers);
	<NUMBER> NUMBER get(Class<NUMBER> aClass, Object object, NUMBER nullValue);
	<NUMBER> NUMBER get(Class<NUMBER> aClass, Object object);
	Integer getInteger(Object object, Integer nullValue);
	Long getLong(Object object, Long nullValue);
	Long getLong(Object object);
	BigDecimal getBigDecimal(Object object, BigDecimal nullValue);
	BigDecimal getBigDecimal(Object object);
}
