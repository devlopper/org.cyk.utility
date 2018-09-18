package org.cyk.utility.server.business;

import javax.transaction.Transactional;

public interface BusinessFunctionRemover extends BusinessFunctionTransaction {

	@Override 
	@Transactional
	BusinessFunctionRemover execute();
	
	BusinessFunctionRemover setAll(Boolean value);
	Boolean getAll();
}
