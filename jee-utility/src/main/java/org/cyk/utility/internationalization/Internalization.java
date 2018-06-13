package org.cyk.utility.internationalization;

import java.util.Locale;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.string.Case;

public interface Internalization {

	String getText(Properties properties);
	String getText(String key,Object[] parameters,Locale locale,Case aCase);
	String getText(String key,Object[] parameters);
	String getText(String key,Locale locale,Case aCase);
	String getText(String key);
	
}
