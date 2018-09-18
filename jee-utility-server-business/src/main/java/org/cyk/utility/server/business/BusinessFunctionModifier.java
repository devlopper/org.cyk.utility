package org.cyk.utility.server.business;

import javax.transaction.Transactional;

public interface BusinessFunctionModifier extends BusinessFunctionTransaction {

	@Override 
	@Transactional
	BusinessFunctionModifier execute();
	
}
