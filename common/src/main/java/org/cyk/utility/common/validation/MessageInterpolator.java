package org.cyk.utility.common.validation;

import java.util.Locale;

import javax.validation.Validation;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.StringHelper.CaseType;

public class MessageInterpolator implements javax.validation.MessageInterpolator {
	
	private static final String MESSAGE_CUSTOM_START = Constant.CHARACTER_LEFT_PARENTHESIS.toString();
    private static final String MESSAGE_CUSTOM_END = Constant.CHARACTER_RIGHT_PARENTHESIS.toString();
    private static final String PREFIX_MESSAGE_JAVAX = Constant.CHARACTER_LEFT_PARENTHESIS+Constant.PREFIX_PACKAGE_BEAN_VALIDATION;
    private static final String PREFIX_MESSAGE_HIBERNATE = Constant.CHARACTER_LEFT_PARENTHESIS+"org.hibernate.";
    
    public static Locale LOCALE = Locale.FRENCH;
    
    private javax.validation.MessageInterpolator defaultInterpolator;
    
    public MessageInterpolator() {
        defaultInterpolator = Validation.byDefaultProvider().configure().getDefaultMessageInterpolator();
    }
    
	public String interpolate(String message, Context context) {
		return interpolate(message, context, /*languageBusiness.findCurrentLocale()*/LOCALE);
	}

	public String interpolate(String message, Context context, Locale locale) {
		if(StringUtils.startsWithAny(message, PREFIX_MESSAGE_JAVAX,PREFIX_MESSAGE_HIBERNATE))
			return defaultInterpolator.interpolate(message, context, locale);
	    if(message.startsWith(MESSAGE_CUSTOM_START) && message.endsWith(MESSAGE_CUSTOM_END)){
	    	String identifier = message.substring(1, message.length()-1);
            return StringHelper.getInstance().get(identifier, CaseType.FURL, new Object[]{}, locale);
	    }
        return message;
        /*
        String interpolatedMessage = message;
		if(StringUtils.startsWith(message, PREFIX_MESSAGE_JAVAX))
			interpolatedMessage = defaultInterpolator.interpolate(message, context, locale);
	    if(message.startsWith(MESSAGE_CUSTOM_START) && message.endsWith(MESSAGE_CUSTOM_END))
	    	interpolatedMessage = languageBusiness.findText(locale,message.substring(1, message.length()-1));
	    System.out.println(message+" >>> "+interpolatedMessage);
        return interpolatedMessage;
        */
	}
}
