package org.cyk.utility.common.computation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ArithmeticOperator {
	EQ("="), GT(">"), GTE(">="), LT("<"), LTE("<="),IN("IN"),BETWEEN("BETWEEN");
	private String symbol;
}