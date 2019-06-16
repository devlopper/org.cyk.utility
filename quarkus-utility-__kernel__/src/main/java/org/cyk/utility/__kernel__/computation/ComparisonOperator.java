package org.cyk.utility.__kernel__.computation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ComparisonOperator {
	EQ("=") 
	,NEQ("!=")
	,GT(">")
	,GTE(">=")
	,LT("<")
	,LTE("<=")
	
	;
	private String symbol;
}