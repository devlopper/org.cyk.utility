package org.cyk.utility.server.business;

import javax.transaction.Transactional;

public interface BusinessFunctionCreator extends BusinessFunctionTransaction {

	@Override 
	@Transactional
	BusinessFunctionCreator execute();
	
}
