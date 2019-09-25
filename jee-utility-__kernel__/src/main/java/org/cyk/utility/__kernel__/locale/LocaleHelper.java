package org.cyk.utility.__kernel__.locale;

import java.util.Locale;

import org.cyk.utility.__kernel__.DependencyInjection;

public interface LocaleHelper {

	static Locale getDefault() {
		return LOCALES.getDefault();
	}
	
	Locales LOCALES = DependencyInjection.inject(Locales.class);
	
}
