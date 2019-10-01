package org.cyk.utility.__kernel__.string;

import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;

public interface RegularExpressionHelper {

	static String buildIsExactly(Collection<String> strings) {
		if(strings == null || strings.isEmpty())
			return null;
		return "^("+StringUtils.join(strings,"|")+")$";
	}
	
	static String buildIsExactly(String...strings) {
		if(strings == null || strings.length == 0)
			return null;
		return buildIsExactly(List.of(strings));
	}
	
	static String buildIsNotExactly(Collection<String> strings) {
		if(strings == null || strings.isEmpty())
			return null;
		return StringUtils.join(strings.stream().map(string -> "(^(?!"+string+")|(?<!"+string+")$)").collect(Collectors.toList()),ConstantEmpty.STRING);
	}
	
	static String buildIsNotExactly(String...strings) {
		if(strings == null || strings.length == 0)
			return null;
		return buildIsNotExactly(List.of(strings));
	}
	
	static String buildIsDoNotEndWith(Collection<String> strings) {
		if(strings == null || strings.isEmpty())
			return null;
		return StringUtils.join(strings.stream().map(string -> ".*(?<!"+string+")$").collect(Collectors.toList()),ConstantEmpty.STRING);
	}
	
	static String buildIsDoNotEndWith(String...strings) {
		if(strings == null || strings.length == 0)
			return null;
		return buildIsDoNotEndWith(List.of(strings));
	}
	
	
	
	static Boolean match(String string, Pattern pattern) {
		if(string == null || string.isEmpty() || pattern == null)
			return Boolean.FALSE;
		return pattern.matcher(string).find();
	}
	
	static Boolean match(String string, String expression) {
		if(string == null || string.isEmpty() || expression == null || expression.isEmpty())
			return Boolean.FALSE;
		return match(string, Pattern.compile(expression));
	}

	static Boolean isContainAlphabeticCharacter(String string) {
		if(string == null || string.isBlank())
			return Boolean.FALSE;
		return match(string, ".*[a-zA-Z].*");
	}
	
	static Boolean isFileName(String string) {
		if(string == null || string.isBlank())
			return Boolean.FALSE;
		return match(string, "^[\\w,\\s-]+\\.[A-Za-z]+$");
	}
	
	static Boolean isFileNameHavingExtensions(String string,Collection<String> extensions) {
		if(string == null || string.isBlank() || extensions == null || extensions.isEmpty())
			return Boolean.FALSE;
		return match(string, String.format(FILE_NAME_HAVING_EXTENSIONS_FORMAT ,StringUtils.join(extensions,"|")));
	}
	
	static String formatFileNameHavingExtensions(Collection<String> extensions) {
		if(extensions == null || extensions.isEmpty())
			return null;
		return String.format(FILE_NAME_HAVING_EXTENSIONS_FORMAT, StringUtils.join(extensions,"|"));
	}
	
	static String formatFileNameHavingExtensions(String...extensions) {
		if(extensions == null || extensions.length == 0)
			return null;
		return formatFileNameHavingExtensions(List.of(extensions));
	}
	
	static String formatFileNameHavingExtensions(Strings extensions) {
		if(extensions == null || extensions.isEmpty())
			return null;
		return formatFileNameHavingExtensions(extensions.get());
	}
	
	String FILE_NAME_HAVING_EXTENSIONS_FORMAT = "(?i)^.+\\.(%s)$";
}
