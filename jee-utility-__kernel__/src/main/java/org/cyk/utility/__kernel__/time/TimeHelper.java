package org.cyk.utility.__kernel__.time;

import java.time.Duration;
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
	
	public static String formatDurationTillNow(String string) {
		if(StringHelper.isBlank(string))
			return null;
		return Duration.between(LocalDateTime.parse(string), LocalDateTime.now()).toString();
	}
}
