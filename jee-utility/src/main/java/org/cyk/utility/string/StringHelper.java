package org.cyk.utility.string;

import org.cyk.utility.__kernel__.properties.Properties;

public interface StringHelper {

	Number getLength(String string);
	Boolean isBlank(String string);
	Boolean isNotBlank(String string);
	String replace(String string, String subString, String replacement);
	String applyCase(String string,Case aCase);
	String getFromRepositories(Properties properties);
}
