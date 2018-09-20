package org.cyk.utility.string;

import java.beans.Introspector;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.helper.AbstractHelper;

@Singleton
public class StringHelperImpl extends AbstractHelper implements StringHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Number getLength(String string) {
		return StringUtils.length(string);
	}

	@Override
	public Boolean isBlank(String string) {
		return __isBlank__(string);
	}
	
	@Override
	public Boolean isNotBlank(String string) {
		return Boolean.FALSE.equals(isBlank(string));
	}
	
	@Override
	public String replace(String string, String subString, String replacement) {
		return StringUtils.replace(string, subString, replacement);
	}
	
	@Override
	public String applyCase(String string, Case aCase) {
		if(string==null)
			return null;
		if(aCase==null)
			aCase = Case.DEFAULT;
		switch(aCase){
		case NONE:return string;
		case LOWER:return StringUtils.lowerCase(string);
		case UPPER:return StringUtils.upperCase(string);
		case FIRST_CHARACTER_UPPER:return StringUtils.upperCase(StringUtils.substring(string, 0,1))+StringUtils.substring(string, 1);
		case FIRST_CHARACTER_LOWER:return StringUtils.lowerCase(StringUtils.substring(string, 0,1))+StringUtils.substring(string, 1);
		case FIRST_CHARACTER_UPPER_REMAINDER_LOWER:return StringUtils.capitalize(string.toLowerCase());
		}
		return string;	
	}
	
	@Override
	public String getFromRepositories(Properties properties) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isAtLocation(String string,String subString,StringLocation location,Boolean caseSensitive){
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
	
	@Override
	public Boolean isAtLocation(String string, String subString, StringLocation location) {
		return isAtLocation(string, subString, location, Boolean.TRUE);
	}

	@Override
	public String concatenate(Collection<String> strings,String separator) {
		return StringUtils.join(strings,separator);
	}
	
	@Override
	public String concatenate(Collection<String> strings) {
		return concatenate(strings, StringConstant.EMPTY);
	}

	@Override
	public String concatenate(String... strings) {
		return concatenate(__inject__(CollectionHelper.class).instanciate(strings));
	}
	
	@Override
	public Collection<String> get(Collection<?> collection) {
		Collection<String> strings = null;
		if(__inject__(CollectionHelper.class).isNotEmpty(strings)){
			strings = new ArrayList<>();
			for(Object index : collection)
				strings.add(index == null ? null : index.toString());	
		}
		return strings;
	}
	
	@Override
	public Collection<String> getFromArray(Object... array) {
		return get(__inject__(CollectionHelper.class).instanciate(array));
	}
	
	@Override
	public String getVariableNameFrom(String string){
		return Introspector.decapitalize(string);
	}
	
	/**/
	
	public static Boolean __isBlank__(String string) {
		return StringUtils.isBlank(string);
	}
	
	public static String __defaultIfBlank__(String string,String defaultString) {
		return StringUtils.defaultIfBlank(string,defaultString);
	}
}
