package org.cyk.utility.client.controller.data;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.__kernel__.system.action.SystemAction;

public interface DataGetter extends FunctionWithPropertiesAsInput<Data> {

	SystemAction getSystemAction();
	DataGetter setSystemAction(SystemAction systemAction);
	
	Boolean getIsInjectIfNull();
	DataGetter setIsInjectIfNull(Boolean isInjectIfNull);
	
	Class<?> getKlass();
	DataGetter setKlass(Class<?> klass);
	
}
