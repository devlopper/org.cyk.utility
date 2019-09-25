package org.cyk.utility.internationalization;

import org.cyk.utility.__kernel__.internationalization.InternationalizationKeyStringType;
import org.cyk.utility.string.StringFunction;

@Deprecated
public interface InternalizationKeyStringBuilder extends StringFunction {

	Object getValue();
	InternalizationKeyStringBuilder setValue(Object value);
	
	InternationalizationKeyStringType getType();
	InternalizationKeyStringBuilder setType(InternationalizationKeyStringType type);
	
}
