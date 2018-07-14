package org.cyk.utility.computation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ArithmeticOperator {
	EQ("=") 
	,NEQ("!=")
	,GT(">")
	,GTE(">=")
	,LT("<")
	,LTE("<=")
	,IN("IN")
	,NOT_IN("NOT IN")
	,BETWEEN("BETWEEN")
	,LIKE("LIKE")
	;
	private String symbol;
}