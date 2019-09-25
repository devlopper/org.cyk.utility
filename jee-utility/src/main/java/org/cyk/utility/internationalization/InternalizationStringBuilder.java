package org.cyk.utility.internationalization;

import java.util.Collection;
import java.util.Locale;

import org.cyk.utility.__kernel__.internationalization.InternationalizationKeyStringType;
import org.cyk.utility.__kernel__.string.Case;
import org.cyk.utility.function.FunctionWithPropertiesAsInputAndStringAsOutput;

//TODO use sinleton helper to reduce memory 
@Deprecated
public interface InternalizationStringBuilder extends FunctionWithPropertiesAsInputAndStringAsOutput {

	InternalizationKeyStringBuilder getKeyBuilder();
	InternalizationKeyStringBuilder getKeyBuilder(Boolean injectIfNull);
	InternalizationStringBuilder setKeyBuilder(InternalizationKeyStringBuilder key);
	InternalizationStringBuilder setKeyValue(Object key);
	InternalizationStringBuilder setKeyType(InternationalizationKeyStringType type);
	
	String getKey();
	InternalizationStringBuilder setKey(String key);
	
	Collection<Object> getParameters();
	InternalizationStringBuilder setParameters(Collection<Object> parameters);
	
	Locale getLocale();
	InternalizationStringBuilder setLocale(Locale locale);
	
	Case getCase();
	InternalizationStringBuilder setCase(Case aCase);
	
}
