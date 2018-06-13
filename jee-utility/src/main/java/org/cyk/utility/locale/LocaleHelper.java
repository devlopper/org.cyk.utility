package org.cyk.utility.locale;

import java.util.Locale;

public interface LocaleHelper {

	LocaleHelper setDefaultLocale(Locale defaultLocale);
	Locale getDefaultLocale();
	Locale getLocaleDefaultToIfNull(Locale defaultLocale);
	Locale getLocaleDefaultIfNull();
}
