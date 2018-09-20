package org.cyk.utility.regularexpression;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.collection.CollectionInstanceString;

public interface RegularExpressionInstance extends Objectable {

	String getExpression();
	RegularExpressionInstance setExpression(String expression);
	
	Boolean match(String string);
	
	/**/
	CollectionInstanceString getStartTokens();
	RegularExpressionInstance setStartTokens(CollectionInstanceString startTokens);
	
	CollectionInstanceString getMiddleTokens();
	CollectionInstanceString getMiddleTokens(Boolean injectIfNull);
	RegularExpressionInstance setMiddleTokens(CollectionInstanceString middleTokens);
	
	CollectionInstanceString getEndTokens();
	CollectionInstanceString getEndTokens(Boolean injectIfNull);
	RegularExpressionInstance setEndTokens(CollectionInstanceString endTokens);
}
