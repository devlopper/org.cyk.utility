package org.cyk.utility.locale;

import java.util.Locale;

import org.cyk.utility.helper.Helper;
@Deprecated
public interface LocaleHelper extends Helper {

	LocaleHelper setDefaultLocale(Locale defaultLocale);
	Locale getDefaultLocale();
	Locale getLocaleDefaultToIfNull(Locale defaultLocale);
	Locale getLocaleDefaultIfNull();
}
