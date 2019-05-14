package org.cyk.utility.string;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface StringToBarCodeBytesBuilder extends FunctionWithPropertiesAsInput<byte[]> {

	String getString();
	StringToBarCodeBytesBuilder setString(String string);
	
}
