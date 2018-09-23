package org.cyk.utility.string;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.helper.Helper;

public interface StringHelper extends Helper {

	Number getLength(String string);
	Boolean isBlank(String string);
	Boolean isNotBlank(String string);
	String replace(String string, String subString, String replacement);
	String applyCase(String string,Case aCase);
	String getFromRepositories(Properties properties);
	
	Boolean isAtLocation(String string,String subString,StringLocation location,Boolean caseSensitive);
	Boolean isAtLocation(String string,String subString,StringLocation location);
	
	String concatenate(Collection<String> strings,String separator);
	String concatenate(Collection<String> strings);
	String concatenate(String...strings);
	
	Collection<String> get(Collection<?> collection);
	Collection<String> getFromArray(Object...array);
	String getVariableNameFrom(String string);
	
	String addToBeginIfDoesNotStartWith(String string,Object prefix);
	String addToEndIfDoesNotEndWith(String string,Object suffix);
	
}
