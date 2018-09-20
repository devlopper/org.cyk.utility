package org.cyk.utility.regularexpression;

import java.util.regex.Pattern;

import org.cyk.utility.helper.Helper;

public interface RegularExpressionHelper extends Helper {

	Boolean match(String string,String expression);
	Boolean match(String string,Pattern pattern);
	
}
