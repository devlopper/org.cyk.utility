package org.cyk.utility.internationalization;

import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.Dependent;

import org.cyk.utility.map.AbstractMapInstanceImpl;
import org.cyk.utility.__kernel__.string.Case;

@Dependent
public class InternationalizationStringMapImpl extends AbstractMapInstanceImpl<String, InternationalizationString> implements InternationalizationStringMap,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public InternationalizationStringMap setKey(String key, String value) {
		get(key, Boolean.TRUE).setKey(new InternationalizationKey().setValue(value));
		return this;
	}

	@Override
	public InternationalizationStringMap setArguments(String key, Object[] arguments) {
		get(key, Boolean.TRUE).setArguments(arguments);
		return this;
	}

	@Override
	public InternationalizationStringMap setLocale(String key, Locale locale) {
		get(key, Boolean.TRUE).setLocale(locale);
		return this;
	}

	@Override
	public InternationalizationStringMap setCase(String key, Case kase) {
		get(key, Boolean.TRUE).setKase(kase);
		return this;
	}

}
