package org.cyk.utility.system.action;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface SystemAction extends Objectable  {

	Boolean getIsBatchProcessing();
	SystemAction setIsBatchProcessing(Boolean isBatchProcessing);
}
