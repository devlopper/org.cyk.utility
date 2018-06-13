package org.cyk.utility.number;

import java.util.Collection;

public interface NumberHelper {

	public static enum Operation {ADD,SUBTRACT,MULTIPLY,DIVIDE}
	
	Boolean isZero(Number number);
	Boolean isGreaterThanZero(Number number);
	
	Number operate(Operation operation,Collection<Number> numbers);
	Number operate(Operation operation,Number...numbers);
	Number add(Number...numbers);
	Number subtract(Number...numbers);
	
}
