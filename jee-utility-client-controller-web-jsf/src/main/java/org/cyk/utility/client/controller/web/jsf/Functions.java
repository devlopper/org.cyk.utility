package org.cyk.utility.client.controller.web.jsf;

import java.time.Duration;

import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.time.TimeHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;

public class Functions {

	public static String formatLocalDateTimeFromString(String string,String pattern) {
		if(StringHelper.isBlank(string))
			return null;
		pattern = ValueHelper.defaultToIfBlank(pattern, "dd/MM/yyyy");
		return TimeHelper.formatLocalDateTimeFromString(string, pattern);
	}
	
	public static Duration getDurationTillNow(String string) {
		if(StringHelper.isBlank(string))
			return null;
		return TimeHelper.getDurationTillNow(string);
	}
	
}
