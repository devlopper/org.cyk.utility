package org.cyk.utility.common.string;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.helper.AbstractHelper;
import org.cyk.utility.common.helper.CollectionHelper;

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
	
	public Collection<String> getMatches(String string,String patternString){
		Collection<String> matches = null;
		if(string != null && patternString != null){
			Pattern pattern = Pattern.compile(patternString);
			Matcher matcher = pattern.matcher(string);			
			if (matcher.find()){
				matches = new ArrayList<>();
				if(matcher.groupCount() > 0){
					for(Integer index = 1; index <= matcher.groupCount(); index++){
						matches.add(matcher.group(index));
					}	
				}else
					matches.add(matcher.group());
			}
		}
		return matches;
	}
	
	public Boolean hasMatch(String string,String patternString){
		return CollectionHelper.getInstance().isNotEmpty(getMatches(string, patternString));
	}
	
	public String getMatch(String string,String patternString){
		return CollectionHelper.getInstance().getFirst(getMatches(string, patternString));
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
