package org.cyk.utility.regularexpression;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.string.Strings;

public interface RegularExpressionInstance extends Objectable {

	String getExpression();
	RegularExpressionInstance setExpression(String expression);
	
	Boolean match(String string);
	
	/**/
	Strings getStartTokens();
	RegularExpressionInstance setStartTokens(Strings startTokens);
	
	Strings getMiddleTokens();
	Strings getMiddleTokens(Boolean injectIfNull);
	RegularExpressionInstance setMiddleTokens(Strings middleTokens);
	
	Strings getEndTokens();
	Strings getEndTokens(Boolean injectIfNull);
	RegularExpressionInstance setEndTokens(Strings endTokens);
}
