package org.cyk.utility.locale;

import java.io.Serializable;
import java.util.Locale;

import javax.inject.Singleton;

import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.value.ValueHelper;

@Singleton
public class LocaleHelperImpl extends AbstractHelper implements LocaleHelper,Serializable {
	private static final long serialVersionUID = 1L;

	private Locale defaultLocale;
	
	@Override
	public LocaleHelper setDefaultLocale(Locale defaultLocale) {
		this.defaultLocale = defaultLocale;
		return this;
	}

	@Override
	public Locale getDefaultLocale() {
		return defaultLocale;
	}

	@Override
	public Locale getLocaleDefaultToIfNull(Locale defaultLocale) {
		return __inject__(ValueHelper.class).defaultToIfNull(getDefaultLocale(), defaultLocale);
	}
	
	@Override
	public Locale getLocaleDefaultIfNull() {
		return getLocaleDefaultToIfNull(Locale.FRENCH);
	}
}
