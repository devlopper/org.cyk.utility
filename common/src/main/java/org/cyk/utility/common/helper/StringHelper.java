package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;

@Singleton
public class StringHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 2366347884051000495L;

	public static final String[] END_LINE = {"\r\n","\n"};
	
	public enum CaseType{NONE,FURL}
	public enum Location{START,INSIDE,END,EXAT}
	
	private static final String RESOURCE_BUNDLE_NAME = "org.cyk.utility.common.i18n";
	private static final String KEY_ORDINAL_NUMBER_FORMAT = "ordinal.number.%s";
	private static final String KEY_ORDINAL_NUMBER_SUFFIX_FORMAT = KEY_ORDINAL_NUMBER_FORMAT+".suffix";
			
	public String applyCaseType(String string,CaseType caseType){
		switch(caseType){
		case NONE:return string;
		case FURL:return StringUtils.capitalize(string.toLowerCase());
		}
		return string;	
	}
	
	public String removeEndLineMarker(String line){
		for(String endLine : END_LINE)
			if(line.endsWith(endLine))
				return line.substring(0, line.length()-endLine.length());
		return line;
	}
	
	public String getText(Locale locale,String identifier,Object[] parameters){
		if(locale==null)
			locale = Locale.FRENCH;
		ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME,locale);
		String value = parameters==null?resourceBundle.getString(identifier):MessageFormat.format(resourceBundle.getString(identifier),parameters);
		return value;
    }
	
	public java.lang.String getOrdinalNumberSuffix(Locale locale,Number number) {
		return getText(locale, String.format(KEY_ORDINAL_NUMBER_SUFFIX_FORMAT, number), null);
	}
	
	public java.lang.String getOrdinalNumber(Locale locale,Number number) {
		return getText(locale, String.format(KEY_ORDINAL_NUMBER_FORMAT, number), null);
	}
	
	public String concatenate(Object[] strings,String separator){
		return StringUtils.join(strings, separator);
	}
	
	public Boolean isAtLocation(String string,String value,Location location){
		if(StringUtils.isEmpty(value))
			return Boolean.TRUE;
		if(location==null)
			location = Location.EXAT;
		switch(location){
		case START : return StringUtils.startsWith(string, value);
		case INSIDE : return StringUtils.contains(string, value);
		case END : return StringUtils.endsWith(string, value);
		case EXAT : return StringUtils.equals(string, value);
		}
		return Boolean.FALSE;
	}
	
	public Collection<String> removeBlank(Collection<String> collection){
		Collection<String> results = new ArrayList<>();
		for(String string : collection)
			if(StringUtils.isNotBlank(string))
				results.add(string);
		return results;
	}
	
	/**/
	
	private static final StringHelper INSTANCE = new StringHelper();
	public static StringHelper getInstance() {
		return INSTANCE;
	}
	
	//TODO listener has to be added
	
}
