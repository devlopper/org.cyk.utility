package org.cyk.utility.string;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.helper.AbstractHelper;

public class StringHelperImpl extends AbstractHelper implements StringHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Number getLength(String string) {
		return StringUtils.length(string);
	}

	@Override
	public Boolean isBlank(String string) {
		return StringUtils.isBlank(string);
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

}
