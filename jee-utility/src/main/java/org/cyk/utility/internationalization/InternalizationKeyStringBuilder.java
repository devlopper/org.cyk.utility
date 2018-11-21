package org.cyk.utility.internationalization;

import org.cyk.utility.string.StringFunction;

public interface InternalizationKeyStringBuilder extends StringFunction {

	Object getValue();
	InternalizationKeyStringBuilder setValue(Object value);
	
}
