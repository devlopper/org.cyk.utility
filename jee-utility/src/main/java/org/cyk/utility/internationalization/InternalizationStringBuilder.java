package org.cyk.utility.internationalization;

import java.util.Collection;
import java.util.Locale;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndStringAsOutput;
import org.cyk.utility.string.Case;

public interface InternalizationStringBuilder extends FunctionWithPropertiesAsInputAndStringAsOutput {

	InternalizationKeyStringBuilder getKeyBuilder();
	InternalizationKeyStringBuilder getKeyBuilder(Boolean injectIfNull);
	InternalizationStringBuilder setKeyBuilder(InternalizationKeyStringBuilder key);
	InternalizationStringBuilder setKeyValue(String key);
	
	String getKey();
	InternalizationStringBuilder setKey(String key);
	
	Collection<Object> getParameters();
	InternalizationStringBuilder setParameters(Collection<Object> parameters);
	
	Locale getLocale();
	InternalizationStringBuilder setLocale(Locale locale);
	
	Case getCase();
	InternalizationStringBuilder setCase(Case aCase);
	
}
