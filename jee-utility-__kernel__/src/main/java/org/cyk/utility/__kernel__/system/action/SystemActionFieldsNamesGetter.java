package org.cyk.utility.__kernel__.system.action;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface SystemActionFieldsNamesGetter extends SystemActionXXXGetter<String> {
	
	static SystemActionFieldsNamesGetter getInstance() {
		SystemActionFieldsNamesGetter instance = (SystemActionFieldsNamesGetter) INSTANCE.get();
		if(instance != null)
			return instance;
		INSTANCE.set(instance = DependencyInjection.inject(SystemActionFieldsNamesGetter.class));
		LogHelper.logInfo("instance has been set. <<"+instance.getClass()+">>", SystemActionFieldsNamesGetter.class);
		return instance;
	}
	
	Value INSTANCE = new Value();
}
