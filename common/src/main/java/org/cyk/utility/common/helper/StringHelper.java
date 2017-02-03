package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.cdi.AbstractBean;

@Singleton
public class StringHelper extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 2366347884051000495L;

	public static final String[] END_LINE = {"\r\n","\n"};
	
	public enum CaseType{NONE,FURL}
	
	private static final String RESOURCE_BUNDLE_NAME = "org.cyk.utility.common.i18n";
	private static final String KEY_ORDINAL_NUMBER_SUFFIX_FORMAT = "ordinal.number.suffix.%s";
			
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
	
	public java.lang.String getNumberSuffix(Locale locale,Number number) {
		return getText(locale, String.format(KEY_ORDINAL_NUMBER_SUFFIX_FORMAT, number), null);
	}
	
	/**/
	
	private static final StringHelper INSTANCE = new StringHelper();
	public static StringHelper getInstance() {
		return INSTANCE;
	}
	
}
