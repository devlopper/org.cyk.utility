package org.cyk.utility.internationalization;

import org.cyk.utility.string.StringFunction;

@Deprecated
public interface InternalizationKeyStringBuilder extends StringFunction {

	Object getValue();
	InternalizationKeyStringBuilder setValue(Object value);
	
	InternalizationKeyStringType getType();
	InternalizationKeyStringBuilder setType(InternalizationKeyStringType type);
	
}
