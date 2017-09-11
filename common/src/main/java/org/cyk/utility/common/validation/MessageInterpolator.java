package org.cyk.utility.common.validation;

import java.io.Serializable;
import java.util.Locale;

import javax.inject.Singleton;
import javax.validation.Validation;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.LocaleHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.StringHelper.CaseType;

@Singleton
public class MessageInterpolator extends AbstractBean implements javax.validation.MessageInterpolator,Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE_CUSTOM_START = Constant.CHARACTER_LEFT_BRACKET.toString();
    private static final String MESSAGE_CUSTOM_END = Constant.CHARACTER_RIGHT_BRACKET.toString();
    private static final String PREFIX_MESSAGE_JAVAX = Constant.CHARACTER_LEFT_BRACKET+Constant.PREFIX_PACKAGE_BEAN_VALIDATION;
    private static final String PREFIX_MESSAGE_HIBERNATE = Constant.CHARACTER_LEFT_BRACKET+"org.hibernate.";
    
    private static final javax.validation.MessageInterpolator MESSAGE_INTERPOLATOR = Validation.byDefaultProvider().configure().getDefaultMessageInterpolator();
    
    private static final MessageInterpolator INSTANCE = new MessageInterpolator();
    
    private MessageInterpolator() {
    	logInfo("Message Interpolator Instance created");
    }
    
	public String interpolate(String message, Context context) {
		return interpolate(message, context, LocaleHelper.getInstance().get());
	}

	public String interpolate(String message, Context context, Locale locale) {
		if(StringUtils.startsWithAny(message, PREFIX_MESSAGE_JAVAX,PREFIX_MESSAGE_HIBERNATE))
			return MESSAGE_INTERPOLATOR.interpolate(message, context, locale);
	    if(message.startsWith(MESSAGE_CUSTOM_START) && message.endsWith(MESSAGE_CUSTOM_END)){
	    	String identifier = message.substring(1, message.length()-1);
            return StringHelper.getInstance().get(identifier, CaseType.FURL, new Object[]{}, locale);
	    }
	    return message;
	}
	
	public static MessageInterpolator getInstance() {
		return INSTANCE;
	}
}
