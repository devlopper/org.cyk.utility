package org.cyk.utility.context;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface SystemContextListener<CONTEXT> extends Objectable {

	SystemContextListener<CONTEXT> initialize(CONTEXT context);
	
	SystemContextListener<CONTEXT> destroy(CONTEXT context);
	
}
