package org.cyk.utility.regularexpression;

import java.io.Serializable;
import java.util.regex.Pattern;

import javax.inject.Singleton;

import org.cyk.utility.helper.AbstractHelper;

@Singleton
public class RegularExpressionHelperImpl extends AbstractHelper implements RegularExpressionHelper, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Boolean match(String string, Pattern pattern) {
		if(pattern == null)
			return Boolean.FALSE;
		return pattern.matcher(string).find();
	}
	
	@Override
	public Boolean match(String string, String expression) {
		if(string == null || expression == null)
			return Boolean.FALSE;
		return match(string, Pattern.compile(expression));
	}

}
