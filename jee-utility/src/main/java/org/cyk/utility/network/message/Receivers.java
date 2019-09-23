package org.cyk.utility.network.message;

import org.cyk.utility.__kernel__.collection.CollectionInstance;

public interface Receivers extends CollectionInstance<Receiver> {

	@Override Receivers add(Receiver...receivers);
	
}
