package org.cyk.utility.internationalization;

import java.util.Collection;
import java.util.Locale;

import org.cyk.utility.__kernel__.string.Case;
import org.cyk.utility.string.StringFunction;

@Deprecated
public interface InternalizationPhraseBuilder extends StringFunction {

	InternalizationStringBuilders getStrings();
	InternalizationStringBuilders getStrings(Boolean injectIfNull);
	InternalizationPhraseBuilder setStrings(InternalizationStringBuilders strings);
	InternalizationPhraseBuilder addStrings(Collection<InternalizationStringBuilder> strings);
	InternalizationPhraseBuilder addStrings(InternalizationStringBuilder...strings);
	InternalizationPhraseBuilder addStringsByKeys(Collection<Object> keys);
	InternalizationPhraseBuilder addStringsByKeys(Object...keys);
	
	Locale getLocale();
	InternalizationPhraseBuilder setLocale(Locale locale);
	
	Case getCase();
	InternalizationPhraseBuilder setCase(Case aCase);
}
