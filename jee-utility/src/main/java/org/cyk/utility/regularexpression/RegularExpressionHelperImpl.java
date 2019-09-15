package org.cyk.utility.regularexpression;

import java.io.Serializable;
import java.util.Collection;
import java.util.regex.Pattern;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.array.ArrayHelperImpl;
import org.cyk.utility.collection.CollectionHelperImpl;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.string.StringHelperImpl;
import org.cyk.utility.string.Strings;

@ApplicationScoped
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
	
	/**/
	
	public static Boolean __match__(String string, Pattern pattern) {
		if(pattern == null)
			return Boolean.FALSE;
		return pattern.matcher(string).find();
	}
	
	public static Boolean __match__(String string, String expression) {
		if(string == null || expression == null)
			return Boolean.FALSE;
		return __match__(string, Pattern.compile(expression));
	}

	public static Boolean __isContainAlphabeticCharacter__(String string) {
		if(StringHelperImpl.__isBlank__(string))
			return Boolean.FALSE;
		return __match__(string, ".*[a-zA-Z].*");
	}
	
	public static Boolean __isFileName__(String string) {
		if(StringHelperImpl.__isBlank__(string))
			return Boolean.FALSE;
		return __match__(string, "^[\\w,\\s-]+\\.[A-Za-z]+$");
	}
	
	public static Boolean __isFileNameHavingExtensions__(String string,Collection<String> extensions) {
		if(StringHelperImpl.__isBlank__(string) || CollectionHelperImpl.__isEmpty__(extensions))
			return Boolean.FALSE;
		return __match__(string, String.format(FILE_NAME_HAVING_EXTENSIONS_FORMAT ,StringUtils.join(extensions,"|")));
	}
	
	public static String __formatFileNameHavingExtensions__(Collection<String> extensions) {
		if(CollectionHelperImpl.__isEmpty__(extensions))
			return null;
		return String.format(FILE_NAME_HAVING_EXTENSIONS_FORMAT, StringUtils.join(extensions,"|"));
	}
	
	public static String __formatFileNameHavingExtensions__(String...extensions) {
		if(ArrayHelperImpl.__isEmpty__(extensions))
			return null;
		return __formatFileNameHavingExtensions__(CollectionHelperImpl.__instanciate__(extensions));
	}
	
	public static String __formatFileNameHavingExtensions__(Strings extensions) {
		if(CollectionHelperImpl.__isEmpty__(extensions))
			return null;
		return __formatFileNameHavingExtensions__(extensions.get());
	}
}
