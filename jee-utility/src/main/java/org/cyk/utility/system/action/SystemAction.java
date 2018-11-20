package org.cyk.utility.system.action;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.object.Objects;

public interface SystemAction extends Objectable  {

	Objects getEntities();
	Objects getEntities(Boolean injectIfNull);
	SystemAction setEntities(Objects entities);
	SystemAction setEntityClass(Class<?> entityClass);
	
	Boolean getIsBatchProcessing();
	SystemAction setIsBatchProcessing(Boolean isBatchProcessing);
}
