package org.cyk.utility.network.protocol;

import org.cyk.utility.map.MapInstance;

@SuppressWarnings("rawtypes")
public interface ProtocolDefaults extends MapInstance<Class, Protocol> {

	ProtocolSimpleMailTransfer getSimpleMailTransfer();
	ProtocolDefaults setSimpleMailTransfer(ProtocolSimpleMailTransfer simpleMailTransfer);
	
}
