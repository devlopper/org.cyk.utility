package org.cyk.utility.__kernel__.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;

public interface TimeHelper {

	static String formatLocalDateTimeFromString(String string,String pattern) {
		if(StringHelper.isBlank(string))
			return null;
		pattern = ValueHelper.defaultToIfBlank(pattern, "dd/MM/yyyy");
		LocalDateTime localDateTime =  LocalDateTime.parse(string);
		return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
	}
	
}
