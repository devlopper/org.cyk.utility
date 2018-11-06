package org.cyk.utility.string;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface StringFunction extends FunctionWithPropertiesAsInput<String> {

	StringFormat getFormat();
	StringFormat getFormat(Boolean injectIfNull);
	StringFunction setFormat(StringFormat format);
}
