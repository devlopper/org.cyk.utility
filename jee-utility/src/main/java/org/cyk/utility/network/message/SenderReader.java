package org.cyk.utility.network.message;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndVoidAsOutput;
import org.cyk.utility.network.protocol.Protocol;

public interface SenderReader extends FunctionWithPropertiesAsInputAndVoidAsOutput {

	Protocol getProtocol();
	SenderReader setProtocol(Protocol protocol);
	
}
