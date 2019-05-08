package org.cyk.utility.byte_;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface HashFunction extends FunctionWithPropertiesAsInput<String> {

	String getAlgorithm();
	HashFunction setAlgorithm(String algorithm);
	
	byte[] getBytes();
	HashFunction setBytes(byte[] bytes);
	
}
