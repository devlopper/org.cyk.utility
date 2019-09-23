package org.cyk.utility.__kernel__.string;

import java.beans.Introspector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.constant.ConstantSeparator;

public interface StringHelper {

	static Boolean isEmpty(String string) {
		return string == null || string.isEmpty();
	}
	
	static Boolean isNotEmpty(String string) {
		return string != null && !string.isEmpty();
	}
	
	static Boolean isBlank(String string) {
		return string == null || string.isBlank();
	}
	
	static Boolean isNotBlank(String string) {
		return string != null && !string.isBlank();
	}
	
	static Boolean isAtLocation(String string,String subString,StringLocation location,Boolean caseSensitive){
		if(StringUtils.isEmpty(subString))
			return Boolean.TRUE;
		if(location==null)
			location = StringLocation.EXAT;
		if(caseSensitive==null)
			caseSensitive = Boolean.TRUE;
		switch(location){
		case START : return Boolean.TRUE.equals(caseSensitive) ? StringUtils.startsWith(string, subString) : StringUtils.startsWithIgnoreCase(string, subString);
		case INSIDE : return Boolean.TRUE.equals(caseSensitive) ? StringUtils.contains(string, subString) : StringUtils.containsIgnoreCase(string, subString);
		case END : return Boolean.TRUE.equals(caseSensitive) ? StringUtils.endsWith(string, subString) : StringUtils.endsWithIgnoreCase(string, subString);
		case EXAT : return Boolean.TRUE.equals(caseSensitive) ? StringUtils.equals(string, subString) : StringUtils.equalsIgnoreCase(string, subString);
		}
		return Boolean.FALSE;
	}
	
	static Boolean isAtLocation(String string, String subString, StringLocation location) {
		return isAtLocation(string, subString, location, Boolean.TRUE);
	}
	
	static Number getLength(String string) {
		if(string == null || string.isEmpty())
			return 0;
		return string.length();
	}
	
	static Collection<String> splitByCharacterTypeCamelCase(String string) {
		if(string == null || string.isBlank())
			return null;
		String[] strings =	StringUtils.splitByCharacterTypeCamelCase(string);
		if(strings == null || strings.length == 0)
			return null;
		return List.of(strings);
	}
	
	static String defaultIfBlank(String string,String defaultString) {
		return StringUtils.defaultIfBlank(string,defaultString);
	}
	
	static String getVariableNameFrom(String string){
		if(string == null || string.isBlank())
			return null;
		return Introspector.decapitalize(string);
	}
	
	static String concatenate(Collection<String> strings,String separator) {
		if(strings == null || strings.isEmpty())
			return null;
		return StringUtils.join(strings,separator);
	}
	
	static String applyCase(String string, Case kase) {
		if(string==null || string.isBlank())
			return null;
		if(kase==null)
			kase = Case.DEFAULT;
		switch(kase){
		case NONE:return string;
		case LOWER:return StringUtils.lowerCase(string);
		case UPPER:return StringUtils.upperCase(string);
		case FIRST_CHARACTER_UPPER:return StringUtils.upperCase(StringUtils.substring(string, 0,1))+StringUtils.substring(string, 1);
		case FIRST_CHARACTER_LOWER:return StringUtils.lowerCase(StringUtils.substring(string, 0,1))+StringUtils.substring(string, 1);
		case FIRST_CHARACTER_UPPER_REMAINDER_LOWER:return StringUtils.capitalize(string.toLowerCase());
		}
		throw new RuntimeException("applying case "+kase+" to string "+string+" is not yet handled");	
	}
	
	static Collection<String> applyCase(Collection<String> strings, Case kase) {
		if(strings == null || strings.isEmpty())
			return null;
		return strings.stream().map(x -> applyCase(x, kase)).collect(Collectors.toList());
	}
	
	static String addToBeginIfDoesNotStartWith(String string, Object prefix) {
		String prefixString = get(prefix);
		if(!StringUtils.startsWith(string, prefixString))
			string = prefixString + string;
		return string;
	}
	
	static String addToEndIfDoesNotEndWith(String string, Object suffix) {
		String suffixString = get(suffix);
		if(!StringUtils.endsWith(string, suffixString))
			string = string + suffixString;
		return string;
	}
	
	static String removeToBeginIfDoesStartWith(String string, Object prefix) {
		String prefixString = get(prefix);
		if(StringUtils.startsWith(string, prefixString))
			string = string.substring(prefixString.length());
		return string;
	}
	
	static String get(Object object) {
		return object == null ? ConstantEmpty.STRING : object.toString();
	}
	
	static Collection<String> getLines(String string) {
		if(string == null || string.isBlank())
			return null;
		List<String> lines = string.lines().collect(Collectors.toList());
		for(Integer index = 0 ; index < lines.size() - 1 ; index = index + 1)
			lines.set(index, lines.get(index)+ConstantSeparator.LINE_WITH_LINE_FEED);
		return lines;
	}
	
	static Collection<String> getInvalidLines(Collection<String> lines,Set<String> invalidLinesRegularExpressions) {
		if(lines == null || lines.isEmpty())
			return null;
		Collection<String> collection = new ArrayList<>();
		for(String line : lines) {
			if(line.length() < 2 || !RegularExpressionHelper.isContainAlphabeticCharacter(line))
				collection.add(line);
		}
		/*for(String invalidLine : invalidLinesRegularExpressions) {
			string = StringUtils.remove(string, invalidLine+ConstantSeparator.LINE_WITH_LINE_FEED);		
		}
		*/
		return collection;
	}

	static Collection<String> getInvalidLines(String string,Set<String> invalidLinesRegularExpressions) {
		if(string == null || string.isBlank())
			return null;
		return getInvalidLines(getLines(string),invalidLinesRegularExpressions);
	}
	
	static String removeInvalidLines(String string,Set<String> invalidLinesRegularExpressions) {
		if(string == null || string.isBlank())
			return null;
		//TODO we can use regex to speed ud processing		
		Collection<String> lines = getLines(string);
		Collection<String> invalidLines = getInvalidLines(string, invalidLinesRegularExpressions);
		lines.removeAll(invalidLines);
		return StringUtils.join(lines,"");
	}
}
