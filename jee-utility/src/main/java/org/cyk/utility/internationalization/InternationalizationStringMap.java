package org.cyk.utility.internationalization;

import java.util.Locale;

import org.cyk.utility.__kernel__.internationalization.InternationalizationString;
import org.cyk.utility.__kernel__.string.Case;
import org.cyk.utility.map.MapInstance;

public interface InternationalizationStringMap extends MapInstance<String, InternationalizationString> {

	InternationalizationStringMap setKey(String key,String value);
	InternationalizationStringMap setArguments(String key,Object[] arguments);
	InternationalizationStringMap setLocale(String key,Locale locale);
	InternationalizationStringMap setCase(String key,Case kase);
}
