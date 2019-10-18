package org.cyk.utility.__kernel__.instance;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueUsageType;

public interface InstanceGetter {

	<INSTANCE> INSTANCE getByIdentifier(Class<INSTANCE> klass,Object identifier,ValueUsageType valueUsageType);
	
	default <INSTANCE> INSTANCE getBySystemIdentifier(Class<INSTANCE> klass,Object identifier) {
		return getByIdentifier(klass, identifier, ValueUsageType.SYSTEM);
	}
	
	default <INSTANCE> INSTANCE getByBusinessIdentifier(Class<INSTANCE> klass,Object identifier) {
		return getByIdentifier(klass, identifier, ValueUsageType.BUSINESS);
	}
	
	/**/
	
	static InstanceGetter getInstance() {
		InstanceGetter instance = (InstanceGetter) INSTANCE.get();
		if(instance != null)
			return instance;
		INSTANCE.set(instance = DependencyInjection.inject(InstanceGetter.class));
		LogHelper.logInfo("instance has been set. <<"+instance.getClass()+">>", InstanceGetter.class);
		return instance;
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
	
}
