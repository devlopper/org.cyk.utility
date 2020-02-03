package org.cyk.utility.byte_;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

@Deprecated
public interface BytesOfBarCodeToString extends FunctionWithPropertiesAsInput<String> {

	byte[] getBytes();
	BytesOfBarCodeToString setBytes(byte[] bytes);
	
}
