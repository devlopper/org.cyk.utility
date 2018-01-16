package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;

@Singleton
public class RegularExpressionHelper extends AbstractHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String ANY = ".*";
	
	private static final String MARKUP_LANGUAGE_TAG_START_NAME_FORMAT = "<(%s?)>";
	private static final String MARKUP_LANGUAGE_TAG_END_NAME_FORMAT = "</(%s?)>";
	
	private static final String MARKUP_LANGUAGE_TAG_START_FORMAT = "(<%s?>)";
	private static final String MARKUP_LANGUAGE_TAG_END_FORMAT = "(</%s?>)";
	
	private static RegularExpressionHelper INSTANCE;
	
	public static RegularExpressionHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new RegularExpressionHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public String getMatch(String string,String patternString){
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(string);
		if (matcher.find())
			return matcher.group(1);
		return null;
	}
	
	public String getMatchMarkupLanguageTagStart(String string,String name){
		return getMatch(string, String.format(MARKUP_LANGUAGE_TAG_START_FORMAT, StringUtils.defaultIfBlank(name, ANY)));
	}
	
	public String getMatchMarkupLanguageTagStart(String string){
		return getMatchMarkupLanguageTagStart(string,null);
	}
	
	public String getMatchMarkupLanguageTagEnd(String string,String name){
		return getMatch(string, String.format(MARKUP_LANGUAGE_TAG_END_FORMAT, StringUtils.defaultIfBlank(name, ANY)));
	}
	
	public String getMatchMarkupLanguageTagEnd(String string){
		return getMatchMarkupLanguageTagEnd(string,null);
	}
	
	public String getMatchMarkupLanguageTagStartName(String string){
		return StringUtils.trim(getMatch(string, String.format(MARKUP_LANGUAGE_TAG_START_NAME_FORMAT, ANY)));
	}
	
	public String getMatchMarkupLanguageTagEndName(String string){
		return StringUtils.trim(getMatch(string, String.format(MARKUP_LANGUAGE_TAG_END_NAME_FORMAT, ANY)));
	}
}
